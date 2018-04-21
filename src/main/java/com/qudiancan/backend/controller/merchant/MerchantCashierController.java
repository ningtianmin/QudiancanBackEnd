package com.qudiancan.backend.controller.merchant;

import com.qudiancan.backend.common.ShopAccountHolder;
import com.qudiancan.backend.common.ShopRequiredAuthority;
import com.qudiancan.backend.enums.shop.ShopAuthorityEnum;
import com.qudiancan.backend.pojo.po.AccountPO;
import com.qudiancan.backend.service.shop.ShopProductService;
import com.qudiancan.backend.service.shop.ShopTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * @author NINGTIANMIN
 */
@Controller
@RequestMapping("/merchants")
public class MerchantCashierController {
    @Autowired
    private ShopTableService shopTableService;
    @Autowired
    private ShopProductService shopProductService;

    @GetMapping("/eatin")
    @ShopRequiredAuthority(ShopAuthorityEnum.SHOP_BRANCH_CASHIER)
    public ModelAndView eatin(@RequestParam Integer branchId) {
        AccountPO accountPO = ShopAccountHolder.get();
        Map<String, Object> map = new HashMap<>(1);
        map.put("tables", shopTableService.listBranchTable(accountPO.getId(), accountPO.getShopId(), branchId));
        map.put("products", shopProductService.listProductUp(branchId));
        return new ModelAndView("merchants/cashier/eatin", map);
    }

    @GetMapping("/orders")
    @ShopRequiredAuthority(ShopAuthorityEnum.BRANCH_ORDER_SHOW)
    public ModelAndView orders() {
        return new ModelAndView("merchants/cashier/orders");
    }
}
