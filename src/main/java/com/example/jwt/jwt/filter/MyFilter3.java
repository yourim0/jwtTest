package com.example.jwt.jwt.filter;

import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class MyFilter3 implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest)request; //다운캐스팅
        HttpServletResponse res = (HttpServletResponse) response;

        //1. id와 pw가 정 상적으로 들어와서 로그인이 완료되면 토큰을 만들어주고 그걸 응답해준다.
        //2. 요청할 때마다 header에 Authorization에 value 값을 토큰을 가져온다.
        //3. 그때 토큰이 넘어오면 내가 만든 토큰이 맞는지만 검증하면 된다. RSA,HS256으로 (cos인지 확인할 필요 없다.)
        //private 키로 잠궈서 전달하고, 그걸 다시 가져올때 public키로 열어서 확인한다.
        //토큰 :  cos, cos라는 토큰이 넘어오면 인증성공, 아니면 chain.doFilter 를 타게 해서 인증하게 하고, 아니면 아예 controller 진입조차 못하도록 막는다.
        //security Filter가 동작되기 전에 여기서 걸러져야함.

        if(req.getMethod().equals("POST")) {
            System.out.println("POST 요청됨");
            String headerAuth = req.getHeader("Authorization");
            System.out.println(headerAuth);
            System.out.println("필터3");

            //if(StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            //    chain.doFilter(req,res);
            if(headerAuth.equals("cos")){ //임시토큰
                chain.doFilter(req,res);//프로세스가 끝나지 않도록 계속 chain에 넘겨준다.
            }else{
                PrintWriter out = res.getWriter();
                out.println("인증안됨");
            }
        }

        if(req.getMethod().equals("GET")) {
            System.out.println("GET 요청됨");
            chain.doFilter(req,res);
        }

    }
}
