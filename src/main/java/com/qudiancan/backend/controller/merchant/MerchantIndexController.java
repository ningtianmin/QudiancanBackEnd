package com.qudiancan.backend.controller.merchant;

import com.qudiancan.backend.common.Constant;
import com.qudiancan.backend.common.ShopAccountHolder;
import com.qudiancan.backend.enums.ResponseEnum;
import com.qudiancan.backend.enums.StringPairDTO;
import com.qudiancan.backend.enums.shop.ShopIsCreator;
import com.qudiancan.backend.exception.ShopException;
import com.qudiancan.backend.pojo.dto.shop.AccountInfoDTO;
import com.qudiancan.backend.pojo.po.AccountPO;
import com.qudiancan.backend.pojo.po.BranchPO;
import com.qudiancan.backend.pojo.po.RolePO;
import com.qudiancan.backend.service.shop.RoleService;
import com.qudiancan.backend.service.shop.ShopAccountService;
import com.qudiancan.backend.service.shop.ShopBranchService;
import com.qudiancan.backend.util.CommonUtil;
import com.qudiancan.backend.util.CookieUtil;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author NINGTIANMIN
 */
@Controller
@RequestMapping("/merchants")
public class MerchantIndexController {

    @Autowired
    private ShopAccountService shopAccountService;
    @Autowired
    private ShopBranchService shopBranchService;
    @Autowired
    private RoleService roleService;

    @GetMapping("/index")
    public ModelAndView index(HttpServletRequest request) {
        String currentBranchId = CookieUtil.get(request, Constant.COOKIE_CURRENT_BRANCH_ID);
        if (StringUtils.isEmpty(currentBranchId)) {
            throw new ShopException(ResponseEnum.BAD_REQUEST);
        }
        AccountPO accountPO = ShopAccountHolder.get();
        List<BranchPO> managedBranches = shopBranchService.listBranchManage(accountPO.getId());
        BranchPO currentBranch = shopBranchService.getBranch(accountPO.getId(), accountPO.getShopId(), Integer.valueOf(currentBranchId));
        Map<String, Object> map = new HashMap<>(2);
        map.put("managedBranches", managedBranches);
        map.put("currentBranch", currentBranch);
        return new ModelAndView("merchants/index", map);
    }

    @GetMapping("/branchIndex")
    public ModelAndView branchIndex(HttpServletRequest request, HttpServletResponse response, @RequestParam Integer branchId) {
        AccountPO accountPO = ShopAccountHolder.get();
        List<BranchPO> managedBranches = shopBranchService.listBranchManage(accountPO.getId());
        if (managedBranches.stream().noneMatch(o -> o.getId().equals(branchId))) {
            throw new ShopException(ResponseEnum.PARAM_INVALID);
        }
        CookieUtil.set(response, Constant.COOKIE_CURRENT_BRANCH_ID, branchId + "", Constant.COOKIE_EXPIRY);
        request.setAttribute(Constant.COOKIE_CURRENT_BRANCH_ID, branchId);
        BranchPO currentBranch = shopBranchService.getBranch(accountPO.getId(), accountPO.getShopId(), branchId);
        Map<String, Object> map = new HashMap<>(2);
        map.put("managedBranches", managedBranches);
        map.put("currentBranch", currentBranch);
        return new ModelAndView("merchants/index", map);
    }

    @RequestMapping("/logout")
    public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) {
        shopAccountService.logout(request, response);
        return new ModelAndView("merchants/login");
    }

    @RequestMapping("/perfectShop")
    public ModelAndView perfectShop() {
        Map<String, List<StringPairDTO>> constants = CommonUtil.getConstants();
        Map<String, Object> map = new HashMap<>(1);
        map.put(Constant.CLIENT_CONSTANTS_NAME, constants);
        return new ModelAndView("merchants/perfectShop", map);
    }

    @GetMapping("/personalCenter")
    public ModelAndView personalCenter() {
        AccountPO accountPO = ShopAccountHolder.get();
        AccountInfoDTO accountInfoDTO = new AccountInfoDTO();
        BeanUtils.copyProperties(accountPO, accountInfoDTO);
        accountInfoDTO.setIsCreator(ShopIsCreator.valueOf(accountInfoDTO.getIsCreator()).getValue());
        accountInfoDTO.setBranchesString(shopBranchService.getBranches(accountPO.getBranchIds()).stream().map(BranchPO::getName).collect(Collectors.joining(",")));
        accountInfoDTO.setRolesString(roleService.getRoles(accountPO.getRoleIds()).stream().map(RolePO::getName).collect(Collectors.joining(",")));

        Map<String, Object> map = new HashMap<>(1);
        map.put("accountInfo", accountInfoDTO);
        return new ModelAndView("merchants/personalCenter", map);
    }

    @GetMapping("/updatePassword")
    public ModelAndView updatePassword() {
        return new ModelAndView("merchants/updatePassword");
    }
}
