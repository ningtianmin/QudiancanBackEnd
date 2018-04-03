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
public class OrderVO {
    private Integer branchId;
    private Integer branchTableId;
    private String note;
    private Integer memberId;
    private List<CartVO> cart;
}
