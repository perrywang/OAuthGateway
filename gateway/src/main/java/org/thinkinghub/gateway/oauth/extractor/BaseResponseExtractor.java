package org.thinkinghub.gateway.oauth.extractor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.scribejava.core.exceptions.OAuthException;

public abstract class BaseResponseExtractor implements ResponseExtractor{
    private static String COMMON_JSON_REGEX = "\"%s\"\\s*:\\s*\"(\\S*?)\"";
    private static String COMMON_URLPARAM_REGEX = "\"%s\"\\s*=\\s*\"(\\S*?)\"";

//    public RetBean extract(String response) {
//    	
//    	ObjectMapper om = new ObjectMapper();
//    	om.readTree(response).get(getErrorCodeFieldName());
//    	
//    	String ddd = getxxx();
//    	
//    	return new RetBean();
//    }
//    
//    public String getErrorCodeFieldName() {
//    	return "errorCode";
//    }
//    
//    
//    
//    String getErrorCode(String response) {
//    	ObjectMapper om = new ObjectMapper();
//    	return om.readTree(response).get(getErrorCodeFieldName());
//    }

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
