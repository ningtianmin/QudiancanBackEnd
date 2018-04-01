package com.qudiancan.backend.service.util.shop;

import com.qudiancan.backend.enums.ResponseEnum;
import com.qudiancan.backend.enums.shop.ShopHolderType;
import com.qudiancan.backend.exception.ShopException;
import com.qudiancan.backend.pojo.vo.shop.ShopVO;
import org.springframework.util.StringUtils;

import java.util.regex.Pattern;

/**
 * @author NINGTIANMIN
 */
public class ShopServiceUtil {
    public static final Pattern SOCIAL_CREDIT_CODE_PATTERN = Pattern.compile("^[1-9A-GY][1239][1-5][0-9]{5}[0-9A-Z]{10}$");
    public static final Pattern NATIONAL_IDENTIFICATION_NUMBER_PATTERN = Pattern.compile("(^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{2}[0-9Xx]$)");

    public static void checkShopVO(ShopVO shopVO) {
        try {
            ShopHolderType shopHolderType = ShopHolderType.valueOf(shopVO.getHolderType());
            String holderIdentify = shopVO.getHolderIdentify();
            boolean holderIdentifyValidity = (ShopHolderType.COMPANY == shopHolderType && checkSocialCreditCodeValidity(holderIdentify)) ||
                    (ShopHolderType.PERSON == shopHolderType && checkNationalIdentificationNumberValidity(holderIdentify));
            if (!holderIdentifyValidity) {
                throw new ShopException(ResponseEnum.SHOP_PARAM_WRONG, "holderIdentify");
            }
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new ShopException(ResponseEnum.SHOP_PARAM_WRONG, "holderType");
        }
        if (StringUtils.isEmpty(shopVO.getHolderName())) {
            throw new ShopException(ResponseEnum.SHOP_PARAM_WRONG, "holderName");
        }
        if (StringUtils.isEmpty(shopVO.getIntroduction())) {
            throw new ShopException(ResponseEnum.SHOP_PARAM_WRONG, "introduction");
        }
        if (StringUtils.isEmpty(shopVO.getName())) {
            throw new ShopException(ResponseEnum.SHOP_PARAM_WRONG, "name");
        }
        if (!ShopAccountServiceUtil.checkPhoneValidity(shopVO.getTelephone())) {
            throw new ShopException(ResponseEnum.SHOP_PARAM_WRONG, "telephone");
        }
    }

    public static boolean checkSocialCreditCodeValidity(String socialCreditCode) {
        return !StringUtils.isEmpty(socialCreditCode) && SOCIAL_CREDIT_CODE_PATTERN.matcher(socialCreditCode).matches();
    }

    public static boolean checkNationalIdentificationNumberValidity(String nationalIdentificationNumber) {
        return !StringUtils.isEmpty(nationalIdentificationNumber) && NATIONAL_IDENTIFICATION_NUMBER_PATTERN.matcher(nationalIdentificationNumber).matches();
    }
}
