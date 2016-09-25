package org.thinkinghub.gateway.oauth.extractor;

import org.thinkinghub.gateway.oauth.response.GatewayResponse;

import com.github.scribejava.core.model.Response;

public interface ResponseExtractor {
    GatewayResponse extract(Response response);
}
