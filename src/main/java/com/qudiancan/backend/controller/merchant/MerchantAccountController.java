package com.qudiancan.backend.controller.merchant;

import com.qudiancan.backend.enums.StringPairDTO;
import com.qudiancan.backend.pojo.Response;
import com.qudiancan.backend.util.CommonUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

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

    @GetMapping("constants")
    @ResponseBody
    public Response<Map<String, List<StringPairDTO>>> getConstants() {
        return Response.success(CommonUtil.getConstants());
    }

}
