package com.qudiancan.backend.service.util;

import com.qudiancan.backend.enums.ResponseEnum;
import com.qudiancan.backend.exception.ShopException;
import com.qudiancan.backend.pojo.vo.ProductCategoryVO;
import org.springframework.util.StringUtils;

import java.util.regex.Pattern;

/**
 * @author NINGTIANMIN
 */
public class ProductServiceUtil {
    public static final Pattern POSITION_PATTERN = Pattern.compile("^[1-9][0-9]{0,3}$");

    public static void checkProductCategoryVO(ProductCategoryVO productCategoryVO) {
        if (StringUtils.isEmpty(productCategoryVO.getName())) {
            throw new ShopException(ResponseEnum.SHOP_PARAM_WRONG, "name");
        }
        if (StringUtils.isEmpty(productCategoryVO.getPosition()) || !checkPositionValidity(String.valueOf(productCategoryVO.getPosition()))) {
            throw new ShopException(ResponseEnum.SHOP_PARAM_WRONG, "position");
        }
    }

    public static boolean checkPositionValidity(String postion) {
        return !StringUtils.isEmpty(postion) && POSITION_PATTERN.matcher(postion).matches();
    }
}
