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
