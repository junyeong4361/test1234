package dev.yhp.fin.configs;

import dev.yhp.fin.interceptors.CommonInterceptor;
import dev.yhp.fin.interceptors.SecurityInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.commonInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/resources/**");
        registry.addInterceptor(this.securityInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/resources/**");
    }

    @Bean
    protected CommonInterceptor commonInterceptor() {
        return new CommonInterceptor();
    }

    @Bean
    protected SecurityInterceptor securityInterceptor() {
        return new SecurityInterceptor();
    }
}