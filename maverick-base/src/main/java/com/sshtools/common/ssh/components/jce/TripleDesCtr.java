/*
 * (c) 2002-2023 JADAPTIVE Limited. All Rights Reserved.
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
 * along with Maverick Synergy.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.sshtools.common.ssh.components.jce;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import com.sshtools.common.ssh.SecurityLevel;
import com.sshtools.common.ssh.components.SshCipherFactory;

/**
 * An implementation of the 3DES cipher using a JCE provider.
 * 
 * @author Lee David Painter
 */
public class TripleDesCtr extends AbstractJCECipher {

	private static final String CIPHER = "3des-ctr";

	public static class TripleDesCtrFactory implements SshCipherFactory<TripleDesCtr> {

		@Override
		public TripleDesCtr create() throws NoSuchAlgorithmException, IOException {
			return new TripleDesCtr();
		}

		@Override
		public String[] getKeys() {
			return new String[] { CIPHER };
		}
	}

	public TripleDesCtr() throws IOException {
		super(JCEAlgorithms.JCE_3DESCTRNOPADDING, "DESede", 24, CIPHER, SecurityLevel.WEAK, 0);
	}

}
