/*
 *    _           _             _   _
 *   (_) __ _  __| | __ _ _ __ | |_(_)_   _____
 *   | |/ _` |/ _` |/ _` | '_ \| __| \ \ / / _ \
 *   | | (_| | (_| | (_| | |_) | |_| |\ V /  __/
 *  _/ |\__,_|\__,_|\__,_| .__/ \__|_| \_/ \___|
 * |__/                  |_|
 *
 * This file is part of the Maverick Synergy Hotfixes Java SSH API
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 *
 * Copyright (C) 2002-2021 JADAPTIVE Limited - All Rights Reserved
 *
 * Use of this software may also be covered by third-party licenses depending on the choices you make about what features to use.
 *
 * Please visit the link below to see additional third-party licenses and copyrights
 *
 * https://www.jadaptive.com/app/manpage/en/article/1565029/What-third-party-dependencies-does-the-Maverick-Synergy-API-have
 */

package com.sshtools.agent.server;

import java.io.File;
import java.io.IOException;

import org.newsclub.net.unix.AFUNIXServerSocket;
import org.newsclub.net.unix.AFUNIXSocketAddress;

import com.sshtools.agent.InMemoryKeyStore;
import com.sshtools.agent.KeyStore;
import com.sshtools.agent.openssh.OpenSSHConnectionFactory;
import com.sshtools.common.logger.Log;

public class SshAgentServer {

	KeyStore keystore;
	SshAgentConnectionFactory connectionFactory;
	SshAgentAcceptor acceptor;
	
	public SshAgentServer(SshAgentConnectionFactory connectionFactory) {
		this(connectionFactory, new InMemoryKeyStore());
	}
	
	public SshAgentServer(SshAgentConnectionFactory connectionFactory, KeyStore keystore) {
		this.connectionFactory = connectionFactory;
		this.keystore = keystore;
	}
	
	public void startListener(SshAgentAcceptor acceptor) throws IOException {
		
		this.acceptor = acceptor;
		ServerThread t = new ServerThread(acceptor);
		t.start();
	}
	
	public void startUnixSocketListener(String location) throws IOException{
		
		File socketFile = new File(location);
		AFUNIXServerSocket server = AFUNIXServerSocket.newInstance(); 
		server.bind(AFUNIXSocketAddress.of(socketFile));
		
		ServerThread t = new ServerThread(acceptor = new UnixSocketAdapter(server));
		t.start();
	}
	
	public void close() throws IOException {
		Log.info("Agent server is closing down");
		if(acceptor!=null) {
			acceptor.close();
		}
	}
	
	class ServerThread extends Thread {

		SshAgentAcceptor socket;
		public ServerThread(SshAgentAcceptor socket) {
			super("Agent-Server-Thread");
			setDaemon(true);
			this.socket = socket;
		}
		
		public void run() {
			SshAgentTransport sock;
			try {
				while((sock = socket.accept())!=null) {
					
					SshAgentConnection c = connectionFactory.createConnection(keystore, 
							sock.getInputStream(), sock.getOutputStream(), sock);
					Thread t = new Thread(c);
					t.start();
				}
			} catch (IOException e) {
				Log.error("Agent server exited with error", e);
				try {
					socket.close();
				} catch (IOException e1) {
				}
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		new SshAgentServer(new OpenSSHConnectionFactory()).startUnixSocketListener("/private/tmp/com.sshtools.agent");
	}
 }
