package org.thinkinghub.gateway.oauth.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@SuppressWarnings("serial")
@Data
@EqualsAndHashCode(callSuper=false)
public class MandatoryParameterMissingException extends RuntimeException{
	private String errorCode;
	private String message;
	
	public MandatoryParameterMissingException(String message){
		super(message);
	}
	
    public MandatoryParameterMissingException(String errorCode,String message) {
        this.errorCode = errorCode;
        this.message = message;
    }
    
    @Override
    public String toString(){
    	return errorCode + " : " + message;
    }
}
