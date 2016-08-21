package org.thinkinghub.gateway.core.exceptions;

public class OAuthException extends RuntimeException{
	public OAuthException (String message, Exception e){
		super(message, e);
	}
}
