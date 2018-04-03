package com.qudiancan.backend.service.impl.wechat;

import com.qudiancan.backend.pojo.dto.shop.OrderDTO;
import com.qudiancan.backend.pojo.vo.wechat.OrderVO;
import com.qudiancan.backend.service.shop.ShopOrderService;
import com.qudiancan.backend.service.wechat.WechatAccountService;
import com.qudiancan.backend.service.wechat.WechatOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author NINGTIANMIN
 */
@Service
@Slf4j
public class WechatOrderServiceImpl implements WechatOrderService {
    @Autowired
    private ShopOrderService shopOrderService;
    @Autowired
    private WechatAccountService wechatAccountService;

    @Override
    public OrderDTO create(OrderVO orderVO) {
        Integer memberId = wechatAccountService.getMemberIdByBranchIdAndOpenid(orderVO.getBranchId(), orderVO.getOpenid());
        com.qudiancan.backend.pojo.vo.shop.OrderVO orderVOShop = new com.qudiancan.backend.pojo.vo.shop.OrderVO();
        BeanUtils.copyProperties(orderVO, orderVOShop);
        orderVOShop.setMemberId(memberId);
        return shopOrderService.create(orderVOShop);
    }
}
