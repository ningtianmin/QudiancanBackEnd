package com.qudiancan.backend.service.shop;

import com.qudiancan.backend.pojo.dto.shop.OrderDTO;
import com.qudiancan.backend.pojo.vo.shop.AddProductsVO;
import com.qudiancan.backend.pojo.vo.shop.OrderVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

/**
 * @author NINGTIANMIN
 */
public interface ShopOrderService {

    /**
     * 创建订单
     *
     * @param orderVO 订单参数
     * @return 创建的订单
     */
    OrderDTO create(OrderVO orderVO);

    /**
     * 通过订单编号查询订单
     *
     * @param orderNum 订单编号
     * @return 查到的订单
     */
    OrderDTO findByOrderNum(String orderNum);

    /**
     * 通过订单id查询订单
     *
     * @param orderId 订单id
     * @return 查到的订单
     */
    OrderDTO findByOrderId(Integer orderId);

    /**
     * 分页查询订单
     *
     * @param memberId 会员id
     * @param pageable 分页参数
     * @return 订单列表
     */
    Page<OrderDTO> list(Integer memberId, Pageable pageable);

    /**
     * 取消订单
     *
     * @param orderNum 订单编号
     * @return 取消的订单
     */
    OrderDTO cancel(String orderNum);

    /**
     * 完结订单
     *
     * @param orderNumbers 订单编号列表
     */
    void finish(Set<String> orderNumbers);

    /**
     * 微信支付订单
     *
     * @param orderNumber 订单编号
     */
    void payByWechat(String orderNumber);

    /**
     * 追加产品
     *
     * @param addProductsVO 追加的产品信息
     * @return 追加后的订单
     */
    OrderDTO addProducts(AddProductsVO addProductsVO);

    /**
     * 获取订单列表
     *
     * @param openid 微信用户openid
     * @return 订单列表
     */
    List<OrderDTO> listOrderByOpenid(String openid);

    /**
     * 现金支付订单
     *
     * @param accountId   账户id
     * @param shopId      店铺id
     * @param branchId    门店id
     * @param orderNumber 订单编号
     */
    void payByCash(Integer accountId, String shopId, Integer branchId, String orderNumber);

    /**
     * 根据门店分页查询订单
     *
     * @param accountId 账号id
     * @param branchId  门店id
     * @param pageable  分页参数
     * @return 分页订单
     */
    Page<OrderDTO> pageOrderByBranch(Integer accountId, Integer branchId, Pageable pageable);
}
