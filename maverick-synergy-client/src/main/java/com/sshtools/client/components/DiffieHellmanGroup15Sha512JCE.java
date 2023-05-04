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
package com.sshtools.client.components;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import com.sshtools.client.SshKeyExchangeClientFactory;
import com.sshtools.common.ssh.SecurityLevel;
import com.sshtools.common.ssh.components.DiffieHellmanGroups;
import com.sshtools.common.ssh.components.jce.JCEAlgorithms;

/**
 * Implementation of the required SSH Transport Protocol key exchange method
 * "diffie-hellman-group14-sha1".
 */
public class DiffieHellmanGroup15Sha512JCE extends DiffieHellmanGroup {

	/**
	 * Constant for the algorithm name "diffie-hellman-group15-sha512".
	 */
	public static final String DIFFIE_HELLMAN_GROUP15_SHA512 = "diffie-hellman-group15-sha512";

	public static class DiffieHellmanGroup15Sha512JCEFactory
			implements SshKeyExchangeClientFactory<DiffieHellmanGroup15Sha512JCE> {
		@Override
		public DiffieHellmanGroup15Sha512JCE create() throws NoSuchAlgorithmException, IOException {
			return new DiffieHellmanGroup15Sha512JCE();
		}

		@Override
		public String[] getKeys() {
			return new String[] { DIFFIE_HELLMAN_GROUP15_SHA512 };
		}
	}

	public DiffieHellmanGroup15Sha512JCE() {
		super(DIFFIE_HELLMAN_GROUP15_SHA512, JCEAlgorithms.JCE_SHA512, DiffieHellmanGroups.group15,
				SecurityLevel.PARANOID, 3015);
	}

}
