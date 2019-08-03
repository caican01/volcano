package com.movie.moviesite.config;

import com.movie.moviesite.interceptor.Interceptors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 配置类，自定义的拦截器需要在这里注册
 */
@Configuration  //这个注解很关键，@Configuration说明它标识的类是配置类
public class WebConfigurer implements WebMvcConfigurer {

    @Autowired
    private Interceptors interceptors;

    // 这个方法是用来配置静态资源的，比如html，js，css，等等
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

    }

    // 这个方法用来注册拦截器，我们自己写好的拦截器需要通过这里添加注册才能生效
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptors)
                .addPathPatterns("/user/edit","/user/update","/page/profile","/profile");  //所要拦截的路径
    }

}
