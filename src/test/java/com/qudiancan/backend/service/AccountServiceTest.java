package com.qudiancan.backend.service;

import com.qudiancan.backend.BackEndApplicationTests;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author NINGTIANMIN
 */
public class AccountServiceTest extends BackEndApplicationTests {
    @Autowired
    private AccountService accountService;

    @Test
    public void testSendSmsCaptcha() {
        // accountService.sendSmsCaptcha("18607967421", SmsCaptchaType.REGISTER);
    }

    @Test
    public void testVerifySmsCaptcha() {
        // accountService.verifySmsCaptcha("18607967421", "");
    }

    @Test
    public void testRegister() {
    }
}