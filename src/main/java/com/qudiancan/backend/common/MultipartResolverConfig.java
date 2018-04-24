package com.qudiancan.backend.common;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.MultipartFilter;

import javax.servlet.MultipartConfigElement;

/**
 * @author NINGTIANMIN
 */
@Configuration
public class MultipartResolverConfig {

    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver multipart = new CommonsMultipartResolver();
        multipart.setMaxUploadSize(1024 * 1024 * 10);
        return multipart;
    }

    @Bean
    @Order(0)
    public MultipartFilter multipartFilter() {
        MultipartFilter multipartFilter = new MultipartFilter();
        multipartFilter.setMultipartResolverBeanName("multipartResolver");
        return multipartFilter;
    }

    @Bean
    MultipartConfigElement multipartConfigElement() {
        // 不添加location会导致部署在阿里云上的项目无法接收到layui上传的图片
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setLocation("d:/tmp");
        return factory.createMultipartConfig();
    }
}
