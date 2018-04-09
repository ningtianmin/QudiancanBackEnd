package com.qudiancan.backend.controller.merchant;

import com.qudiancan.backend.common.ShopRequiredAuthority;
import com.qudiancan.backend.enums.shop.ShopAuthorityEnum;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author NINGTIANMIN
 */
@Controller
@RequestMapping("/merchants")
public class MerchantBranchController {

    @GetMapping("/categories")
    @ShopRequiredAuthority(ShopAuthorityEnum.BRANCH_CATEGORY_SHOW)
    public ModelAndView categories() {
        return new ModelAndView("merchants/categories");
    }

    @GetMapping("/departments")
    @ShopRequiredAuthority(ShopAuthorityEnum.BRANCH_DEPARTMENT_SHOW)
    public ModelAndView departments() {
        return new ModelAndView("merchants/departments");
    }

    @GetMapping("/products")
    @ShopRequiredAuthority(ShopAuthorityEnum.BRANCH_PRODUCT_SHOW)
    public ModelAndView products() {
        return new ModelAndView("merchants/products");
    }

}
