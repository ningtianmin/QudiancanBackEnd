package com.qudiancan.backend.controller.merchant;

import com.qudiancan.backend.common.Constant;
import com.qudiancan.backend.common.ShopAccountHolder;
import com.qudiancan.backend.common.ShopRequiredAuthority;
import com.qudiancan.backend.enums.StringPairDTO;
import com.qudiancan.backend.enums.shop.ShopAuthorityEnum;
import com.qudiancan.backend.pojo.dto.shop.AccountInfoDTO;
import com.qudiancan.backend.pojo.po.AccountPO;
import com.qudiancan.backend.pojo.po.BranchPO;
import com.qudiancan.backend.pojo.po.RolePO;
import com.qudiancan.backend.pojo.po.ShopPO;
import com.qudiancan.backend.service.shop.RoleService;
import com.qudiancan.backend.service.shop.ShopBranchService;
import com.qudiancan.backend.service.shop.ShopService;
import com.qudiancan.backend.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author NINGTIANMIN
 */
@Controller
@RequestMapping("/merchants")
@Slf4j
public class MerchantSystemController {

    @Autowired
    private ShopService shopService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private ShopBranchService shopBranchService;

    @GetMapping("/shopSetting")
    public ModelAndView shopSetting() {
        AccountPO accountPO = ShopAccountHolder.get();
        ShopPO shop = shopService.getShop(accountPO.getId(), accountPO.getShopId());
        Map<String, List<StringPairDTO>> constants = CommonUtil.getConstants();
        Map<String, Object> map = new HashMap<>(2);
        map.put("shop", shop);
        map.put(Constant.CLIENT_CONSTANTS_NAME, constants);
        // TODO: 18/04/18 主体类型前端显示有误
        return new ModelAndView("merchants/shopSetting", map);
    }

    @GetMapping("/roleSetting")
    public ModelAndView roleSetting() {
        List<RolePO> roles = roleService.findAll();
        Map<String, Object> map = new HashMap<>(1);
        map.put("roles", roles);
        return new ModelAndView("merchants/roleSetting", map);
    }

    @GetMapping("/accountSetting")
    @ShopRequiredAuthority(ShopAuthorityEnum.SHOP_ACCOUNT_SETTING)
    public ModelAndView accountSetting() {
        List<AccountInfoDTO> accounts = shopService.listShopAccount(ShopAccountHolder.get().getShopId());
        Map<String, Object> map = new HashMap<>(1);
        map.put("accounts", accounts);
        return new ModelAndView("merchants/accountSetting", map);
    }

    @GetMapping("/branchSetting")
    @ShopRequiredAuthority(ShopAuthorityEnum.SHOP_BRANCH_UPDATE)
    public ModelAndView branchSetting(@RequestParam Integer branchId) {
        AccountPO accountPO = ShopAccountHolder.get();
        BranchPO branch = shopBranchService.getBranch(accountPO.getId(), accountPO.getShopId(), branchId);
        Map<String, Object> map = new HashMap<>(1);
        map.put("branch", branch);
        return new ModelAndView("merchants/branchSetting", map);
    }

    @GetMapping("/createBranch")
    @ShopRequiredAuthority(ShopAuthorityEnum.SHOP_BRANCH_CREATE)
    public ModelAndView createBranch() {
        return new ModelAndView("merchants/createBranch");
    }
}
