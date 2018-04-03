package com.qudiancan.backend.service.util.shop;

import com.qudiancan.backend.enums.ResponseEnum;
import com.qudiancan.backend.exception.ShopException;
import com.qudiancan.backend.pojo.vo.shop.AddProductsVO;
import com.qudiancan.backend.pojo.vo.shop.OrderVO;
import org.springframework.util.CollectionUtils;

import java.util.Objects;

/**
 * @author NINGTIANMIN
 */
public class ShopOrderServiceUtil {
    public static void checkOrderVO(OrderVO orderVO) {
        if (Objects.isNull(orderVO.getBranchId())) {
            throw new ShopException(ResponseEnum.PARAM_INCOMPLETE, "branchId");
        }
        if (CollectionUtils.isEmpty(orderVO.getCart())) {
            throw new ShopException(ResponseEnum.PARAM_INCOMPLETE, "cart");
        }
        if (orderVO.getCart().stream().anyMatch(
                o -> Objects.isNull(o.getProductId()) || Objects.isNull(o.getProductNum()) || o.getProductNum() < 1)) {
            throw new ShopException(ResponseEnum.PARAM_INCOMPLETE, "cart");
        }
    }

    public static void checkAddProductsVO(AddProductsVO addProductsVO) {
        if (Objects.isNull(addProductsVO.getOrderId())) {
            throw new ShopException(ResponseEnum.PARAM_INCOMPLETE, "orderId");
        }
        if (CollectionUtils.isEmpty(addProductsVO.getCart())) {
            throw new ShopException(ResponseEnum.PARAM_INCOMPLETE, "cart");
        }
        if (addProductsVO.getCart().stream().anyMatch(
                o -> Objects.isNull(o.getProductId()) || Objects.isNull(o.getProductNum()) || o.getProductNum() < 1)) {
            throw new ShopException(ResponseEnum.PARAM_INCOMPLETE, "cart");
        }
    }
}
