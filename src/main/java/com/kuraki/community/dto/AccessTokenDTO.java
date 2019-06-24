package com.kuraki.community.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class AccessTokenDTO {

    @JSONField(name = "client_id")
    private String clientId;

    @JSONField(name = "client_secret")
    private String clientSecret;

    private String code;

    @JSONField(name = "redirect_uri")
    private String redirectUri;

    private String state;
}
