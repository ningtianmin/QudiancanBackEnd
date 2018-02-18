package com.qudiancan.backend.service.util;

import com.qudiancan.backend.enums.IsCreator;
import com.qudiancan.backend.enums.ResponseEnum;
import com.qudiancan.backend.exception.ShopException;
import com.qudiancan.backend.pojo.po.AccountPO;
import com.qudiancan.backend.pojo.po.BranchPO;
import com.qudiancan.backend.pojo.vo.BranchVO;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * @author NINGTIANMIN
 */
public class BranchServiceUtil {
    public static final Pattern LONGITUDE_PATTERN = Pattern.compile("^-?((0|1?[0-7]?[0-9]?)(([.][0-9]{1,4})?)|180(([.][0]{1,4})?))$");
    public static final Pattern LATITUDE_PATTERN = Pattern.compile("^-?((0|[1-8]?[0-9]?)(([.][0-9]{1,4})?)|90(([.][0]{1,4})?))$");

    public static void checkBranchVO(BranchVO branchVO) {
        if (branchVO == null) {
            throw new ShopException(ResponseEnum.SHOP_PARAM_WRONG);
        }
        if (StringUtils.isEmpty(branchVO.getAddress())) {
            throw new ShopException(ResponseEnum.SHOP_PARAM_WRONG, "address");
        }
        if (StringUtils.isEmpty(branchVO.getIntroduction())) {
            throw new ShopException(ResponseEnum.SHOP_PARAM_WRONG, "introduction");
        }
        if (!checkLatitudeValidity(branchVO.getLatitude())) {
            throw new ShopException(ResponseEnum.SHOP_PARAM_WRONG, "latitude");
        }
        if (!checkLongitudeValidity(branchVO.getLongitude())) {
            throw new ShopException(ResponseEnum.SHOP_PARAM_WRONG, "longitude");
        }
        if (StringUtils.isEmpty(branchVO.getName())) {
            throw new ShopException(ResponseEnum.SHOP_PARAM_WRONG, "name");
        }
        if (StringUtils.isEmpty(branchVO.getNotice())) {
            throw new ShopException(ResponseEnum.SHOP_PARAM_WRONG, "notice");
        }
        if (!AccountServiceUtil.checkPhoneValidity(branchVO.getPhone())) {
            throw new ShopException(ResponseEnum.SHOP_PARAM_WRONG, "phone");
        }
    }

    public static boolean canManageBranch(AccountPO accountPO, String shopId, BranchPO branchPO) {
        return (Objects.nonNull(accountPO) && !StringUtils.isEmpty(shopId) && Objects.nonNull(branchPO)) &&
                (shopId.equals(accountPO.getShopId()) && shopId.equals(branchPO.getShopId())) &&
                ((IsCreator.YES.name().equals(accountPO.getIsCreator())) ||
                        (!StringUtils.isEmpty(accountPO.getBranchIds()) &&
                                Arrays.stream(accountPO.getBranchIds().split(",")).anyMatch(String.valueOf(branchPO.getId())::equals)));
    }

    public static boolean checkLongitudeValidity(String longitude) {
        return !StringUtils.isEmpty(longitude) && LONGITUDE_PATTERN.matcher(longitude).matches();
    }

    public static boolean checkLatitudeValidity(String latitude) {
        return !StringUtils.isEmpty(latitude) && LATITUDE_PATTERN.matcher(latitude).matches();
    }
}
