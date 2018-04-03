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
public class OrderVO {
    private Integer branchId;
    private Integer branchTableId;
    private String openid;
    private String note;
    private String cartData;
    private List<CartVO> cart;
}