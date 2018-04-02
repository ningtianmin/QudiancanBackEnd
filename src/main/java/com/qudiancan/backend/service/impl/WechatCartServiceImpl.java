package com.qudiancan.backend.service.impl;

import com.qudiancan.backend.enums.ResponseEnum;
import com.qudiancan.backend.enums.shop.ShopBranchProductStatus;
import com.qudiancan.backend.exception.WechatException;
import com.qudiancan.backend.pojo.po.BranchPO;
import com.qudiancan.backend.pojo.po.BranchProductPO;
import com.qudiancan.backend.pojo.po.CartPO;
import com.qudiancan.backend.pojo.po.CartProductPO;
import com.qudiancan.backend.repository.BranchProductRepository;
import com.qudiancan.backend.repository.BranchRepository;
import com.qudiancan.backend.repository.CartProductRepository;
import com.qudiancan.backend.repository.CartRepository;
import com.qudiancan.backend.service.wechat.WechatCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author NINGTIANMIN
 */
@Service
@Slf4j
public class WechatCartServiceImpl implements WechatCartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartProductRepository cartProductRepository;
    @Autowired
    private BranchRepository branchRepository;
    @Autowired
    private BranchProductRepository branchProductRepository;

    @Override
    public List<CartProductPO> getCart(Integer branchId, String openid) {
        log.info("【获取购物车】branchId：{}，openid：{}", branchId, openid);
        String nullOpenid = "null";
        if (Objects.isNull(branchId) || StringUtils.isEmpty(openid) || nullOpenid.equals(openid)) {
            throw new WechatException(ResponseEnum.WECHAT_WRONG_PARAM, "branchId，openid");
        }
        CartPO cartPO = cartRepository.findByBranchIdAndWechatId(branchId, openid);
        if (Objects.isNull(cartPO)) {
            BranchPO branchPO = branchRepository.findOne(branchId);
            if (Objects.isNull(branchPO)) {
                throw new WechatException(ResponseEnum.WECHAT_WRONG_PARAM, "branchId");
            }
            cartPO = new CartPO(null, branchId, openid);
            cartRepository.save(cartPO);
            return Collections.emptyList();
        }
        return cartProductRepository.findByCartId(cartPO.getId());
    }

    @Transactional(rollbackOn = {Exception.class})
    @Override
    public void clearCart(Integer branchId, String openid) {
        CartPO cartPO = cartRepository.findByBranchIdAndWechatId(branchId, openid);
        if (Objects.isNull(cartPO)) {
            throw new WechatException(ResponseEnum.WECHAT_BAD_REQUEST, "购物车不存在");
        }
        cartProductRepository.deleteAllByCartId(cartPO.getId());
    }

    @Override
    public void subtractNum(Integer branchId, String openid, Integer productId) {
        CartPO cartPO = cartRepository.findByBranchIdAndWechatId(branchId, openid);
        if (Objects.isNull(cartPO)) {
            throw new WechatException(ResponseEnum.WECHAT_BAD_REQUEST, "购物车不存在");
        }
        CartProductPO cartProductPO = cartProductRepository.findByCartIdAndProductId(cartPO.getId(), productId);
        if (Objects.isNull(cartProductPO)) {
            throw new WechatException(ResponseEnum.WECHAT_BAD_REQUEST, "购物车产品不存在");
        }
        if (1 == cartProductPO.getProductNum()) {
            cartProductRepository.delete(cartProductPO.getId());
        } else {
            cartProductPO.setProductNum(cartProductPO.getProductNum() - 1);
            cartProductPO.setSum(cartProductPO.getProductPrice().multiply(new BigDecimal(cartProductPO.getProductNum())));
            cartProductRepository.save(cartProductPO);
        }
    }

    @Override
    public void addNum(Integer branchId, String openid, Integer productId) {
        CartPO cartPO = cartRepository.findByBranchIdAndWechatId(branchId, openid);
        if (Objects.isNull(cartPO)) {
            throw new WechatException(ResponseEnum.WECHAT_BAD_REQUEST, "购物车不存在");
        }
        CartProductPO cartProductPO = cartProductRepository.findByCartIdAndProductId(cartPO.getId(), productId);
        if (Objects.isNull(cartProductPO)) {
            BranchProductPO branchProductPO = branchProductRepository.findByIdAndStatus(productId, ShopBranchProductStatus.NORMAL.name());
            if (Objects.isNull(branchProductPO) || !branchProductPO.getBranchId().equals(branchId)) {
                throw new WechatException(ResponseEnum.WECHAT_BAD_REQUEST, "产品id有误");
            }
            cartProductPO = new CartProductPO(null, cartPO.getId(), productId, 1,
                    branchProductPO.getName(), branchProductPO.getPrice(), branchProductPO.getPrice());
            cartProductRepository.save(cartProductPO);
        } else {
            cartProductPO.setProductNum(cartProductPO.getProductNum() + 1);
            cartProductPO.setSum(cartProductPO.getProductPrice().multiply(new BigDecimal(cartProductPO.getProductNum())));
            cartProductRepository.save(cartProductPO);
        }
    }
}
