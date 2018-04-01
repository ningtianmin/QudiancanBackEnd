package com.qudiancan.backend.service;

import com.qudiancan.backend.BackEndApplicationTests;
import com.qudiancan.backend.service.shop.ShopAccountService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author NINGTIANMIN
 */
public class ShopAccountServiceTest extends BackEndApplicationTests {
    @Autowired
    private ShopAccountService shopAccountService;

    @Test
    public void testSendSmsCaptcha() {
        // shopAccountService.sendSmsCaptcha("18607967421", SmsCaptchaType.REGISTER);
    }

    @Test
    public void testVerifySmsCaptcha() {
        // shopAccountService.verifySmsCaptcha("18607967421", "");
    }

    @Test
    public void testRegister() {
    }
}