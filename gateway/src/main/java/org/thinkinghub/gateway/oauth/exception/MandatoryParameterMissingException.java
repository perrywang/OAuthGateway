package org.thinkinghub.gateway.oauth.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@SuppressWarnings("serial")
@Data
@EqualsAndHashCode(callSuper=false)
public class MandatoryParameterMissingException extends RuntimeException{
	
	public MandatoryParameterMissingException(){
		super();
	}
	
	public MandatoryParameterMissingException(String message){
		super(message);
	}
	
    public String getErrorCode(){
    	return "GW10001";
    }
}
