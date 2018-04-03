package com.qudiancan.backend.util;

import com.alibaba.fastjson.JSON;
import com.qudiancan.backend.enums.ResponseEnum;
import com.qudiancan.backend.exception.ShopException;
import com.qudiancan.backend.pojo.vo.shop.CartVO;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author NINGTIANMIN
 */
@Slf4j
public class JsonToObjectUtil {
    public static List<CartVO> toCartVO(String cartData) {
        try {
            return JSON.parseArray(cartData, CartVO.class);
        } catch (Exception e) {
            log.error("【对象转换出错】cartData：{}", cartData);
            throw new ShopException(ResponseEnum.PARAM_INVALID, cartData);
        }
    }
}
