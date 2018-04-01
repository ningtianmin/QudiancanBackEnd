package com.qudiancan.backend.service.util.shop;

import com.qudiancan.backend.enums.ResponseEnum;
import com.qudiancan.backend.exception.ShopException;
import com.qudiancan.backend.pojo.vo.shop.DepartmentVO;
import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 * @author NINGTIANMIN
 */
public class ShopDepartmentServiceUtil {
    public static void checkDepartmentVO(DepartmentVO departmentVO) {
        if (Objects.isNull(departmentVO)) {
            throw new ShopException(ResponseEnum.SHOP_PARAM_WRONG, "departmentVO");
        }
        if (StringUtils.isEmpty(departmentVO.getName())) {
            throw new ShopException(ResponseEnum.SHOP_PARAM_WRONG, "name");
        }
        if (StringUtils.isEmpty(departmentVO.getDescription())) {
            throw new ShopException(ResponseEnum.SHOP_PARAM_WRONG, "description");
        }
    }
}
