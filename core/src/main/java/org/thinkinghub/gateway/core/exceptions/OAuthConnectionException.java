package org.thinkinghub.gateway.core.exceptions;

import com.github.scribejava.core.exceptions.OAuthException;

public class OAuthConnectionException extends OAuthException {

	private static final long serialVersionUID = -4405417233087481586L;
	private static final String MSG = "There was a problem while creating a connection to the remote service: ";

    public OAuthConnectionException(String url, Exception e) {
        super(MSG + url, e);
    }
}