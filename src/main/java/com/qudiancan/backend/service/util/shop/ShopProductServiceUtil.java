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
    public static final Pattern PRICE_PATTERN = Pattern.compile("^(0|[1-9][0-9]{0,3})(\\.[0-9]{1,2})?$");

    public static void checkProductCategoryVO(ProductCategoryVO productCategoryVO) {
        if (StringUtils.isEmpty(productCategoryVO.getName())
                || productCategoryVO.getName().length() < 2 || productCategoryVO.getName().length() > 15) {
            throw new ShopException(ResponseEnum.SHOP_PARAM_WRONG, "name两个字符到十五个字符");
        }
        if (!checkPositionValidity(productCategoryVO.getPosition())) {
            throw new ShopException(ResponseEnum.SHOP_PARAM_WRONG, "position为1至4位整数");
        }
    }

    public static void checkBranchProductVO(BranchProductVO branchProductVO) {
        if (Objects.isNull(branchProductVO.getCategoryId())) {
            throw new ShopException(ResponseEnum.SHOP_PARAM_WRONG, "categoryId");
        }
        if (Objects.isNull(branchProductVO.getDepartmentId())) {
            throw new ShopException(ResponseEnum.SHOP_PARAM_WRONG, "departmentId");
        }
        if (StringUtils.isEmpty(branchProductVO.getDescription()) || branchProductVO.getDescription().length() < 2 || branchProductVO.getDescription().length() > 50) {
            throw new ShopException(ResponseEnum.SHOP_PARAM_WRONG, "产品描述两个字符到五十个字符");
        }
        if (StringUtils.isEmpty(branchProductVO.getName()) || branchProductVO.getName().length() > 15) {
            throw new ShopException(ResponseEnum.SHOP_PARAM_WRONG, "产品名称一个字符到15个字符");
        }
        if (StringUtils.isEmpty(branchProductVO.getImage())) {
            throw new ShopException(ResponseEnum.SHOP_PARAM_WRONG, "image");
        }
        if (!checkPositionValidity(branchProductVO.getPosition())) {
            throw new ShopException(ResponseEnum.SHOP_PARAM_WRONG, "类目内排序为1至4位的整数");
        }
        if (!checkPriceValidity(branchProductVO.getPrice())) {
            throw new ShopException(ResponseEnum.SHOP_PARAM_WRONG, "价格0至9999.99");
        }
        if (StringUtils.isEmpty(branchProductVO.getUnitName()) || branchProductVO.getUnitName().length() > 5) {
            throw new ShopException(ResponseEnum.SHOP_PARAM_WRONG, "单位名称一个字符到五个字符");
        }
    }

    public static boolean checkPositionValidity(Integer position) {
        return Objects.nonNull(position) && POSITION_PATTERN.matcher(String.valueOf(position)).matches();
    }

    public static boolean checkPriceValidity(BigDecimal price) {
        return Objects.nonNull(price) && PRICE_PATTERN.matcher(price.toString()).matches();
    }

}
