package com.example.jwt.jwt.config;

import com.example.jwt.jwt.filter.MyFilter1;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CorsFilter corsFilter; //bean 등록이되어있으니까 그냥 가져와서 주입

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)//세션을 사용하지 않는다.
                .and()
                .addFilter(corsFilter) //모든 요청은 이 필터를 탐. @CrossOrigin(인증 없을 때), 시큐리티 필터에 등록 해주어야 함(인증이 있을 때)
                .formLogin().disable() //form 태그를 이용한 login 안씀
                .httpBasic().disable() //기본적 http 방식을 안씀
                .authorizeRequests()
                .antMatchers("/api/vi/user/**") //이 주소로 들어오면
                .access("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')") //user, manage, admin 접속이 가능하다.
                .antMatchers("/api/vi/manager/**")
                .access("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
                .antMatchers("/api/vi/admin/**")
                .access("hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll();//외 모든 주소는 권한없이 접속가능하다
    }
}
