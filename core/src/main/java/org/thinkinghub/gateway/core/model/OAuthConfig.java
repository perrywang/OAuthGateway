package org.thinkinghub.gateway.core.model;

import java.io.OutputStream;

import com.github.scribejava.core.model.SignatureType;

public class OAuthConfig {
    private String apiKey;
    private String apiSecret;
    private String callback;
    private SignatureType signatureType;
    private String scope;
    private OutputStream debugStream;
    private String state;
    private String responseType;
    private String userAgent;
    
    private Integer connectTimeout;
    private Integer readTimeout;
    
	public OAuthConfig(String apiKey, String apiSecret, String callback, String scope, OutputStream debugStream,
			String state, String responseType, String userAgent, Integer connectTimeout, Integer readTimeout) {
		this.apiKey = apiKey;
		this.apiSecret = apiSecret;
		this.callback = callback;
		this.scope = scope;
		this.debugStream = debugStream;
		this.state = state;
		this.responseType = responseType;
		this.userAgent = userAgent;
		this.connectTimeout = connectTimeout;
		this.readTimeout = readTimeout;
	}
    
    public String getResponseType(){
    	return this.responseType;
    }
    
    public String getApiKey(){
    	return this.apiKey;
    }
    
    public String getApiSecret(){
    	return this.apiSecret;
    }
    
    public String getCallback(){
    	return this.callback;
    }
    
    public String getScope(){
    	return this.scope;
    }
    
    public String getState(){
    	return this.state;
    }
    
    public String getUserAgent(){
    	return this.userAgent;
    }
    
    public Integer getConnectTimeout(){
    	return this.connectTimeout;
    }
    
    public Integer getReadTimeout(){
    	return this.readTimeout;
    }
}
