package org.thinkinghub.gateway.oauth.util;

public class Base64Encoder {

	private static class instanceHolder{
		static final Base64Encoder INSTANCE = new Base64Encoder(); 
	}

	public static Base64Encoder getInstance() {
		return instanceHolder.INSTANCE;
	}

	public String encode(String str) {
		sun.misc.BASE64Encoder be = new sun.misc.BASE64Encoder();
		String base64 = be.encode(str.getBytes());
		return base64;
	}
}
