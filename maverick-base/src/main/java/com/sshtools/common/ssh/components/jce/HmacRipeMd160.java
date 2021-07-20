
package com.sshtools.common.ssh.components.jce;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import com.sshtools.common.ssh.SecurityLevel;
import com.sshtools.common.ssh.SshException;


/**
 * SHA-1 message authentication implementation.
 * @author Lee David Painter
 *
 */
public class HmacRipeMd160 extends AbstractHmac {

	public HmacRipeMd160() {
		super(JCEAlgorithms.JCE_HMACRIPEMD160, 20, SecurityLevel.WEAK, 1);
	}
	
	protected HmacRipeMd160(int size) {
		super(JCEAlgorithms.JCE_HMACRIPEMD160, 20, size, SecurityLevel.WEAK, 1);
	}

	
	public String getAlgorithm() {
		return "hmac-ripemd160";
	}
	

	public void init(byte[] keydata) throws SshException {
        try {
            mac = JCEProvider.getProviderForAlgorithm(jceAlgorithm)==null ? Mac.getInstance(jceAlgorithm) : Mac.getInstance(jceAlgorithm, JCEProvider.getProviderForAlgorithm(jceAlgorithm));

            // Create a key of 16 bytes
            byte[] key = new byte[20];
            System.arraycopy(keydata, 0, key, 0, key.length);

            SecretKeySpec keyspec = new SecretKeySpec(key, jceAlgorithm);
            mac.init(keyspec);
        } catch (Throwable t) {
            throw new SshException(t);
        }
	}


}
