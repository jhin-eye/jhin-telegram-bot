package com.yanoos.global.config;

import com.yanoos.global.interceptor.AuthenticationInterceptor;
import com.yanoos.global.interceptor.LoggingInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    private final LoggingInterceptor loggingInterceptor;
    private final AuthenticationInterceptor authenticationInterceptor;

    //인터셉터 등록
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //요청 uri 로깅용 인터셉터
        registry.addInterceptor(loggingInterceptor)
                .order(1);

        registry.addInterceptor(authenticationInterceptor)
                .order(2)
                .addPathPatterns("/api/**",
                        "/test/**")
                .excludePathPatterns(
                        "/api/kakao/**",
                        "/api/token/by/refresh");

    }
}
