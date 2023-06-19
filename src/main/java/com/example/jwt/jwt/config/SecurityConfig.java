package com.example.jwt.jwt.config;

import com.example.jwt.jwt.config.repository.UserRepository;
import com.example.jwt.jwt.filter.MyFilter1;
import com.example.jwt.jwt.filter.MyFilter3;
import com.example.jwt.jwt.jwt.JwtAuthenticationFilter;
import com.example.jwt.jwt.jwt.JwtAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CorsFilter corsFilter; //bean 등록이되어있으니까 그냥 가져와서 주입
    private final UserRepository userRepository;

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        //http.addFilterBefore(new MyFilter3(), BasicAuthenticationFilter.class);//security filter chain이 무조건 우선이다. 내가 만든 filter가 무조건 먼저 실행하게 하려면 securityfilterchain의 첫번째 filter보다 before로 설정해준다.
        //http.addFilterBefore(new MyFilter3(), SecurityContextPersistenceFilter.class);

        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)//세션을 사용하지 않는다.
                .and()
                .addFilter(corsFilter) //모든 요청은 이 필터를 탐. @CrossOrigin(인증 없을 때), 시큐리티 필터에 등록 해주어야 함(인증이 있을 때)
                .formLogin().disable() //form 태그를 이용한 login 안씀
                .addFilter(new JwtAuthenticationFilter(authenticationManager()))//UsernamePasswordAuthenticationFilter 사용하기 위함. AuthenticationManager 파라미터가 필요하다.
                .addFilter(new JwtAuthorizationFilter(authenticationManager(),userRepository)) ///login실행
                .httpBasic().disable() //기본적 http 방식을 안씀
                .authorizeRequests()
                .antMatchers("/auth/api/v1/user/**") //이 주소로 들어오면
                .access("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')") //user, manage, admin 접속이 가능하다.
                .antMatchers("/auth/api/v1/manager/**")
                .access("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
                .antMatchers("/auth/api/v1/admin/**")
                .access("hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll();//외 모든 주소는 권한없이 접속가능하다
    }
}
