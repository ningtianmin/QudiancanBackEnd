package com.qudiancan.backend.pojo.vo.shop;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author NINGTIANMIN
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddProductsVO {
    private Integer orderId;
    private String note;
    private String cartData;
    private List<CartVO> cart;
}
