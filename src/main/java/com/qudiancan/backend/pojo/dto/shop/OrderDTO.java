package com.qudiancan.backend.pojo.dto.shop;

import com.qudiancan.backend.pojo.po.OrderProductPO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * @author NINGTIANMIN
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    List<OrderProductPO> orderProducts;
    private Integer id;
    private Integer branchId;
    private Integer tableId;
    private Integer memberId;
    private String orderNumber;
    private BigDecimal totalSum;
    private BigDecimal discountSum;
    private BigDecimal wipeSum;
    private BigDecimal chargeSum;
    private String tableName;
    private String note;
    private String payMethod;
    private String payStatus;
    private String orderStatus;
    private Timestamp createTime;
}
