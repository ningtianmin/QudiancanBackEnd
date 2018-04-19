package com.qudiancan.backend.common;

/**
 * @author NINGTIANMIN
 */
public interface Constant {

    Integer SMS_CAPTCHA_LENGTH = 6;
    String REDIS_SMS_CAPTCHA_KEY_PREFIX = "sms_captcha_token_";
    String REDIS_RESET_PASSWORD_SMS_CAPTCHA_KEY_PREFIX = "reset_password_sms_captcha_token_";
    Integer REDIS_SMS_CAPTCHA_EXPIRY = 5;
    String REDIS_ACCOUNT_KEY_PREFIX = "account_token_";
    Integer REDIS_ACCOUNT_EXPIRY = 60;

    Integer COOKIE_EXPIRY = 100;
    String COOKIE_ACCOUNT_SESSION = "accountSession";
    String COOKIE_CURRENT_ACCOUNT_NAME = "currentAccountName";
    String COOKIE_CURRENT_SHOP_ID = "currentShopId";
    String COOKIE_CURRENT_BRANCH_ID = "currentBranchId";

    String CLIENT_CONSTANTS_NAME = "constants";

}
