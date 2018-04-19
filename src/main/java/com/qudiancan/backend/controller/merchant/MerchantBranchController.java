package com.qudiancan.backend.controller.merchant;

import com.qudiancan.backend.common.Constant;
import com.qudiancan.backend.common.ShopAccountHolder;
import com.qudiancan.backend.common.ShopRequiredAuthority;
import com.qudiancan.backend.enums.ResponseEnum;
import com.qudiancan.backend.enums.StringPairDTO;
import com.qudiancan.backend.enums.shop.ShopAuthorityEnum;
import com.qudiancan.backend.exception.ShopException;
import com.qudiancan.backend.pojo.po.AccountPO;
import com.qudiancan.backend.pojo.po.BranchProductPO;
import com.qudiancan.backend.service.shop.ShopDepartmentService;
import com.qudiancan.backend.service.shop.ShopProductService;
import com.qudiancan.backend.service.shop.ShopTableService;
import com.qudiancan.backend.util.CommonUtil;
import com.qudiancan.backend.util.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author NINGTIANMIN
 */
@Controller
@RequestMapping("/merchants")
public class MerchantBranchController {

    @Autowired
    private ShopProductService shopProductService;
    @Autowired
    private ShopDepartmentService shopDepartmentService;
    @Autowired
    private ShopTableService shopTableService;

    @GetMapping("/productCategories")
    @ShopRequiredAuthority(ShopAuthorityEnum.BRANCH_CATEGORY_SHOW)
    public ModelAndView categories() {
        return new ModelAndView("merchants/productCategories");
    }

    @GetMapping("/departments")
    @ShopRequiredAuthority(ShopAuthorityEnum.BRANCH_DEPARTMENT_SHOW)
    public ModelAndView departments() {
        return new ModelAndView("merchants/departments");
    }

    @GetMapping("/products")
    @ShopRequiredAuthority(ShopAuthorityEnum.BRANCH_PRODUCT_SHOW)
    public ModelAndView products(HttpServletRequest servletRequest) {
        AccountPO accountPO = ShopAccountHolder.get();
        String branchId = CookieUtil.get(servletRequest, Constant.COOKIE_CURRENT_BRANCH_ID);
        if (Objects.isNull(branchId)) {
            throw new ShopException(ResponseEnum.PARAM_INVALID, "branchId");
        }
        Map<String, List<StringPairDTO>> constants = CommonUtil.getConstants();
        Map<String, Object> map = new HashMap<>(1);
        map.put(Constant.CLIENT_CONSTANTS_NAME, constants);
        // TODO: 18/04/10 部门id等转换为名称
        map.put("branchCategories", shopProductService.
                categoriesStringPair(accountPO.getId(), accountPO.getShopId(), Integer.valueOf(branchId))
                .stream().collect(Collectors.toMap(StringPairDTO::getKey, StringPairDTO::getValue)));
        map.put("branchDepartments", shopDepartmentService.departmentsStringPair(accountPO.getId(), accountPO.getShopId(), Integer.valueOf(branchId)));
        return new ModelAndView("merchants/products", map);
    }

    @GetMapping("/createProduct")
    @ShopRequiredAuthority(ShopAuthorityEnum.BRANCH_PRODUCT_CREATE)
    public ModelAndView createProduct(HttpServletRequest servletRequest) {
        AccountPO accountPO = ShopAccountHolder.get();
        String branchId = CookieUtil.get(servletRequest, Constant.COOKIE_CURRENT_BRANCH_ID);
        if (Objects.isNull(branchId)) {
            throw new ShopException(ResponseEnum.PARAM_INVALID, "branchId");
        }
        Map<String, Object> map = new HashMap<>(2);
        map.put("productCategories", shopProductService.categoriesStringPair(accountPO.getId(), accountPO.getShopId(), Integer.valueOf(branchId)));
        map.put("branchDepartments", shopDepartmentService.departmentsStringPair(accountPO.getId(), accountPO.getShopId(), Integer.valueOf(branchId)));
        return new ModelAndView("merchants/createProduct", map);
    }

    @GetMapping("/tableCategories")
    @ShopRequiredAuthority(ShopAuthorityEnum.BRANCH_TABLE_CATEGORY_SHOW)
    public ModelAndView tableCategories() {
        return new ModelAndView("merchants/tableCategories");
    }

    @GetMapping("/tables")
    @ShopRequiredAuthority(ShopAuthorityEnum.BRANCH_TABLE_SHOW)
    public ModelAndView tables(HttpServletRequest servletRequest) {
        AccountPO accountPO = ShopAccountHolder.get();
        String branchId = CookieUtil.get(servletRequest, Constant.COOKIE_CURRENT_BRANCH_ID);
        if (Objects.isNull(branchId)) {
            throw new ShopException(ResponseEnum.PARAM_INVALID, "branchId");
        }
        Map<String, List<StringPairDTO>> constants = CommonUtil.getConstants();
        Map<String, Object> map = new HashMap<>(1);
        map.put(Constant.CLIENT_CONSTANTS_NAME, constants);
        // TODO: 18/04/10 桌台类型id等转换为名称
        map.put("tableCategories", shopTableService.categoriesStringPair(accountPO.getId(), accountPO.getShopId(), Integer.valueOf(branchId)));
        return new ModelAndView("merchants/tables", map);
    }

    @GetMapping("updateProduct")
    @ShopRequiredAuthority(ShopAuthorityEnum.BRANCH_PRODUCT_UPDATE)
    public ModelAndView updateProduct(@RequestParam Integer branchId, @RequestParam Integer productId) {
        AccountPO accountPO = ShopAccountHolder.get();
        BranchProductPO branchProduct = shopProductService.getBranchProduct(accountPO.getId(), accountPO.getShopId(), branchId, productId);
        Map<String, Object> map = new HashMap<>(1);
        map.put("product", branchProduct);
        map.put("productCategories", shopProductService.categoriesStringPair(accountPO.getId(), accountPO.getShopId(), branchId));
        map.put("branchDepartments", shopDepartmentService.departmentsStringPair(accountPO.getId(), accountPO.getShopId(), branchId));
        return new ModelAndView("merchants/updateProduct", map);
    }

}
