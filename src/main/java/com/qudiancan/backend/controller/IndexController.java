package com.qudiancan.backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author NINGTIANMIN
 */
@Controller
public class IndexController {
    @RequestMapping("/")
    public ModelAndView index() {
        return new ModelAndView("merchants/login");
    }
}
