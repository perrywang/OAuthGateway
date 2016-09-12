package org.thinkinghub.gateway.oauth.bean;

import org.thinkinghub.gateway.oauth.entity.ServiceType;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class RetBean {

    @JsonProperty("return_code")
    private String retCode; // 0: success, 1: failure

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("uid")
    private String userId;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String nickname;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String headImage;

    private ServiceType service;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("error_code")
    private String errorCode;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("error_desc")
    private String errorDesc;

    public RetBean(String errorCode, String errorDesc, ServiceType service) {
        this.retCode = "1";
        this.errorCode = errorCode;
        this.errorDesc = errorDesc;
        this.service = service;
    }

    public RetBean(String userId, String nickname, String headImage, ServiceType service) {
        this.retCode = "0";
        this.userId = userId;
        this.nickname = nickname;
        this.headImage = headImage;
        this.service = service;
    }
}
