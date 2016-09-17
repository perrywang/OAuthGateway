package org.thinkinghub.gateway.oauth.extractor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
import org.thinkinghub.gateway.oauth.entity.ServiceType;
import org.thinkinghub.gateway.oauth.exception.GatewayException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.scribejava.core.model.Response;

@Component("QQ")
public class QQResponseExtractor extends BaseResponseExtractor {
	public String getUserIdFieldName() {
		return null;
	}

	public String getUserId(Response response) {
		try {
			ObjectMapper om = new ObjectMapper();
			JsonNode root = om.readTree(response.getBody());
			String figureurl = root.get("figureurl").asText();
			figureurl.matches("[0-9A-Z]{32}");
			final Matcher matcher = Pattern.compile("[0-9A-Z]{32}").matcher(figureurl);
			if (matcher.find()) {
				return matcher.group(0);
			}
		} catch (Exception e) {
			throw new GatewayException("QQ can't extract openid", e);
		}
		throw new GatewayException("QQ can't extract openid");
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
