package org.thinkinghub.gateway.oauth.exception;

@SuppressWarnings("serial")
public class QQUserIdRetrievingException extends GatewayException {
    public String getGWErrorCode() {
        return "GW30005";
    }

}
