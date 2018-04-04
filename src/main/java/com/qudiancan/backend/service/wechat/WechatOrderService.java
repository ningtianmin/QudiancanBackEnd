package com.qudiancan.backend.service.wechat;

import com.qudiancan.backend.pojo.dto.shop.OrderDTO;
import com.qudiancan.backend.pojo.dto.wechat.TableOrderDTO;
import com.qudiancan.backend.pojo.vo.wechat.AddProductsVO;
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

    /**
     * 追加产品
     *
     * @param addProductsVO 追加的产品信息
     * @return 追加后的订单
     */
    OrderDTO addProducts(AddProductsVO addProductsVO);

    /**
     * 获取桌台及对应的订单
     *
     * @param tableId 桌台id
     * @param openid  微信用户openid
     * @return 桌台及对应的订单
     */
    TableOrderDTO getTableOrder(Integer tableId, String openid);
}
