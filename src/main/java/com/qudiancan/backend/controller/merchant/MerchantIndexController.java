package com.qudiancan.backend.controller.merchant;

import com.qudiancan.backend.service.shop.ShopAccountService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

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
        return new ModelAndView("merchant/index");
    }

    @RequestMapping("/logout")
    public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) {
        shopAccountService.logout(request, response);
        return new ModelAndView("merchant/login");
    }

    @RequestMapping("/suppleShopInfo")
    public ModelAndView suppleShopInfo() {
        return new ModelAndView("merchant/suppleShopInfo");
    }

}
