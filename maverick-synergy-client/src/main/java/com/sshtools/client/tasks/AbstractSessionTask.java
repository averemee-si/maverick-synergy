/**
 * (c) 2002-2019 JADAPTIVE Limited. All Rights Reserved.
 *
 * This file is part of the Maverick Synergy Java SSH API.
 *
 * Maverick Synergy is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Maverick Synergy is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Foobar.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.sshtools.client.tasks;

import java.io.IOException;
import java.util.Objects;

import com.sshtools.client.AbstractSessionChannel;
import com.sshtools.client.SshClientContext;
import com.sshtools.common.logger.Log;
import com.sshtools.common.ssh.ChannelRequestFuture;
import com.sshtools.common.ssh.Connection;

/**
 * An abstract task for using the SSH session
 */
public abstract class AbstractSessionTask<T extends AbstractSessionChannel> extends Task {

	Connection<SshClientContext> con;
	long timeout = 10000;
	T session;
	ChannelRequestFuture future;
	Throwable lastError;
	
	public AbstractSessionTask(Connection<SshClientContext> con, ChannelRequestFuture future) {
		super(con);
		this.con = con;
		this.future = future;
	}
	
	public AbstractSessionTask(Connection<SshClientContext> con) {
		this(con, new ChannelRequestFuture());
	}

	public T getSession() {
		return session;
	}
	
	public void disconnect() {
		con.disconnect();
	}
	
	public final Throwable getLastError() {
		return lastError;
	}
	
	public ChannelRequestFuture getChannelFuture() {
		return future;
	}
	
	@Override
	public void doTask() {

		session = createSession(con);
		
		con.getConnectionProtocol().openChannel(session);
		if(!session.getOpenFuture().waitFor(timeout).isSuccess()) {
			throw new IllegalStateException("Could not open session channel");
		}
		
		setupSession(session);
	

		try {
			if(Log.isDebugEnabled()) {
				Log.debug("Starting session task");
			}
			onOpenSession(session);
		} catch(Throwable ex) {
			this.lastError = ex;
		}
	
		if(Log.isDebugEnabled()) {
			Log.debug("Closing session task");
		}
		
		session.close();
		onCloseSession(session);
		
		done(Objects.isNull(lastError));
		
		if(Log.isDebugEnabled()) {
			Log.debug("Session task is done");
		}
	}
	
	protected abstract T createSession(Connection<SshClientContext> con);
	
	protected abstract void setupSession(T session);
	
	protected abstract void onOpenSession(T session) throws IOException;
	
	protected abstract void onCloseSession(T session);

	public void close() {
		session.close();
	}

	public boolean isClosed() {
		return session.isClosed();
	}

	public void changeTerminalDimensions(int cols, int rows, int width, int height) {
		session.changeTerminalDimensions(cols, rows, width, height);
	}
	
}
