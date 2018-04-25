package com.qudiancan.backend.service.wechat;

/**
 * @author NINGTIANMIN
 */
public interface WechatAccountService {

    /**
     * 获取微信用户openid
     *
     * @param jsCode 登录时获取的code
     * @return openid
     */
    String getOpenid(String jsCode);

    /**
     * 获取微信accessToken
     *
     * @param appid  微信小程序appid
     * @param secret 微信小程序secret
     * @return 微信accessToken
     */
    String getAccessToken(String appid, String secret);

    /**
     * 成为餐厅会员
     *
     * @param branchId 门店id
     * @param openid   微信用户openid
     */
    void beShopMember(Integer branchId, String openid);

    /**
     * 获取会员id
     *
     * @param branchId 门店id
     * @param openid   微信用户openid
     * @return 会员id
     */
    Integer getMemberIdByBranchIdAndOpenid(Integer branchId, String openid);

}
