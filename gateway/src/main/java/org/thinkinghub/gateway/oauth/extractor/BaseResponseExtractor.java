package org.thinkinghub.gateway.oauth.extractor;

import com.github.scribejava.core.exceptions.OAuthException;
import org.thinkinghub.gateway.oauth.bean.RetBean;
import org.thinkinghub.gateway.oauth.entity.ServiceType;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class BaseResponseExtractor implements ResponseExtractor {
    private static String COMMON_JSON_REGEX = "\"%s\"\\s*:\\s*\"(\\S*?)\"";
    private static String COMMON_URLPARAM_REGEX = "\"%s\"\\s*=\\s*\"(\\S*?)\"";

    abstract String getUserId();

    abstract String getNickname();

    abstract String getHeadImageUrl();

    abstract String getErrorCode();

    abstract String getErrorDesc();

    abstract Enum getServiceType();

    String getJsonRegex(String name) {
        return String.format(COMMON_JSON_REGEX, name);
    }

    String getUrlParamRegex(String name) {
        return String.format(COMMON_URLPARAM_REGEX, name);
    }

    public RetBean extract(String response) {
        String errorCode = extractParameter(response, getJsonRegex(getErrorCode()));
        if (errorCode != null) {
            String errorDesc = extractParameter(response, getJsonRegex(getErrorDesc()));
            return new RetBean(errorCode, errorDesc, getServiceType());
        }

        String userId = extractParameter(response, getJsonRegex(getUserId()));
        String nickname = extractParameter(response, getJsonRegex(getNickname()));
        String headImage = extractParameter(response, getJsonRegex(getHeadImageUrl()));

        return new RetBean(userId, nickname, headImage, ServiceType.WECHAT);
    }

    protected static String extractParameter(String response, String regex) throws OAuthException {
        final Matcher matcher = Pattern.compile(regex).matcher(response);
        if (matcher.find()) {
            return matcher.group(1);
        }

        return null;
    }
}
