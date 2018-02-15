package com.qudiancan.backend.service.impl;

import com.aliyuncs.exceptions.ClientException;
import com.qudiancan.backend.common.Constant;
import com.qudiancan.backend.enums.IsCreator;
import com.qudiancan.backend.enums.ResponseEnum;
import com.qudiancan.backend.enums.ShopStatus;
import com.qudiancan.backend.enums.SmsCaptchaType;
import com.qudiancan.backend.exception.ShopException;
import com.qudiancan.backend.pojo.dto.AccountDTO;
import com.qudiancan.backend.pojo.dto.AccountTokenDTO;
import com.qudiancan.backend.pojo.po.AccountPO;
import com.qudiancan.backend.pojo.po.ShopPO;
import com.qudiancan.backend.pojo.vo.LoginVO;
import com.qudiancan.backend.pojo.vo.RegisterVO;
import com.qudiancan.backend.repository.AccountRepository;
import com.qudiancan.backend.repository.ShopRepository;
import com.qudiancan.backend.service.AccountService;
import com.qudiancan.backend.service.SmsService;
import com.qudiancan.backend.service.util.AccountServiceUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author NINGTIANMIN
 */
@Service
@Slf4j
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ShopRepository shopRepository;
    @Autowired
    private SmsService smsService;
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public void sendSmsCaptcha(String phone, SmsCaptchaType smsCaptchaType) {
        log.info("[发送验证码]{},{}", phone, smsCaptchaType);
        // 验证字段合法性和有效性
        if (!AccountServiceUtil.checkPhoneValidity(phone)) {
            throw new ShopException(ResponseEnum.SHOP_PHONE_ILLEGAL);
        }

        // 逻辑验证
        if (SmsCaptchaType.REGISTER.equals(smsCaptchaType)) {
            if (accountRepository.findByPhone(phone) != null) {
                log.warn("[发送验证码失败]{},{}", phone, smsCaptchaType);
                throw new ShopException(ResponseEnum.SHOP_PHONE_OCCUPIED);
            }
        }

        String captcha = AccountServiceUtil.generateSmsCaptcha();
        // 调用短信接口
        try {
            smsService.sendCaptcha(phone, captcha);
        } catch (ClientException e) {
            log.warn("[发送验证码失败]{}", e);
            throw new ShopException(ResponseEnum.SHOP_SMS_CAPTCHA_FAILURE);
        }
        // 存放验证码在redis
        redisTemplate.opsForValue().set(String.format(Constant.REDIS_SMS_CAPTCHA_KEY_PREFIX + "%S", phone),
                captcha, Constant.REDIS_SMS_CAPTCHA_EXPIRY, TimeUnit.MINUTES);
    }

    @Override
    public boolean verifySmsCaptcha(String phone, String captcha) {
        String value = redisTemplate.opsForValue().get(String.format(Constant.REDIS_SMS_CAPTCHA_KEY_PREFIX + "%S", phone));
        return !StringUtils.isEmpty(value) && value.equals(captcha);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public AccountDTO register(RegisterVO registerVO) {
        log.info("[注册]{}", registerVO);
        // 验证字段合法性和有效性
        if (registerVO == null) {
            throw new ShopException(ResponseEnum.SHOP_NO_PARAM);
        }
        if (!verifySmsCaptcha(registerVO.getPhone(), registerVO.getPhoneCaptcha())) {
            throw new ShopException(ResponseEnum.SHOP_PHONE_VERIFY_FAILURE);
        }
        AccountServiceUtil.checkRegisterVO(registerVO);

        // 逻辑验证
        if (shopRepository.findOne(registerVO.getShopId()) != null) {
            log.warn("[注册失败]{}", registerVO);
            throw new ShopException(ResponseEnum.SHOP_ID_OCCUPIED);
        }
        if (accountRepository.findByPhone(registerVO.getPhone()) != null) {
            log.warn("[注册失败]{}", registerVO);
            throw new ShopException(ResponseEnum.SHOP_PHONE_OCCUPIED);
        }

        // 持久化
        ShopPO shop = new ShopPO();
        shop.setId(registerVO.getShopId());
        shop.setStatus(ShopStatus.NEW.toString());
        shopRepository.save(shop);
        AccountPO account = new AccountPO(null, registerVO.getShopId(), registerVO.getShopId(), registerVO.getPassword(),
                StringUtils.isEmpty(registerVO.getName()) ? registerVO.getPhone() : registerVO.getName(),
                registerVO.getEmail(), registerVO.getPhone(), null, null, IsCreator.YES.name(), null);
        accountRepository.save(account);
        return new AccountDTO(registerVO.getShopId(), registerVO.getShopId());
    }

    @Override
    public AccountTokenDTO login(LoginVO loginVO) {
        log.info("[登录]{}", loginVO);
        if (loginVO == null) {
            throw new ShopException(ResponseEnum.SHOP_NO_PARAM);
        }
        AccountPO accountPO = accountRepository.findByShopIdAndLoginIdAndPassword(loginVO.getShopId(), loginVO.getLoginId(), loginVO.getPassword());
        if (accountPO == null) {
            log.warn("[登录失败]{}", loginVO);
            throw new ShopException(ResponseEnum.SHOP_LOGIN_FAILURE);
        }
        // 生成token存放于redis
        String token = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set(String.format(Constant.REDIS_ACCOUNT_KEY_PREFIX + "%s", token), String.valueOf(accountPO.getId()), Constant.REDIS_ACCOUNT_EXPIRY, TimeUnit.MINUTES);
        return new AccountTokenDTO(accountPO.getId(), token);
    }
}
