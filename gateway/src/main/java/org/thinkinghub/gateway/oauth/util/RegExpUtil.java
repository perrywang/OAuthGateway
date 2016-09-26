package org.thinkinghub.gateway.oauth.util;

public class RegExpUtil {
	public static boolean isProperUrl(String url){
        String urlRegExp = "^https?://[\\d\\-a-zA-Z]+(\\.[\\d\\-a-zA-Z]+)*/?$";
        return url.matches(urlRegExp);
	}
}
