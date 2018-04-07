package com.qudiancan.backend.service.util.shop;

import com.qudiancan.backend.common.Constant;
import com.qudiancan.backend.enums.ResponseEnum;
import com.qudiancan.backend.exception.ShopException;
import com.qudiancan.backend.pojo.vo.shop.RegisterVO;
import org.springframework.util.StringUtils;

import java.util.Random;
import java.util.regex.Pattern;

/**
 * @author NINGTIANMIN
 */
public class ShopAccountServiceUtil {

    public static final Pattern PHONE_PATTERN = Pattern.compile("^1(3[0-9]|4[57]|5[0-35-9]|7[0135678]|8[0-9])\\d{8}$");
    public static final Pattern ID_PATTERN = Pattern.compile("^[a-zA-Z][a-zA-Z0-9_-]{2,15}$");
    public static final Pattern EMAIL_PATTERN = Pattern.compile("^([A-Za-z0-9_.-])+@([A-Za-z0-9_.-])+\\.([A-Za-z]{2,4})$");
    public static final Pattern PASSWORD_PATTERN = Pattern.compile("^[a-zA-Z0-9?.-_]{6,16}$");
    public static final Random SMS_CAPTCHA_RANDOM = new Random();

    public static void checkRegisterVO(RegisterVO registerVO) {
        if (!checkIdValidity(registerVO.getShopId())) {
            throw new ShopException(ResponseEnum.SHOP_WRONG_ID_PATTERN);
        }
        if (!checkPasswordValidity(registerVO.getPassword())) {
            throw new ShopException(ResponseEnum.SHOP_WRONG_PASSWORD_PATTERN);
        }
        if (!checkEmailValidity(registerVO.getEmail())) {
            throw new ShopException(ResponseEnum.SHOP_WRONG_EMAIL_PATTERN);
        }
    }

    public static String generateSmsCaptcha() {
        int temp = (int) Math.pow(10, Constant.SMS_CAPTCHA_LENGTH - 1);
        return String.valueOf(temp + SMS_CAPTCHA_RANDOM.nextInt(9 * temp));
    }

    public static boolean checkPhoneValidity(String phone) {
        return !StringUtils.isEmpty(phone) && PHONE_PATTERN.matcher(phone).matches();
    }

    public static boolean checkIdValidity(String id) {
        return !StringUtils.isEmpty(id) && ID_PATTERN.matcher(id).matches();
    }

    public static boolean checkPasswordValidity(String password) {
        return !StringUtils.isEmpty(password) && PASSWORD_PATTERN.matcher(password).matches();
    }

    public static boolean checkEmailValidity(String email) {
        return !StringUtils.isEmpty(email) && EMAIL_PATTERN.matcher(email).matches();
    }

}
