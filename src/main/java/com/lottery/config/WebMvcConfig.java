package com.lottery.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * 图片绝对地址与虚拟地址映射
 */

@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    @Value("${locationpath}")
    private String locationpath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

//文件磁盘图片url 映射
//配置server虚拟路径，handler为前台访问的目录，locations为files相对应的本地路径
//        registry.addResourceHandler("/file/**").addResourceLocations("file:"+locationpath);
        registry.addResourceHandler("/file/images/**").addResourceLocations("file:"+locationpath+"images/");
        registry.addResourceHandler("/file/videos/**").addResourceLocations("file:"+locationpath+"videos/");
        registry.addResourceHandler("/file/others/**").addResourceLocations("file:"+locationpath+"others/");

        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");

        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");



    }

}
