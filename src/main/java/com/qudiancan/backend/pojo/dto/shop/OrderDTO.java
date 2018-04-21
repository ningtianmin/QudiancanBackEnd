package com.qudiancan.backend.pojo.dto.shop;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.qudiancan.backend.pojo.po.OrderProductPO;
import com.qudiancan.backend.util.JsonTimeStampSerializer;
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
    private Integer id;
    private Integer branchId;
    private Integer tableId;
    private Integer memberId;
    private String orderNumber;
    private BigDecimal totalSum;
    private BigDecimal discountSum;
    private BigDecimal wipeSum;
    private BigDecimal chargeSum;
    private List<OrderProductPO> orderProducts;
    private String tableName;
    private String note;
    private String payMethod;
    private String payStatus;
    private String orderStatus;
    private Integer customerNum;
    @JsonSerialize(using = JsonTimeStampSerializer.class)
    private Timestamp createTime;
    private String branchName;
}
