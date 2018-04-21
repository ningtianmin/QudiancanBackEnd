package com.qudiancan.backend.service;

import com.aliyuncs.exceptions.ClientException;
import com.qudiancan.backend.BackEndApplicationTests;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;

/**
 * @author NINGTIANMIN
 */
@Transactional
public class SmsServiceTest extends BackEndApplicationTests {
    @Autowired
    private SmsService smsService;

    @Test
    public void testSendCaptcha() throws ClientException {
        // smsService.sendCaptcha("18607967421", "666666");
    }
}