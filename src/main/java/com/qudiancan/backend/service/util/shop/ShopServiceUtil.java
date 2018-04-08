package com.qudiancan.backend.service.util.shop;

import com.qudiancan.backend.controller.shop.PerfectShopVO;
import com.qudiancan.backend.enums.ResponseEnum;
import com.qudiancan.backend.enums.shop.ShopHolderType;
import com.qudiancan.backend.exception.ShopException;
import com.qudiancan.backend.pojo.vo.shop.ShopVO;
import org.springframework.util.StringUtils;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * @author NINGTIANMIN
 */
public class ShopServiceUtil {
    public static final Pattern SOCIAL_CREDIT_CODE_PATTERN = Pattern.compile("^[1-9A-GY][1239][1-5][0-9]{5}[0-9A-Z]{10}$");
    public static final Pattern NATIONAL_IDENTIFICATION_NUMBER_PATTERN = Pattern.compile("(^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{2}[0-9Xx]$)");
    public static final Pattern SHOP_NAME_PATTERN = Pattern.compile("^[a-zA-Z\\u4E00-\\u9FA5]{2,16}$");
    public static final Pattern HOLDER_NAME_PATTERN = Pattern.compile("^[a-zA-Z\\u4E00-\\u9FA5]{2,16}$");
    public static final Pattern SHOP_INTRODUCTION_PATTERN = Pattern.compile("^[a-zA-Z\\u4E00-\\u9FA5]{2,50}$");
    public static final Pattern BRANCH_NAME_PATTERN = Pattern.compile("^[a-zA-Z\\u4E00-\\u9FA5]{2,16}$");
    public static final Pattern BRANCH_NOTICE_PATTERN = Pattern.compile("^[a-zA-Z\\u4E00-\\u9FA5]{2,50}$");
    public static final Pattern BRANCH_ADDRESS_PATTERN = Pattern.compile("^[a-zA-Z\\u4E00-\\u9FA5]{2,50}$");
    public static final Pattern BRANCH_INTRODUCTION_PATTERN = Pattern.compile("^[a-zA-Z\\u4E00-\\u9FA5]{2,50}$");

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

    public static void checkPerfectShopVO(PerfectShopVO perfectShopVO) {
        if (Objects.isNull(perfectShopVO)) {
            throw new ShopException(ResponseEnum.PARAM_INCOMPLETE);
        }
        try {
            ShopHolderType shopHolderType = ShopHolderType.valueOf(perfectShopVO.getShopHolderType());
            String holderIdentify = perfectShopVO.getHolderIdentify();
            boolean holderIdentifyValidity = (ShopHolderType.COMPANY == shopHolderType && checkSocialCreditCodeValidity(holderIdentify)) ||
                    (ShopHolderType.PERSON == shopHolderType && checkNationalIdentificationNumberValidity(holderIdentify));
            if (!holderIdentifyValidity) {
                throw new ShopException(ResponseEnum.SHOP_PARAM_WRONG, "holderIdentify");
            }
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new ShopException(ResponseEnum.SHOP_PARAM_WRONG, "holderType");
        }
        if (!checkBranchAddressValidity(perfectShopVO.getBranchAddress())) {
            throw new ShopException(ResponseEnum.WECHAT_WRONG_PARAM, "branchAddress");
        }
        if (!checkBranchIntroductionValidity(perfectShopVO.getBranchIntroduction())) {
            throw new ShopException(ResponseEnum.WECHAT_WRONG_PARAM, "branchIntroduction");
        }
        if (!checkShopIntroductionValidity(perfectShopVO.getShopIntroduction())) {
            throw new ShopException(ResponseEnum.WECHAT_WRONG_PARAM, "shopIntroduction");
        }
        if (!checkShopNameValidity(perfectShopVO.getShopName())) {
            throw new ShopException(ResponseEnum.WECHAT_WRONG_PARAM, "shopName");
        }
        if (!ShopBranchServiceUtil.checkLongitudeValidity(perfectShopVO.getBranchLongitude())) {
            throw new ShopException(ResponseEnum.WECHAT_WRONG_PARAM, "branchLongitude");
        }
        if (!ShopBranchServiceUtil.checkLatitudeValidity(perfectShopVO.getBranchLatitude())) {
            throw new ShopException(ResponseEnum.WECHAT_WRONG_PARAM, "branchLatitude");
        }
        if (!checkBranchNameValidity(perfectShopVO.getBranchName())) {
            throw new ShopException(ResponseEnum.WECHAT_WRONG_PARAM, "branchName");
        }
        if (!checkBranchNoticeValidity(perfectShopVO.getBranchNotice())) {
            throw new ShopException(ResponseEnum.WECHAT_WRONG_PARAM, "branchNotice");
        }
        if (!ShopAccountServiceUtil.checkPhoneValidity(perfectShopVO.getBranchPhone())) {
            throw new ShopException(ResponseEnum.WECHAT_WRONG_PARAM, "branchPhone");
        }
        if (!checkHolderNameValidity(perfectShopVO.getHolderName())) {
            throw new ShopException(ResponseEnum.WECHAT_WRONG_PARAM, "holderName");
        }
    }

    public static boolean checkSocialCreditCodeValidity(String socialCreditCode) {
        return !StringUtils.isEmpty(socialCreditCode) && SOCIAL_CREDIT_CODE_PATTERN.matcher(socialCreditCode).matches();
    }

    public static boolean checkNationalIdentificationNumberValidity(String nationalIdentificationNumber) {
        return !StringUtils.isEmpty(nationalIdentificationNumber) && NATIONAL_IDENTIFICATION_NUMBER_PATTERN.matcher(nationalIdentificationNumber).matches();
    }

    public static boolean checkShopNameValidity(String shopName) {
        return !StringUtils.isEmpty(shopName) && SHOP_NAME_PATTERN.matcher(shopName).matches();
    }

    public static boolean checkHolderNameValidity(String holderName) {
        return !StringUtils.isEmpty(holderName) && HOLDER_NAME_PATTERN.matcher(holderName).matches();
    }

    public static boolean checkShopIntroductionValidity(String shopIntroduction) {
        return !StringUtils.isEmpty(shopIntroduction) && SHOP_INTRODUCTION_PATTERN.matcher(shopIntroduction).matches();
    }

    public static boolean checkBranchNameValidity(String branchName) {
        return !StringUtils.isEmpty(branchName) && BRANCH_NAME_PATTERN.matcher(branchName).matches();
    }

    public static boolean checkBranchNoticeValidity(String branchNotice) {
        return !StringUtils.isEmpty(branchNotice) && BRANCH_NOTICE_PATTERN.matcher(branchNotice).matches();
    }

    public static boolean checkBranchAddressValidity(String branchAddress) {
        return !StringUtils.isEmpty(branchAddress) && BRANCH_ADDRESS_PATTERN.matcher(branchAddress).matches();
    }

    public static boolean checkBranchIntroductionValidity(String branchIntroduction) {
        return !StringUtils.isEmpty(branchIntroduction) && BRANCH_INTRODUCTION_PATTERN.matcher(branchIntroduction).matches();
    }

}
