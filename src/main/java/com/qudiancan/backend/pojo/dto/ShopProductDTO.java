package com.qudiancan.backend.pojo.dto;

import com.qudiancan.backend.pojo.po.BranchProductPO;
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
public class ShopProductDTO {
    private Integer branchId;
    private Integer categoryId;
    private String categoryName;
    private Integer categoryPosition;
    private List<BranchProductPO> products;
}

