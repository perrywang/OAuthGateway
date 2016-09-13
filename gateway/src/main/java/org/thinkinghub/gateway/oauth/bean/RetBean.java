package org.thinkinghub.gateway.oauth.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RetBean {
    @JsonProperty("return_code")
    private String retCode; // 0: success, 1: failure

    @JsonProperty("uid")
    private String userId;

    private String nickname;

    private String headImage;

    private Enum service;

    @JsonProperty("error_code")
    private String errorCode;

    @JsonProperty("error_desc")
    private String errorDesc;

    public RetBean(String errorCode, String errorDesc, Enum service) {
        this.retCode = "1";
        this.errorCode = errorCode;
        this.errorDesc = errorDesc;
        this.service = service;
    }

    public RetBean(String userId, String nickname, String headImage, Enum service) {
        this.retCode = "0";
        this.userId = userId;
        this.nickname = nickname;
        this.headImage = headImage;
        this.service = service;
    }
}
