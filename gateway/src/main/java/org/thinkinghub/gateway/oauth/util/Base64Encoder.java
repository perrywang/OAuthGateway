package org.thinkinghub.gateway.oauth.util;

import java.util.Base64;

public class Base64Encoder {

	public static String encode(String str) {
		return Base64.getEncoder().encodeToString((str.getBytes()));
	}
}
