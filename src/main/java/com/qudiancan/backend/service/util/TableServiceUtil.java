package com.qudiancan.backend.service.util;

import com.qudiancan.backend.enums.ResponseEnum;
import com.qudiancan.backend.exception.ShopException;
import com.qudiancan.backend.pojo.vo.TableCategoryVO;
import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 * @author NINGTIANMIN
 */
public class TableServiceUtil {
    public static void checkTableCategoryVO(TableCategoryVO tableCategoryVO) {
        if (Objects.isNull(tableCategoryVO)) {
            throw new ShopException(ResponseEnum.SHOP_PARAM_WRONG, "tableCategoryVO");
        }
        if (StringUtils.isEmpty(tableCategoryVO.getName())) {
            throw new ShopException(ResponseEnum.SHOP_PARAM_WRONG, "name");
        }
        if (!ProductServiceUtil.checkPositionValidity(tableCategoryVO.getPosition())) {
            throw new ShopException(ResponseEnum.SHOP_PARAM_WRONG, "position");
        }
    }
}
