package org.thinkinghub.gateway.oauth.extractor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.thinkinghub.gateway.oauth.entity.ServiceType;
import org.thinkinghub.gateway.oauth.exception.QQUserIdRetrievingException;
import org.thinkinghub.gateway.oauth.util.JsonUtil;

public class QQResponseExtractor extends BaseResponseExtractor {
    public String getUserIdFieldName() {
        return null;
    }

    @Override
    public String getUserId(String response) {
        String figureUrl = JsonUtil.getString(response, "figureurl");
        figureUrl.matches("[0-9A-Z]{32}");
        final Matcher matcher = Pattern.compile("[0-9A-Z]{32}").matcher(figureUrl);
        if (matcher.find()) {
            return matcher.group(0);
        }
        throw new QQUserIdRetrievingException();
    }

    public String getNickNameFieldName() {
        return "nickname";
    }

    public String getHeadImageUrlFieldName() {
        return "figureurl";
    }

    public String getErrorCodeFieldName() {
        return "ret";
    }

    public String getErrorDescFieldName() {
        return "msg";
    }

    public ServiceType getServiceType() {
        return ServiceType.QQ;
    }

}
