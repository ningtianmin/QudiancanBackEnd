package com.qudiancan.backend.service.impl.wechat;

import com.qudiancan.backend.common.WechatConfig;
import com.qudiancan.backend.enums.ResponseEnum;
import com.qudiancan.backend.exception.WechatException;
import com.qudiancan.backend.pojo.api.WechatOpenid;
import com.qudiancan.backend.service.wechat.WechatAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @author NINGTIANMIN
 */
@Service
@Slf4j
public class WechatAccountServiceImpl implements WechatAccountService {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private WechatConfig wechatConfig;

    @Override
    public String getOpenid(String jsCode) {
        log.info("【getOpenid】jsCode：", jsCode);
        if (StringUtils.isEmpty(jsCode)) {
            throw new WechatException(ResponseEnum.WECHAT_WRONG_PARAM, "jsCode");
        }
        String url = String.format("%s/sns/jscode2session?appid={appid}&secret={secret}&js_code={jsCode}&grant_type={grantType}", wechatConfig.getApiUrl());
        Map<String, String> uriVariables = new HashMap<>(4);
        uriVariables.put("appid", wechatConfig.getAppId());
        uriVariables.put("secret", wechatConfig.getAppSecret());
        uriVariables.put("grantType", wechatConfig.getGrantType());
        uriVariables.put("jsCode", jsCode);
        return restTemplate.getForObject(url, WechatOpenid.class, uriVariables).getOpenid();
    }
}
