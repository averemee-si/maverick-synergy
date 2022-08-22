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

package com.sshtools.common.ssh.components.jce;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import com.sshtools.common.ssh.SecurityLevel;
import com.sshtools.common.ssh.components.SshHmacFactory;

/**
 * MD5 message authentication implementation.
 * @author Lee David Painter
 *
 */
public class HmacMD5ETM extends AbstractHmac {
	
	private static final String ALGORITHM = "hmac-md5-etm@openssh.com";

	public static class HmacMD5ETMFactory implements SshHmacFactory<HmacMD5ETM> {
		@Override
		public HmacMD5ETM create() throws NoSuchAlgorithmException, IOException {
			return new HmacMD5ETM();
		}

		@Override
		public String[] getKeys() {
			return new String[] { ALGORITHM };
		}
	}

	public HmacMD5ETM() {
		super(JCEAlgorithms.JCE_HMACMD5, 16, SecurityLevel.WEAK, 1);
	}

	
	public String getAlgorithm() {
		return ALGORITHM;
	}
	
	@Override
	public boolean isETM() {
		return true;
	}

}
