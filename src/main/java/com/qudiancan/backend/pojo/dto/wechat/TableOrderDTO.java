package com.qudiancan.backend.pojo.dto.wechat;

import com.qudiancan.backend.pojo.dto.shop.OrderDTO;
import com.qudiancan.backend.pojo.po.BranchTablePO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author NINGTIANMIN
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TableOrderDTO {
    private BranchTablePO table;
    private OrderDTO order;
}
