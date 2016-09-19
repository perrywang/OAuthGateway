package org.thinkinghub.gateway.oauth.bean;

import org.thinkinghub.gateway.oauth.entity.ErrorType;

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
	ErrorType errorType;
	
	public ErrorResponse(String gwErrorCode, String gwErrorMsg, ErrorType errorType){
		this.gwErrorCode = gwErrorCode;
		this.gwErrorMsg = gwErrorMsg;
		this.errorType = errorType;
	}
	
	@Override
	public String toString(){
		return "{\"retCode\":"+ 1
				+","+"\"errorType\":"+ errorType
				+","+"\"gwErrorCode\":"+ gwErrorCode 
				+","+"\"gwErrorMsg\":\"" + gwErrorMsg
    			+","+"\"oauthErrorCode\":\"" + oauthErrorCode
    			+"\","+"\"oauthErrorMsg\":\"" + oauthErrorMsg
    			+"\"}";
	}
}
