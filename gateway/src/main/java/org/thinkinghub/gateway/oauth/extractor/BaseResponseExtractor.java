package org.thinkinghub.gateway.oauth.extractor;

import com.github.scribejava.core.exceptions.OAuthException;
import org.thinkinghub.gateway.oauth.bean.RetBean;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class BaseResponseExtractor implements ResponseExtractor{
    private static String COMMON_JSON_REGEX = "\"%s\"\\s*:\\s*\"(\\S*?)\"";
    private static String COMMON_URLPARAM_REGEX = "\"%s\"\\s*=\\s*\"(\\S*?)\"";

    public abstract RetBean extract(String response);

    String getJsonRegex(String name) {
        return String.format(COMMON_JSON_REGEX, name);
    }

    String getUrlParamRegex(String name) {
        return String.format(COMMON_URLPARAM_REGEX, name);
    }

    protected static String extractParameter(String response, String regex) throws OAuthException {
        final Matcher matcher = Pattern.compile(regex).matcher(response);
        if (matcher.find()) {
            return matcher.group(1);
        }

        return null;
    }
}
