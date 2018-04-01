package com.qudiancan.backend.pojo.api;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author NINGTIANMIN
 */
@Data
@NoArgsConstructor
public class WechatOpenid {
    private Integer errcode;
    private String errmsg;
    private String openid;
    private String session_key;
}
