package com.qudiancan.backend.pojo.vo.wechat;

import com.qudiancan.backend.pojo.vo.shop.CartVO;
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
    private String openid;
    private List<CartVO> cart;
}