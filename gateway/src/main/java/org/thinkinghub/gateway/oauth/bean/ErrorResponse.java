package org.thinkinghub.gateway.oauth.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ErrorResponse {
	String gwErrorCode;
	String gwErrorMsg;
	String oauthErrorCode;
	String oauthErrorMsg;
	
	@Override
	public String toString(){
		return "{\"gwErrorCode\":"+ gwErrorCode 
				+","+"\"gwErrorMsg\":\"" + gwErrorMsg
    			+","+"\"oauthErrorCode\":\"" + oauthErrorCode
    			+"\","+"\"oauthErrorMsg\":\"" + oauthErrorMsg
    			+"\"}";
	}
}
