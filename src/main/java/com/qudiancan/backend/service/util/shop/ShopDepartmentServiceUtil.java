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
        if (StringUtils.isEmpty(departmentVO.getName())
                || departmentVO.getName().length() < 2 || departmentVO.getName().length() > 15) {
            throw new ShopException(ResponseEnum.SHOP_PARAM_WRONG, "name两个字符到十五个字符");
        }
        if (StringUtils.isEmpty(departmentVO.getDescription())
                || departmentVO.getDescription().length() < 2 || departmentVO.getDescription().length() > 50) {
            throw new ShopException(ResponseEnum.SHOP_PARAM_WRONG, "description两个字符到五十个字符");
        }
    }
}
