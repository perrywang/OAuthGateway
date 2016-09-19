package org.thinkinghub.gateway.oauth.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ErrorResponse {
	String gwErrorCode;
	String oauthErrorCode;
	String oauthErrorMsg;
	
	@Override
	public String toString(){
		return "{\"gwErrorCode\":"+ gwErrorCode 
    			+","+"\"oauthErrorCode\":\"" + oauthErrorCode
    			+"\","+"\"oauthErrorMsg\":\"" + oauthErrorMsg
    			+"\"}";
	}
}
