package org.thinkinghub.gateway.oauth.exception;

public class QQUserIdRetrievingException extends GatewayException {
    public String getGWErrorCode() {
        return "GW30005";
    }

}
