package com.example.jwt.jwt.config;

import com.example.jwt.jwt.filter.MyFilter1;
import com.example.jwt.jwt.filter.MyFilter2;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration//ioc 등록 config 파일임을 명시. request 요청이 오면 Filter 실행. SecurityConfig에 filter 등록 시 security Filter 가 우선.
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<MyFilter1> filter1(){
        FilterRegistrationBean<MyFilter1> bean = new FilterRegistrationBean<>(new MyFilter1());
        bean.addUrlPatterns("/*");
        bean.setOrder(0); //낮은 번호가 필터중 가장 먼저 실행.
        return bean;
    }

    @Bean
    public FilterRegistrationBean<MyFilter2> filter2(){
        FilterRegistrationBean<MyFilter2> bean = new FilterRegistrationBean<>(new MyFilter2());
        bean.addUrlPatterns("/*");
        bean.setOrder(0); //낮은 번호가 필터중 가장 먼저 실행.
        return bean;
    }
}
