package com.qudiancan.backend.controller.wechat;

import com.qudiancan.backend.pojo.Response;
import com.qudiancan.backend.pojo.po.CartProductPO;
import com.qudiancan.backend.service.wechat.WechatCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author NINGTIANMIN
 */
@RestController
@RequestMapping("/wechat")
@Slf4j
public class WechatCartController {
    @Autowired
    private WechatCartService wechatCartService;

    /**
     * 获取购物车
     *
     * @param openid 微信用户openid
     * @return 购物车
     */
    @GetMapping("/branches/{branchId}/cart")
    public Response<List<CartProductPO>> getCart(@PathVariable Integer branchId, @RequestParam String openid) {
        // TODO: 18/04/02 做一个切面验证openid时效
        log.info("【获取购物车】branchId：{},openid：{}", branchId, openid);
        return Response.success(wechatCartService.getCart(branchId, openid));
    }

    /**
     * 清空购物车
     *
     * @param branchId 门店id
     * @param openid   微信用户openid
     */
    @PostMapping("/branches/{branchId}/clearCart")
    public Response clearCart(@PathVariable Integer branchId, @RequestParam String openid) {
        log.info("【清空购物车】branchId：{}，openid：{}", branchId, openid);
        wechatCartService.clearCart(branchId, openid);
        return Response.success();
    }

    /**
     * 减少购物车产品数量
     *
     * @param branchId  门店id
     * @param openid    微信用户id
     * @param productId 产品id
     */
    @PostMapping("/branches/{branchId}/cart/subtractNum")
    public Response subtractNum(@PathVariable Integer branchId, @RequestParam String openid, @RequestParam Integer productId) {
        log.info("【减少购物车产品数量】branchId：{}，openid：{}，productId：{}", branchId, openid, productId);
        wechatCartService.subtractNum(branchId, openid, productId);
        return Response.success();
    }

    /**
     * 增加购物车产品数量
     *
     * @param branchId  门店id
     * @param openid    微信用户id
     * @param productId 产品id
     */
    @PostMapping("/branches/{branchId}/cart/addNum")
    public Response addNum(@PathVariable Integer branchId, @RequestParam String openid, @RequestParam Integer productId) {
        log.info("【增加购物车产品数量】branchId：{}，openid：{}，productId：{}", branchId, openid, productId);
        wechatCartService.addNum(branchId, openid, productId);
        return Response.success();
    }
}
