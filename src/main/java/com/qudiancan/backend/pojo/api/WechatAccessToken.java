package com.qudiancan.backend.pojo.api;

import lombok.Data;

/**
 * @author NINGTIANMIN
 */
@Data
public class WechatAccessToken {

    private String access_token;
    private Integer expires_in;
    private Integer errcode;
    private String errmsg;

}
