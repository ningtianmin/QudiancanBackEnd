package com.qudiancan.backend.service.util.shop;

import com.qudiancan.backend.enums.ResponseEnum;
import com.qudiancan.backend.exception.ShopException;
import com.qudiancan.backend.pojo.vo.shop.BranchTableVO;
import com.qudiancan.backend.pojo.vo.shop.TableCategoryVO;
import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 * @author NINGTIANMIN
 */
public class ShopTableServiceUtil {
    public static final Integer TABLE_CAPACITY_MAX = 12;
    public static final Integer TABLE_CAPACITY_MIN = 1;

    public static void checkTableCategoryVO(TableCategoryVO tableCategoryVO) {
        if (Objects.isNull(tableCategoryVO)) {
            throw new ShopException(ResponseEnum.SHOP_PARAM_WRONG, "tableCategoryVO");
        }
        if (StringUtils.isEmpty(tableCategoryVO.getName())) {
            throw new ShopException(ResponseEnum.SHOP_PARAM_WRONG, "name");
        }
        if (!ShopProductServiceUtil.checkPositionValidity(tableCategoryVO.getPosition())) {
            throw new ShopException(ResponseEnum.SHOP_PARAM_WRONG, "position");
        }
    }

    public static void checkBranchTableVO(BranchTableVO branchTableVO) {
        if (Objects.isNull(branchTableVO.getCapacity()) || branchTableVO.getCapacity() > TABLE_CAPACITY_MAX || branchTableVO.getCapacity() < TABLE_CAPACITY_MIN) {
            throw new ShopException(ResponseEnum.SHOP_PARAM_WRONG, "capacity为1-12的整数");
        }
        if (Objects.isNull(branchTableVO.getCategoryId())) {
            throw new ShopException(ResponseEnum.SHOP_PARAM_WRONG, "categoryId");
        }
        if (StringUtils.isEmpty(branchTableVO.getName())) {
            throw new ShopException(ResponseEnum.SHOP_PARAM_WRONG, "name");
        }
        if (!ShopProductServiceUtil.checkPositionValidity(branchTableVO.getPosition())) {
            throw new ShopException(ResponseEnum.SHOP_PARAM_WRONG, "position");
        }
    }
}
