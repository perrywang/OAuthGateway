package org.thinkinghub.gateway.oauth.extractor;

import org.thinkinghub.gateway.oauth.bean.RetBean;

public interface ResponseExtractor {
    RetBean extract(String response);
}
