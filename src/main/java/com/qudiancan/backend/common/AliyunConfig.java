package com.qudiancan.backend.common;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author NINGTIANMIN
 */
@ConfigurationProperties(prefix = "aliyun")
@Component
@Data
public class AliyunConfig {
    private String accesskeyId;
    private String accessKeySecret;
    private String smsSignName;
    private String smsTemplateCode;
}
