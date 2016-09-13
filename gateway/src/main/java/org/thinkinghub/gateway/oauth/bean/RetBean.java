package org.thinkinghub.gateway.oauth.bean;

import org.thinkinghub.gateway.oauth.entity.ServiceType;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
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
    private int errorCode;

    @JsonProperty("error_desc")
    private String errorDesc;

    public RetBean(int errorCode, String errorDesc, ServiceType service) {
        this.retCode = 1;
        this.errorCode = errorCode;
        this.errorDesc = errorDesc;
        this.service = service;
    }

    public RetBean(String userId, String nickname, String headImage, ServiceType service) {
        this.retCode = 0;
        this.userId = userId;
        this.nickname = nickname;
        this.headImage = headImage;
        this.service = service;
    }
}
