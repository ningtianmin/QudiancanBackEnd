package com.qudiancan.backend.controller.wechat;

import com.qudiancan.backend.pojo.Response;
import com.qudiancan.backend.pojo.dto.shop.OrderDTO;
import com.qudiancan.backend.pojo.po.BranchTablePO;
import com.qudiancan.backend.pojo.vo.shop.AddProductsVO;
import com.qudiancan.backend.pojo.vo.wechat.OrderVO;
import com.qudiancan.backend.service.shop.ShopOrderService;
import com.qudiancan.backend.service.shop.ShopTableService;
import com.qudiancan.backend.service.wechat.WechatOrderService;
import com.qudiancan.backend.util.JsonToObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author NINGTIANMIN
 */
@RestController
@RequestMapping("/wechat")
@Slf4j
public class WechatOrderController {
    @Autowired
    private ShopTableService shopTableService;
    @Autowired
    private WechatOrderService wechatOrderService;
    @Autowired
    private ShopOrderService shopOrderService;

    /**
     * 获取桌台
     *
     * @param tableId 桌台id
     * @return 桌台信息
     */
    @GetMapping("/tables/{tableId}")
    public Response<BranchTablePO> getBranchTable(@PathVariable Integer tableId) {
        log.info("【获取桌台】tableId：{}", tableId);
        return Response.success(shopTableService.getBranchTable(tableId));
    }

    /**
     * 创建订单
     *
     * @param orderVO 订单信息
     * @return 创建的订单
     */
    @PostMapping("/orders")
    public Response<OrderDTO> createOrder(OrderVO orderVO) {
        log.info("【创建订单】orderVO：{}", orderVO);
        orderVO.setCart(JsonToObjectUtil.toCartVO(orderVO.getCartData()));
        return Response.success(wechatOrderService.create(orderVO));
    }

    /**
     * 追加产品
     *
     * @param addProductsVO 追加的产品信息
     * @return 追加后的订单
     */
    @PostMapping("/orders/addProducts")
    public Response<OrderDTO> addOrderProducts(AddProductsVO addProductsVO) {
        log.info("【追加产品】addProductsVO：{}", addProductsVO);
        addProductsVO.setCart(JsonToObjectUtil.toCartVO(addProductsVO.getCartData()));
        return Response.success(shopOrderService.addProducts(addProductsVO));
    }
}
