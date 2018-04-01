package com.qudiancan.backend.controller.wechat;

import com.qudiancan.backend.pojo.Response;
import com.qudiancan.backend.service.wechat.WechatAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author NINGTIANMIN
 */
@RestController
@RequestMapping("/wechat")
@Slf4j
public class WechatAccountController {
    @Autowired
    private WechatAccountService wechatAccountService;

    /**
     * 获取微信用户openid
     *
     * @param jsCode 登录时获取的code
     * @return openid
     */
    @GetMapping("/openid")
    public Response<String> getOpenid(String jsCode) {
        log.info("【getOpenid】jsCode：", jsCode);
        return Response.success(wechatAccountService.getOpenid(jsCode));
    }
}
