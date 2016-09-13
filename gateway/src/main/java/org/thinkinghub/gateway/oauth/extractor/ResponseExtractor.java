package org.thinkinghub.gateway.oauth.extractor;

import org.thinkinghub.gateway.oauth.bean.RetBean;

import com.github.scribejava.core.model.Response;

public interface ResponseExtractor {
    RetBean extract(Response response);
}
