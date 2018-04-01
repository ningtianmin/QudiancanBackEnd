package com.qudiancan.backend.service.wechat;

/**
 * @author NINGTIANMIN
 */
public interface WechatAccountService {
    /**
     * 获取微信用户openid
     *
     * @param jsCode 登录时获取的code
     * @return openid
     */
    String getOpenid(String jsCode);
}
