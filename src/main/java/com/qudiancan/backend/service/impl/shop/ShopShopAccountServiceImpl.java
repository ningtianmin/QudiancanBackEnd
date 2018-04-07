package com.qudiancan.backend.service.impl.shop;

import com.aliyuncs.exceptions.ClientException;
import com.qudiancan.backend.common.Constant;
import com.qudiancan.backend.enums.ResponseEnum;
import com.qudiancan.backend.enums.SmsCaptchaType;
import com.qudiancan.backend.enums.shop.ShopIsCreator;
import com.qudiancan.backend.enums.shop.ShopStatus;
import com.qudiancan.backend.exception.ShopException;
import com.qudiancan.backend.pojo.dto.shop.ShopAccountDTO;
import com.qudiancan.backend.pojo.dto.shop.ShopAccountTokenDTO;
import com.qudiancan.backend.pojo.po.AccountPO;
import com.qudiancan.backend.pojo.po.BranchPO;
import com.qudiancan.backend.pojo.po.ShopPO;
import com.qudiancan.backend.pojo.vo.shop.LoginVO;
import com.qudiancan.backend.pojo.vo.shop.RegisterVO;
import com.qudiancan.backend.repository.AccountRepository;
import com.qudiancan.backend.repository.BranchRepository;
import com.qudiancan.backend.repository.ShopRepository;
import com.qudiancan.backend.service.SmsService;
import com.qudiancan.backend.service.shop.ShopAccountService;
import com.qudiancan.backend.service.util.shop.ShopAccountServiceUtil;
import com.qudiancan.backend.util.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author NINGTIANMIN
 */
@Service
@Slf4j
public class ShopShopAccountServiceImpl implements ShopAccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ShopRepository shopRepository;
    @Autowired
    private SmsService smsService;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private BranchRepository branchRepository;

    @Override
    public void sendSmsCaptcha(String phone, SmsCaptchaType smsCaptchaType) {
        log.info("[发送手机验证码]phone:{},smsCaptchaType:{}", phone, smsCaptchaType);
        // 验证字段合法性和有效性
        if (!ShopAccountServiceUtil.checkPhoneValidity(phone)) {
            throw new ShopException(ResponseEnum.SHOP_PHONE_ILLEGAL);
        }

        // 逻辑验证
        if (SmsCaptchaType.REGISTER.equals(smsCaptchaType)) {
            if (accountRepository.findByPhone(phone) != null) {
                log.warn("[发送手机验证码失败]{},{}", phone, smsCaptchaType);
                throw new ShopException(ResponseEnum.SHOP_PHONE_OCCUPIED);
            }
        }

        String captcha = ShopAccountServiceUtil.generateSmsCaptcha();
        // 调用短信接口
        try {
            smsService.sendCaptcha(phone, captcha);
        } catch (ClientException e) {
            log.warn("[发送手机验证码失败]{}", e);
            throw new ShopException(ResponseEnum.SHOP_SMS_CAPTCHA_FAILURE);
        }
        // 存放验证码在redis
        // TODO: 18/02/16 redis需释放资源
        redisTemplate.opsForValue().set(String.format(Constant.REDIS_SMS_CAPTCHA_KEY_PREFIX + "%S", phone),
                captcha, Constant.REDIS_SMS_CAPTCHA_EXPIRY, TimeUnit.MINUTES);
    }

    @Override
    public boolean verifySmsCaptcha(String phone, String captcha) {
        // TODO: 18/02/16 redis需释放资源
        String value = redisTemplate.opsForValue().get(String.format(Constant.REDIS_SMS_CAPTCHA_KEY_PREFIX + "%S", phone));
        return !StringUtils.isEmpty(value) && value.equals(captcha);
    }

    @Override
    @Transactional(rollbackOn = {Exception.class})
    public ShopAccountDTO register(RegisterVO registerVO) {
        log.info("[账户注册]registerVO:{}", registerVO);
        // 验证字段合法性和有效性
        if (Objects.isNull(registerVO)) {
            throw new ShopException(ResponseEnum.SHOP_NO_PARAM);
        }
        if (!verifySmsCaptcha(registerVO.getPhone(), registerVO.getPhoneCaptcha())) {
            throw new ShopException(ResponseEnum.SHOP_PHONE_VERIFY_FAILURE);
        }
        ShopAccountServiceUtil.checkRegisterVO(registerVO);

        // 逻辑验证
        if (Objects.nonNull(shopRepository.findOne(registerVO.getShopId()))) {
            log.warn("[账户注册失败]{}", registerVO);
            throw new ShopException(ResponseEnum.SHOP_ID_OCCUPIED);
        }
        if (Objects.nonNull(accountRepository.findByPhone(registerVO.getPhone()))) {
            log.warn("[账户注册失败]{}", registerVO);
            throw new ShopException(ResponseEnum.SHOP_PHONE_OCCUPIED);
        }

        // 持久化
        ShopPO shop = new ShopPO();
        shop.setId(registerVO.getShopId());
        shop.setStatus(ShopStatus.NEW.toString());
        shopRepository.save(shop);
        AccountPO account = new AccountPO(null, registerVO.getShopId(), registerVO.getShopId(), registerVO.getPassword(),
                StringUtils.isEmpty(registerVO.getName()) ? registerVO.getPhone() : registerVO.getName(),
                registerVO.getEmail(), registerVO.getPhone(), null, null, ShopIsCreator.YES.name(), null);
        accountRepository.save(account);
        return new ShopAccountDTO(registerVO.getShopId(), registerVO.getShopId());
    }

    @Override
    public ShopAccountTokenDTO login(LoginVO loginVO, HttpServletResponse response) {
        log.info("[账户登录]loginVO:{}", loginVO);
        AccountPO accountPO = accountRepository.findByShopIdAndLoginIdAndPassword(loginVO.getShopId(), loginVO.getLoginId(), loginVO.getPassword());
        if (Objects.isNull(accountPO)) {
            log.warn("[账户登录失败]{}", loginVO);
            throw new ShopException(ResponseEnum.SHOP_LOGIN_FAILURE);
        }
        // 生成token存放于redis
        String token = UUID.randomUUID().toString();
        // TODO: 18/02/16 redis需释放资源
        redisTemplate.opsForValue().set(String.format(Constant.REDIS_ACCOUNT_KEY_PREFIX + "%s", token),
                String.valueOf(accountPO.getId()), Constant.REDIS_ACCOUNT_EXPIRY, TimeUnit.MINUTES);
        // 存放token于cookie
        CookieUtil.set(response, Constant.COOKIE_ACCOUNT_SESSION, token, Constant.COOKIE_ACCOUNT_SESSION_EXPIRY);
        CookieUtil.set(response, Constant.MERCHANT_CURRENT_ACCOUNT_NAME, accountPO.getName(), Constant.COOKIE_ACCOUNT_SESSION_EXPIRY);
        CookieUtil.set(response, Constant.MERCHANT_CURRENT_SHOP_ID, accountPO.getShopId(), Constant.COOKIE_ACCOUNT_SESSION_EXPIRY);

        if (shopRepository.findOne(accountPO.getShopId()).getStatus().equals(ShopStatus.NEW.name())) {
            throw new ShopException(ResponseEnum.BRANCH_NEED_CREATED);
        } else {
            BranchPO firstBranchPO = branchRepository.findFirstByShopId(accountPO.getShopId());
            if (accountPO.getIsCreator().equals(ShopIsCreator.YES.name())) {
                if (Objects.nonNull(firstBranchPO)) {
                    CookieUtil.set(response, Constant.MERCHANT_CURRENT_BRANCH_ID, firstBranchPO.getId() + "", Constant.COOKIE_ACCOUNT_SESSION_EXPIRY);
                }
            } else {
                CookieUtil.set(response, Constant.MERCHANT_CURRENT_BRANCH_ID, accountPO.getBranchIds().split(",")[0], Constant.COOKIE_ACCOUNT_SESSION_EXPIRY);
            }
        }
        return new ShopAccountTokenDTO(accountPO.getId(), token);
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        String token = CookieUtil.get(request, Constant.COOKIE_ACCOUNT_SESSION);
        if (Objects.nonNull(token)) {
            redisTemplate.opsForValue().getOperations().delete(String.format(Constant.REDIS_ACCOUNT_KEY_PREFIX + "%S", token));
            CookieUtil.set(response, Constant.COOKIE_ACCOUNT_SESSION, null, 0);
        }
    }
}
