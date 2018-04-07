package com.qudiancan.backend.aspect;

import com.qudiancan.backend.common.Constant;
import com.qudiancan.backend.util.CookieUtil;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author NINGTIANMIN
 */
public class CommonInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        request.setAttribute("basePath", String.format("%s://%s:%d%s", request.getScheme(), request.getServerName(),
                request.getServerPort(), request.getContextPath()));
        request.setAttribute(Constant.MERCHANT_CURRENT_SHOP_ID, CookieUtil.get(request, Constant.MERCHANT_CURRENT_SHOP_ID));
        request.setAttribute(Constant.MERCHANT_CURRENT_BRANCH_ID, CookieUtil.get(request, Constant.MERCHANT_CURRENT_BRANCH_ID));
        request.setAttribute(Constant.MERCHANT_CURRENT_ACCOUNT_NAME, CookieUtil.get(request, Constant.MERCHANT_CURRENT_ACCOUNT_NAME));
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

}
