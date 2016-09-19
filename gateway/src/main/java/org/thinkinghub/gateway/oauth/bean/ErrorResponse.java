package org.thinkinghub.gateway.oauth.bean;

import org.thinkinghub.gateway.oauth.entity.ErrorType;
import org.thinkinghub.gateway.oauth.entity.ServiceType;

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
	ServiceType serviceType;
	
	public ErrorResponse(String gwErrorCode, String gwErrorMsg, ErrorType errorType, ServiceType serviceType){
		this.gwErrorCode = gwErrorCode;
		this.gwErrorMsg = gwErrorMsg;
		this.errorType = errorType;
		this.serviceType = serviceType;
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
