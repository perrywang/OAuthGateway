package org.thinkinghub.gateway.oauth.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Base64Encoder {

	private static MD5Base64Encoder instance;

	public static MD5Base64Encoder getInstance() {
		synchronized (MD5Base64Encoder.class) {
			if (instance == null) {
				instance = new MD5Base64Encoder();
			}
		}
		return instance;
	}

	public static String encode(String str) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("md5");
		} catch (NoSuchAlgorithmException e) {

		} finally {

		}
		byte[] md5 = md.digest(str.getBytes());

		sun.misc.BASE64Encoder be = new sun.misc.BASE64Encoder();
		String base64 = be.encode(md5);
		return base64;
	}
}
