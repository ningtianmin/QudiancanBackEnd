package com.qudiancan.backend.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

/**
 * @author NINGTIANMIN
 */
public class CookieUtil {
    public static void set(HttpServletResponse response, String name, String value, Integer expiry) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setMaxAge(expiry * 60);
        response.addCookie(cookie);
    }

    public static String get(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (Objects.isNull(cookies)) {
            return null;
        }
        Optional<Cookie> cookie = Arrays.stream(cookies).filter(o -> o.getName().equals(name)).findFirst();
        return cookie.isPresent() ? cookie.get().getValue() : null;
    }
}
