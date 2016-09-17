package org.thinkinghub.gateway.api;

import com.github.scribejava.core.builder.api.DefaultApi20;
import com.github.scribejava.core.model.Verb;

public class FacebookApi extends DefaultApi20 {
    protected FacebookApi() {
    }

    private static class InstanceHolder {

        private static final FacebookApi INSTANCE = new FacebookApi();
    }

    public static FacebookApi instance() {
        return InstanceHolder.INSTANCE;
    }

    @Override
    public Verb getAccessTokenVerb() {
        return Verb.GET;
    }

    @Override
    public String getAccessTokenEndpoint() {
        return "https://graph.facebook.com/v2.6/oauth/access_token";
    }

    @Override
    public String getRefreshTokenEndpoint() {
        throw new UnsupportedOperationException("Facebook doesn't support refreshing tokens");
    }

    @Override
    protected String getAuthorizationBaseUrl() {
        return "https://www.facebook.com/v2.6/dialog/oauth";
    }
}