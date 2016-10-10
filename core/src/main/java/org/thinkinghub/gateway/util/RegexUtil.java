package org.thinkinghub.gateway.util;

import com.github.scribejava.core.exceptions.OAuthException;
import com.github.scribejava.core.utils.OAuthEncoder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {
    public static String extractParameter(String response, String regex) throws OAuthException {
        final Matcher matcher = Pattern.compile(regex).matcher(response);
        if (matcher.find()) {
            return OAuthEncoder.decode(matcher.group(1));
        } else {
            return null;
        }
    }

    public static boolean isProperUrl(String url){
        String urlRegExp = "^(https?://)?([\\da-z.-]+).([a-z.]{2,6})([/\\w .-]*)*/?$";
        return url.matches(urlRegExp);
    }
}
