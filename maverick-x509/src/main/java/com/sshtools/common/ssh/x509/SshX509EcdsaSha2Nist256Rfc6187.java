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

package com.sshtools.common.ssh.x509;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.interfaces.ECPublicKey;

import com.sshtools.common.ssh.components.SshPublicKeyFactory;
import com.sshtools.common.ssh.components.jce.JCEAlgorithms;

public class SshX509EcdsaSha2Nist256Rfc6187 extends
		SshX509EcdsaSha2NistPublicKeyRfc6187 {
	
	private static final String ALGORITHM = "x509v3-ecdsa-sha2-nistp256";
	
	public static class SshX509EcdsaSha2Nist256Rfc6187Factory implements SshPublicKeyFactory<SshX509EcdsaSha2Nist256Rfc6187> {

		@Override
		public SshX509EcdsaSha2Nist256Rfc6187 create() throws NoSuchAlgorithmException, IOException {
			return new SshX509EcdsaSha2Nist256Rfc6187();
		}

		@Override
		public String[] getKeys() {
			return new String[] {  ALGORITHM };
		}
	}

	public SshX509EcdsaSha2Nist256Rfc6187(ECPublicKey pk) throws IOException {
		super(pk, "secp256r1");
	}

	public SshX509EcdsaSha2Nist256Rfc6187() {
		super("ecdsa-sha2-nistp256", JCEAlgorithms.JCE_SHA256WithECDSA, "secp256r1", "nistp256");
	}

	public SshX509EcdsaSha2Nist256Rfc6187(Certificate[] chain)
			throws IOException {
		super(chain, "secp256r1");
	}

	@Override
	public String getAlgorithm() {
		return ALGORITHM;
	}

}
