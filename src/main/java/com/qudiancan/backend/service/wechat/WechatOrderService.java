package com.qudiancan.backend.service.wechat;

import com.qudiancan.backend.pojo.dto.shop.OrderDTO;
import com.qudiancan.backend.pojo.vo.wechat.OrderVO;

/**
 * @author NINGTIANMIN
 */
public interface WechatOrderService {
    /**
     * 创建订单
     *
     * @param orderVO 订单参数
     * @return 创建的订单
     */
    OrderDTO create(OrderVO orderVO);
}
