package com.qudiancan.backend.service.util.shop;

import com.qudiancan.backend.enums.ResponseEnum;
import com.qudiancan.backend.exception.ShopException;
import com.qudiancan.backend.pojo.vo.shop.BranchProductVO;
import com.qudiancan.backend.pojo.vo.shop.ProductCategoryVO;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * @author NINGTIANMIN
 */
public class ShopProductServiceUtil {
    public static final Pattern POSITION_PATTERN = Pattern.compile("^[1-9][0-9]{0,3}$");
    public static final Pattern PRICE_PATTERN = Pattern.compile("^(0|[1-9][0-9]{0,5})(\\.[0-9]{1,2})?$");

    public static void checkProductCategoryVO(ProductCategoryVO productCategoryVO) {
        if (StringUtils.isEmpty(productCategoryVO.getName())) {
            throw new ShopException(ResponseEnum.SHOP_PARAM_WRONG, "name");
        }
        if (!checkPositionValidity(productCategoryVO.getPosition())) {
            throw new ShopException(ResponseEnum.SHOP_PARAM_WRONG, "position");
        }
    }

    public static void checkBranchProductVO(BranchProductVO branchProductVO) {
        if (Objects.isNull(branchProductVO.getCategoryId())) {
            throw new ShopException(ResponseEnum.SHOP_PARAM_WRONG, "categoryId");
        }
        if (Objects.isNull(branchProductVO.getDepartmentId())) {
            throw new ShopException(ResponseEnum.SHOP_PARAM_WRONG, "departmentId");
        }
        if (StringUtils.isEmpty(branchProductVO.getDescription())) {
            throw new ShopException(ResponseEnum.SHOP_PARAM_WRONG, "description");
        }
        if (StringUtils.isEmpty(branchProductVO.getName())) {
            throw new ShopException(ResponseEnum.SHOP_PARAM_WRONG, "name");
        }
        if (StringUtils.isEmpty(branchProductVO.getImage())) {
            throw new ShopException(ResponseEnum.SHOP_PARAM_WRONG, "image");
        }
        if (!checkPositionValidity(branchProductVO.getPosition())) {
            throw new ShopException(ResponseEnum.SHOP_PARAM_WRONG, "position");
        }
        if (!checkPriceValidity(branchProductVO.getPrice())) {
            throw new ShopException(ResponseEnum.SHOP_PARAM_WRONG, "price");
        }
        if (StringUtils.isEmpty(branchProductVO.getUnitName())) {
            throw new ShopException(ResponseEnum.SHOP_PARAM_WRONG, "unitName");
        }
    }

    public static boolean checkPositionValidity(Integer position) {
        return Objects.nonNull(position) && POSITION_PATTERN.matcher(String.valueOf(position)).matches();
    }

    public static boolean checkPriceValidity(BigDecimal price) {
        return Objects.nonNull(price) && PRICE_PATTERN.matcher(price.toString()).matches();
    }
}
