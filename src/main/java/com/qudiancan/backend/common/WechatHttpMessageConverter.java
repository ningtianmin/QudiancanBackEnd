package com.qudiancan.backend.common;

import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.Collections;

/**
 * @author NINGTIANMIN
 */
public class WechatHttpMessageConverter extends MappingJackson2HttpMessageConverter {
    public WechatHttpMessageConverter() {
        setSupportedMediaTypes(Collections.singletonList(MediaType.TEXT_PLAIN));
    }
}
