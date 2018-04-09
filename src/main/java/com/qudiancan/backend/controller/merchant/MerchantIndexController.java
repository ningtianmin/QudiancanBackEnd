package com.qudiancan.backend.controller.merchant;

import com.qudiancan.backend.common.Constant;
import com.qudiancan.backend.enums.StringPairDTO;
import com.qudiancan.backend.service.shop.ShopAccountService;
import com.qudiancan.backend.util.CommonUtil;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author NINGTIANMIN
 */
@Controller
@RequestMapping("/merchants")
public class MerchantIndexController {

    @Autowired
    private ShopAccountService shopAccountService;

    @GetMapping("/index")
    public ModelAndView index() {
        return new ModelAndView("merchants/index");
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

}
