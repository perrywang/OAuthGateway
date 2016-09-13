package org.thinkinghub.gateway.oauth.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.thinkinghub.gateway.oauth.exception.GatewayException;

public class MD5Encrypt {

	public static String hashing(String str) {

		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());
			byte byteData[] = md.digest();
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < byteData.length; i++) {
				sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			throw new GatewayException("Error occurred while using MD5 hashing.", e);
		}
	}
}