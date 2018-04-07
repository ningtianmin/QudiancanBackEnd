package com.qudiancan.backend.common;

/**
 * @author NINGTIANMIN
 */
public interface Constant {

    Integer SMS_CAPTCHA_LENGTH = 6;
    String REDIS_SMS_CAPTCHA_KEY_PREFIX = "sms_captcha_token_";
    Integer REDIS_SMS_CAPTCHA_EXPIRY = 5;
    String REDIS_ACCOUNT_KEY_PREFIX = "account_token_";
    Integer REDIS_ACCOUNT_EXPIRY = 60;
    String COOKIE_ACCOUNT_SESSION = "accountSession";
    Integer COOKIE_ACCOUNT_SESSION_EXPIRY = 100;
    String MERCHANT_CURRENT_ACCOUNT_NAME = "currentAccountName";
    String MERCHANT_CURRENT_SHOP_ID = "currentShopId";
    String MERCHANT_CURRENT_BRANCH_ID = "currentBranchId";

}
