package com.qudiancan.backend.common;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author NINGTIANMIN
 */
@ConfigurationProperties(prefix = "wechat")
@Component
@Data
public class WechatConfig {
    private String appId;
    private String appSecret;
    private String grantType;
    private String apiUrl;
    private String accessTokenGrantType;
    private String accessTokenUrl;
    private String wxacodeUrl;
}
