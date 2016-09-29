package org.thinkinghub.gateway.oauth.response;

import org.thinkinghub.gateway.oauth.entity.ErrorType;
import org.thinkinghub.gateway.oauth.entity.ServiceType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ErrorResponse {
    //This GW error code represents in which process oauthgateway throws exception
    String gwErrorCode;
    String gwErrorMessage;
    String oauthError;
    //This OAuth error code represents the original code from third-party
    String oauthErrorCode;
    String oauthErrorMessage;
    ErrorType errorType;
    ServiceType serviceType;

    public ErrorResponse gwErrorCode(String gwErrorCode) {
        this.gwErrorCode = gwErrorCode;
        return this;
    }

    public ErrorResponse gwErrorMessage(String gwErrorMessage){
        this.gwErrorMessage  = gwErrorMessage;
        return this;
    }

    public ErrorResponse oauthError(String oauthError){
        this.oauthError = oauthError;
        return this;
    }

    public ErrorResponse oauthErrorCode(String oauthErrorCode){
        this.oauthErrorCode = oauthErrorCode;
        return this;
    }

    public ErrorResponse oauthErrorMessage(String oauthErrorMessage){
        this.oauthErrorMessage = oauthErrorMessage;
        return this;
    }

    public ErrorResponse errorType(ErrorType errorType){
        this.errorType = errorType;
        return this;
    }

    public ErrorResponse serviceType(ServiceType serviceType){
        this.serviceType = serviceType;
        return this;
    }

    @Override
    public String toString() {
        return "{\"retCode\":" + 1
                + "," + "\"errorType\":" + errorType
                + "\"," + "\"serviceType\":\"" + serviceType
                + "," + "\"gwErrorCode\":" + gwErrorCode
                + "," + "\"gwErrorMessage\":\"" + gwErrorMessage
                + "," + "\"oauthError\":\"" + oauthError
                + "," + "\"oauthErrorCode\":\"" + oauthErrorCode
                + "\"," + "\"oauthErrorMessage\":\"" + oauthErrorMessage
                + "\"}";
    }
}
