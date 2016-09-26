package org.thinkinghub.gateway.oauth.event;

import org.thinkinghub.gateway.oauth.response.ErrorResponse;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class OAuthProcessErrorEvent extends GatewayEvent {
    private ErrorResponse errResponse;

    private String state;

    public OAuthProcessErrorEvent(ErrorResponse response, String state) {
        this.errResponse = response;
        this.state = state;
    }

    @Override
    public String toString() {
        return "state: " + state
                + "\n"
                + "Gateway error code: " + errResponse.getGwErrorCode()
                + "\n"
                + "Gateway error message: " + errResponse.getGwErrorMessage()
                + "\n"
                + "OAuth error code: " + errResponse.getOauthErrorCode()
                + "\n"
                + "OAuth error message: " + errResponse.getOauthErrorMessage();
    }
}
