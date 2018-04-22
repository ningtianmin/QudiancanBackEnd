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
import com.qudiancan.backend.pojo.po.RolePO;
import com.qudiancan.backend.pojo.po.ShopPO;
import com.qudiancan.backend.pojo.vo.shop.CreateAccountVO;
import com.qudiancan.backend.pojo.vo.shop.LoginVO;
import com.qudiancan.backend.pojo.vo.shop.RegisterVO;
import com.qudiancan.backend.repository.AccountRepository;
import com.qudiancan.backend.repository.BranchRepository;
import com.qudiancan.backend.repository.RoleRepository;
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
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author NINGTIANMIN
 */
@Service
@Slf4j
public class ShopAccountServiceImpl implements ShopAccountService {
    public static final String COMMA = ",";
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
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void sendSmsCaptcha(String phone, SmsCaptchaType smsCaptchaType) {
        log.info("[发送手机验证码]phone:{},smsCaptchaType:{}", phone, smsCaptchaType);
        // 验证字段合法性和有效性
        if (!ShopAccountServiceUtil.checkPhoneValidity(phone)) {
            throw new ShopException(ResponseEnum.SHOP_PHONE_ILLEGAL);
        }

        AccountPO accountPO = accountRepository.findByPhone(phone);
        String key;
        // 逻辑验证
        switch (smsCaptchaType) {
            case REGISTER:
                if (Objects.nonNull(accountPO)) {
                    log.warn("[发送手机验证码失败]{},{}", phone, smsCaptchaType);
                    throw new ShopException(ResponseEnum.SHOP_PHONE_OCCUPIED);
                }
                key = String.format(Constant.REDIS_SMS_CAPTCHA_KEY_PREFIX + "%S", phone);
                break;
            case RESET_PASSWORD:
                if (Objects.isNull(accountPO)) {
                    log.warn("[发送手机验证码失败]{},{}", phone, smsCaptchaType);
                    throw new ShopException(ResponseEnum.PARAM_INVALID, "不存在该手机号对应的账号");
                }
                key = String.format(Constant.REDIS_RESET_PASSWORD_SMS_CAPTCHA_KEY_PREFIX + "%S", phone);
                break;
            case CREATE_ACCOUNT:
                if (Objects.nonNull(accountPO)) {
                    throw new ShopException(ResponseEnum.PHONE_OCCUPIED);
                }
                key = String.format(Constant.REDIS_CREATE_ACCOUNT_SMS_CAPTCHA_KEY_PREFIX + "%S", phone);
                break;
            default:
                key = "";
                break;
        }

        String captcha = ShopAccountServiceUtil.generateSmsCaptcha();
        // 调用短信接口
        try {
            smsService.sendCaptcha(phone, captcha);
        } catch (ClientException e) {
            log.warn("[发送手机验证码失败]{}", e);
            throw new ShopException(ResponseEnum.SHOP_SMS_CAPTCHA_FAILURE);
        }
        redisTemplate.opsForValue().set(key, captcha, Constant.REDIS_SMS_CAPTCHA_EXPIRY, TimeUnit.MINUTES);
    }

    @Override
    public boolean verifySmsCaptcha(String phone, String captcha, SmsCaptchaType smsCaptchaType) {
        // TODO: 18/02/16 redis需释放资源
        String key;
        switch (smsCaptchaType) {
            case REGISTER:
                key = String.format(Constant.REDIS_SMS_CAPTCHA_KEY_PREFIX + "%S", phone);
                break;
            case RESET_PASSWORD:
                key = String.format(Constant.REDIS_RESET_PASSWORD_SMS_CAPTCHA_KEY_PREFIX + "%S", phone);
                break;
            case CREATE_ACCOUNT:
                key = String.format(Constant.REDIS_CREATE_ACCOUNT_SMS_CAPTCHA_KEY_PREFIX + "%S", phone);
                break;
            default:
                key = "";
                break;
        }
        String value = redisTemplate.opsForValue().get(key);
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
        if (!verifySmsCaptcha(registerVO.getPhone(), registerVO.getPhoneCaptcha(), SmsCaptchaType.REGISTER)) {
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
        shop.setStatus(ShopStatus.REMAIN_PERFECT.toString());
        shop.setTelephone(registerVO.getPhone());
        shopRepository.save(shop);
        AccountPO account = new AccountPO(null, registerVO.getShopId(), registerVO.getShopId(), registerVO.getPassword(),
                StringUtils.isEmpty(registerVO.getName()) ? registerVO.getEmail() : registerVO.getName(),
                registerVO.getEmail(), registerVO.getPhone(), null, null, ShopIsCreator.YES.name(), null);
        accountRepository.save(account);
        return new ShopAccountDTO(registerVO.getShopId(), registerVO.getShopId());
    }

    @Override
    public void resetPassword(String phone, String phoneCaptcha, String newPassword) {
        log.info("[重置密码]phone:{}，phoneCaptcha：{}，newPassword：{}", phone, phoneCaptcha, newPassword);
        // 验证字段合法性和有效性
        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(phoneCaptcha) || StringUtils.isEmpty(newPassword)) {
            throw new ShopException(ResponseEnum.PARAM_INCOMPLETE);
        }
        if (!ShopAccountServiceUtil.checkPasswordValidity(newPassword)) {
            throw new ShopException(ResponseEnum.SHOP_PARAM_WRONG, "密码格式有误");
        }
        if (!verifySmsCaptcha(phone, phoneCaptcha, SmsCaptchaType.RESET_PASSWORD)) {
            throw new ShopException(ResponseEnum.SHOP_PHONE_VERIFY_FAILURE);
        }

        // 逻辑验证
        AccountPO accountPO = accountRepository.findByPhone(phone);
        if (Objects.isNull(accountPO)) {
            throw new ShopException(ResponseEnum.PARAM_INVALID, "请检查phone");
        }

        // 持久化
        accountPO.setPassword(newPassword);
        accountRepository.save(accountPO);
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
        CookieUtil.set(response, Constant.COOKIE_ACCOUNT_SESSION, token, Constant.COOKIE_EXPIRY);
        CookieUtil.set(response, Constant.COOKIE_CURRENT_ACCOUNT_NAME, accountPO.getName(), Constant.COOKIE_EXPIRY);
        CookieUtil.set(response, Constant.COOKIE_CURRENT_SHOP_ID, accountPO.getShopId(), Constant.COOKIE_EXPIRY);

        if (shopRepository.findOne(accountPO.getShopId()).getStatus().equals(ShopStatus.REMAIN_PERFECT.name())) {
            throw new ShopException(ResponseEnum.SHOP_REMAIN_PERFECT);
        } else {
            BranchPO firstBranchPO = branchRepository.findFirstByShopId(accountPO.getShopId());
            if (accountPO.getIsCreator().equals(ShopIsCreator.YES.name())) {
                if (Objects.nonNull(firstBranchPO)) {
                    CookieUtil.set(response, Constant.COOKIE_CURRENT_BRANCH_ID, firstBranchPO.getId() + "", Constant.COOKIE_EXPIRY);
                }
            } else {
                CookieUtil.set(response, Constant.COOKIE_CURRENT_BRANCH_ID, accountPO.getBranchIds().split(",")[0], Constant.COOKIE_EXPIRY);
            }
        }
        return new ShopAccountTokenDTO(accountPO.getId(), token);
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        String token = CookieUtil.get(request, Constant.COOKIE_ACCOUNT_SESSION);
        redisTemplate.opsForValue().getOperations().delete(String.format(Constant.REDIS_ACCOUNT_KEY_PREFIX + "%S", token));
        CookieUtil.set(response, Constant.COOKIE_ACCOUNT_SESSION, null, 0);
        CookieUtil.set(response, Constant.COOKIE_CURRENT_ACCOUNT_NAME, null, 0);
        CookieUtil.set(response, Constant.COOKIE_CURRENT_BRANCH_ID, null, 0);
        CookieUtil.set(response, Constant.COOKIE_CURRENT_SHOP_ID, null, 0);
    }

    @Override
    public void updatePassword(Integer accountId, String oldPassword, String newPassword) {
        if (Objects.isNull(accountId) || StringUtils.isEmpty(oldPassword) || StringUtils.isEmpty(newPassword)) {
            throw new ShopException(ResponseEnum.BAD_REQUEST);
        }
        if (!ShopAccountServiceUtil.checkPasswordValidity(newPassword)) {
            throw new ShopException(ResponseEnum.SHOP_PARAM_WRONG);
        }
        AccountPO accountPO = accountRepository.findOne(accountId);
        if (Objects.isNull(accountPO)) {
            throw new ShopException(ResponseEnum.PARAM_INVALID);
        }
        if (!accountPO.getPassword().equals(oldPassword)) {
            throw new ShopException(ResponseEnum.BAD_REQUEST, "旧密码错误");
        }
        accountPO.setPassword(newPassword);
        accountRepository.save(accountPO);
    }

    @Override
    public void createAccount(Integer accountId, CreateAccountVO createAccountVO) {
        log.info("[账号创建]accountId:{},createAccountVO:{}", accountId, createAccountVO);
        // 验证字段合法性和有效性
        if (Objects.isNull(accountId) || Objects.isNull(createAccountVO)) {
            throw new ShopException(ResponseEnum.SHOP_NO_PARAM);
        }
        if (!verifySmsCaptcha(createAccountVO.getPhone(), createAccountVO.getPhoneCaptcha(), SmsCaptchaType.CREATE_ACCOUNT)) {
            throw new ShopException(ResponseEnum.SHOP_PHONE_VERIFY_FAILURE);
        }
        ShopAccountServiceUtil.checkCreateAccountVO(createAccountVO);

        // 逻辑验证
        AccountPO accountPO = accountRepository.findOne(accountId);
        if (Objects.isNull(accountPO)) {
            throw new ShopException(ResponseEnum.PARAM_INVALID, "accountId");
        }
        if (Objects.nonNull(accountRepository.findByPhone(createAccountVO.getPhone()))) {
            log.warn("[账户创建失败]{}", createAccountVO);
            throw new ShopException(ResponseEnum.PHONE_OCCUPIED);
        }

        Set<Integer> existBranchIds = branchRepository.findByShopId(accountPO.getShopId()).stream().map(BranchPO::getId).collect(Collectors.toSet());
        if (Arrays.stream(createAccountVO.getBranchIds().split(COMMA)).map(Integer::valueOf).anyMatch(o -> !existBranchIds.contains(o))) {
            throw new ShopException(ResponseEnum.PARAM_INVALID, "请确保所有门店id正确");
        }
        Set<Integer> existRoleIds = roleRepository.findAll().stream().map(RolePO::getId).collect(Collectors.toSet());
        if (Arrays.stream(createAccountVO.getRoleIds().split(COMMA)).map(Integer::valueOf).anyMatch(o -> !existRoleIds.contains(o))) {
            throw new ShopException(ResponseEnum.PARAM_INVALID, "请确保所有角色id正确");
        }

        // 持久化
        accountRepository.save(new AccountPO(null, accountPO.getShopId(), createAccountVO.getLoginId(), createAccountVO.getPassword(), createAccountVO.getName(), createAccountVO.getEmail(), createAccountVO.getPhone(),
                createAccountVO.getBranchIds(), createAccountVO.getRoleIds(), ShopIsCreator.NO.getKey(), null));
    }
}
