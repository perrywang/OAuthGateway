package org.thinkinghub.gateway.oauth.response;

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
	String gwErrorMessage;
	String oauthErrorCode;
	String oauthErrorMessage;
	ErrorType errorType;
	ServiceType serviceType;
	
	public ErrorResponse(String gwErrorCode, String gwErrorMessage, ErrorType errorType, ServiceType serviceType){
		this.gwErrorCode = gwErrorCode;
		this.gwErrorMessage = gwErrorMessage;
		this.errorType = errorType;
		this.serviceType = serviceType;
	}
	
	@Override
	public String toString(){
		return "{\"retCode\":"+ 1
				+","+"\"errorType\":"+ errorType
				+","+"\"gwErrorCode\":"+ gwErrorCode 
				+","+"\"gwErrorMessage\":\"" + gwErrorMessage
    			+","+"\"oauthErrorCode\":\"" + oauthErrorCode
    			+"\","+"\"oauthErrorMessage\":\"" + oauthErrorMessage
    			+"\"}";
	}
}
