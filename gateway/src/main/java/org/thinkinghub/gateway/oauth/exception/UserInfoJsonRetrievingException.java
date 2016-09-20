package org.thinkinghub.gateway.oauth.exception;


@SuppressWarnings("serial")
public class UserInfoJsonRetrievingException extends GatewayException{
    public UserInfoJsonRetrievingException(Throwable e){
        super(e);
    }

    @Override
    public String getGWErrorCode(){
        return "GW30003";
    }
}
