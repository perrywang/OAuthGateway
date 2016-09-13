package org.thinkinghub.gateway.oauth.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.thinkinghub.gateway.oauth.entity.ServiceType;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RetBean {
    @JsonProperty("return_code")
    private int retCode; // 0: success, 1: failure

    @JsonProperty("uid")
    private String userId;

    private String nickname;

    private String headImage;

    private ServiceType service;

    @JsonProperty("error_code")
    private String errorCode;

    @JsonProperty("error_desc")
    private String errorDesc;

    @JsonIgnore
    private String rawResponse;

    public RetBean(String errorCode, String errorDesc, ServiceType service, String rawResponse) {
        this.retCode = 1;
        this.errorCode = errorCode;
        this.errorDesc = errorDesc;
        this.service = service;
        this.rawResponse = rawResponse;
    }

    public RetBean(String userId, String nickname, String headImage, ServiceType service, String rawResponse) {
        this.retCode = 0;
        this.userId = userId;
        this.nickname = nickname;
        this.headImage = headImage;
        this.service = service;
        this.rawResponse = rawResponse;
    }
}
