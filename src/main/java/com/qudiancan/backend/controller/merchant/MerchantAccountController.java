package com.qudiancan.backend.controller.merchant;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author NINGTIANMIN
 */
@Controller
@RequestMapping("/merchants")
public class MerchantAccountController {

    @RequestMapping("/register")
    public ModelAndView register() {
        return new ModelAndView("merchant/register");
    }

    @RequestMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("merchant/login");
    }

}
