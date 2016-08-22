package org.thinkinghub.gateway.core.model;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import org.thinkinghub.gateway.core.exceptions.OAuthConnectionException;
import org.thinkinghub.gateway.core.exceptions.OAuthException;
import org.thinkinghub.gateway.core.service.OAuthService;


public class OAuthRequest {
    public static final String CONTENT_TYPE = "Content-Type";
    protected static final String CONTENT_LENGTH = "Content-Length";
	public static final String DEFAULT_CONTENT_TYPE = "application/x-www-form-urlencoded";
	
    private static final String OAUTH_PREFIX = "oauth_";
	
	private final Verb verb;
	private final String url;
	private final OAuthService service;
	private String userAgent;
	private final Map<String,String> headers = new HashMap<>();
	private HttpURLConnection connection;
	private final ParameterList querystringParams = new ParameterList();
	private byte[] bytePayload;
	private String charset;
	
	public OAuthRequest(Verb verb, String url, OAuthService service){
        this.verb = verb;
        this.url = url;
        this.service = service;
	}
	
	public Verb getVerb(){
		return this.verb;
	}
    public String getUserAgent() {
        return userAgent;
    }
    
	public OAuthService getService(){
		return this.service;
	}
	
    public Map<String, String> getHeaders() {
        return headers;
    }
    
    void addHeaders() {
        for (Map.Entry<String, String> entry : getHeaders().entrySet()) {
            connection.setRequestProperty(entry.getKey(), entry.getValue());
        }
        final String userAgent = getService().getConfig().getUserAgent();
        if (userAgent != null) {
            connection.setRequestProperty(OAuthConstants.USER_AGENT_HEADER_NAME, userAgent);
        }
    }
    
    public String getCompleteUrl() {
        return querystringParams.appendTo(url);
    }
    
    private void createConnection() throws IOException {
        final String completeUrl = getCompleteUrl();
        if (connection == null) {
            connection = (HttpURLConnection) new URL(completeUrl).openConnection();
        }
    }
    
    public Response send() {
        try {
            createConnection();
            return doSend();
        } catch (IOException | RuntimeException e) {
            throw new OAuthConnectionException(getCompleteUrl(), e);
        }
    }
    
    Response doSend() throws IOException {
        final Verb verb = getVerb();
        connection.setRequestMethod(verb.name());
        final OAuthConfig config = getService().getConfig();
        if (config.getConnectTimeout() != null) {
            connection.setConnectTimeout(config.getConnectTimeout());
        }
        if (config.getReadTimeout() != null) {
            connection.setReadTimeout(config.getReadTimeout());
        }
        addHeaders();
        if (hasBodyContent()) {
            addBody(getByteBodyContents());
        }
        return new Response(connection);
    }
    
    void addBody(byte[] content) throws IOException {
        connection.setRequestProperty(CONTENT_LENGTH, String.valueOf(content.length));

        if (connection.getRequestProperty(CONTENT_TYPE) == null) {
            connection.setRequestProperty(CONTENT_TYPE, DEFAULT_CONTENT_TYPE);
        }
        connection.setDoOutput(true);
        connection.getOutputStream().write(content);
    }
    
    protected boolean hasBodyContent() {
        return verb == Verb.PUT || verb == Verb.POST;
    }
    
    private String payload;
    private final ParameterList bodyParams = new ParameterList();
    
    public void addBodyParameter(String key, String value) {
        this.bodyParams.add(key, value);
    }
    
    public void addParameter(String key, String value) {
        if (hasBodyContent()) {
            bodyParams.add(key, value);
        } else {
            querystringParams.add(key, value);
        }
    }
    
    public String getCharset() {
        return charset == null ? Charset.defaultCharset().name() : charset;
    }
    
    byte[] getByteBodyContents() {
        if (bytePayload != null) {
            return bytePayload;
        }
        final String body = (payload == null) ? bodyParams.asFormUrlEncodedString() : payload;
        try {
            return body.getBytes(getCharset());
        } catch (UnsupportedEncodingException uee) {
            throw new OAuthException("Unsupported Charset: " + getCharset(), uee);
        }
    }
        
}
