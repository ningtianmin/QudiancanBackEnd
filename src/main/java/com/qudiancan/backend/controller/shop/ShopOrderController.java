package com.qudiancan.backend.controller.shop;

import com.qudiancan.backend.common.ShopAccountHolder;
import com.qudiancan.backend.common.ShopRequiredAuthority;
import com.qudiancan.backend.enums.shop.ShopAuthorityEnum;
import com.qudiancan.backend.pojo.PageResponse;
import com.qudiancan.backend.pojo.Response;
import com.qudiancan.backend.pojo.dto.shop.OrderDTO;
import com.qudiancan.backend.pojo.po.AccountPO;
import com.qudiancan.backend.pojo.vo.shop.AddProductsVO;
import com.qudiancan.backend.pojo.vo.shop.OrderVO;
import com.qudiancan.backend.service.shop.ShopOrderService;
import com.qudiancan.backend.util.JsonToObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

/**
 * @author NINGTIANMIN
 */
@RestController
@RequestMapping("/shops")
@Slf4j
public class ShopOrderController {
    @Autowired
    private ShopOrderService shopOrderService;

    /**
     * 创建订单
     *
     * @param orderVO 下单信息
     * @return 创建的订单
     */
    @PostMapping("/orders")
    @ShopRequiredAuthority(ShopAuthorityEnum.SHOP_BRANCH_CASHIER)
    public Response<OrderDTO> createOrder(OrderVO orderVO) {
        orderVO.setCart(JsonToObjectUtil.toCartVO(orderVO.getCartJson()));
        return Response.success(shopOrderService.create(orderVO));
    }

    /**
     * 追加订单产品
     *
     * @param addProductsVO 追加的产品信息
     * @return 更新后的订单信息
     */
    @PostMapping("/orders/addProducts")
    @ShopRequiredAuthority(ShopAuthorityEnum.SHOP_BRANCH_CASHIER)
    public Response<OrderDTO> addOrderProducts(AddProductsVO addProductsVO) {
        log.info("【追加产品】addProductsVO：{}", addProductsVO);
        addProductsVO.setCart(JsonToObjectUtil.toCartVO(addProductsVO.getCartJson()));
        return Response.success(shopOrderService.addProducts(addProductsVO));
    }

    @PostMapping("/orders/cashPay")
    @ShopRequiredAuthority(ShopAuthorityEnum.SHOP_BRANCH_CASHIER)
    public Response cashPayOrder(@RequestParam String orderNumber, @RequestParam Integer branchId) {
        AccountPO accountPO = ShopAccountHolder.get();
        shopOrderService.payByCash(accountPO.getId(), accountPO.getShopId(), branchId, orderNumber);
        return Response.success();
    }

    @GetMapping("/branches/{branchId}/orders")
    @ShopRequiredAuthority(ShopAuthorityEnum.BRANCH_ORDER_SHOW)
    public PageResponse<OrderDTO> pageOrderByBranch(@PathVariable Integer branchId,
                                                    @RequestParam(name = "page", defaultValue = "1") Integer page,
                                                    @RequestParam(name = "size", defaultValue = "10") Integer size) {
        return PageResponse.success(shopOrderService.pageOrderByBranch(
                ShopAccountHolder.get().getId(), branchId, new PageRequest(page - 1, size)));
    }
}
