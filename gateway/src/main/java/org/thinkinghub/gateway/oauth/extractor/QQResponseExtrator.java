package org.thinkinghub.gateway.oauth.extractor;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
import org.thinkinghub.gateway.oauth.bean.RetBean;
import org.thinkinghub.gateway.oauth.entity.ServiceType;

import com.github.scribejava.core.exceptions.OAuthException;
import com.github.scribejava.core.utils.Preconditions;

@Component("QQ")
public class QQResponseExtrator extends BaseResponseExtractor {
	private static String OPEN_ID_REGEX = "\"openid\"\\s*:\\s*\"(\\S*?)\"";
	
    private final String RET = "ret"; //if ret is not 0, the error code will be stored in it.
    private final String USERID = "openid";
    private final String NICKNAME = "nickname";
    private final String HEADIMAGE_URL = "figureurl";
    private final String ERRORDESC = "msg";

    @Override
	public RetBean extract(String response) {
		String ret = extractParameter(response, getJsonRegex(RET));
		if (ret == null) {
			extractOpenId(response);
			String openId = extractOpenId(response);
			return new RetBean(openId, null, null, ServiceType.QQ);
		} else if (!("0").equals(ret)) {
			String errorDesc = extractParameter(response, getJsonRegex(ERRORDESC));
			return new RetBean(ret, errorDesc, ServiceType.QQ);
		} else {
			String userId = extractParameter(response, getJsonRegex(USERID));
			String nickname = extractParameter(response, getJsonRegex(NICKNAME));
			String headImage = extractParameter(response, getJsonRegex(HEADIMAGE_URL));
			return new RetBean(userId, nickname, headImage, ServiceType.QQ);
		}
	}
    
    private String extractOpenId(String response) {
        Preconditions.checkEmptyString(response,
                "Response body is incorrect. Can't extract a openId from an empty string");

        final String openId = extractParameter(response, OPEN_ID_REGEX, true);

        return openId;
    }
    
    protected static String extractParameter(String response, String regex, boolean required) throws OAuthException {
        final Matcher matcher = Pattern.compile(regex).matcher(response);
        if (matcher.find()) {
            return matcher.group(1);
        }

        if (required) {
            throw new OAuthException("Response body is incorrect. Can't extract a '" + regex
                    + "' from this: '" + response + "'", null);
        }

        return null;
    }
}
