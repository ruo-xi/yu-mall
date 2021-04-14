package top.yu.mall.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import top.yu.mall.config.interceptor.AuthInterceptorHandler;
import top.yu.mall.config.properties.AuthSkipUrlProperties;

/**
 * @Author: yu
 * @Date: 4/13/21:3:12 PM
 */
@Configuration
public class IntercepterConfiguration implements WebMvcConfigurer {
    @Autowired
    AuthSkipUrlProperties authSkipUrlProperties;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptorHandler())
                .addPathPatterns("/**")
                .excludePathPatterns(authSkipUrlProperties.getUrls());
    }

    @Bean
    public AuthInterceptorHandler authInterceptorHandler() {
        return new AuthInterceptorHandler();
    }
}
