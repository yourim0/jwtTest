package com.example.jwt.jwt.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.jwt.jwt.config.auth.PrincipalDetails;
import com.example.jwt.jwt.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;


//스프링시큐리티에 UsernamePasswordAuthenticationFilter 가 있음. 로그인하는 역할.
// /login 요청해서 Username, password를 post로 전송하면 UsernamePasswordAuthenticationFilter 필터가 낚아채서 동작한다.
//AuthenticationManager

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    //생성자를 통한 파라미터 받기
    //public JwtAuthenticationFilter(authenticationManager()) {
    //}
    private final AuthenticationManager authenticationManager;

    JwtProperties jwtProperties;

// /login 요청 시 로그인 시도를 위해서 실행되는 함수
    @Override
            public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
                System.out.println("JwtAuthenticationFilter : 로그인 시도중");

                //1.username, password 받아서; //request.getInputStream() 안에 username, password가 들어있다.
                //확인 방법
                try {
//            BufferedReader br = request.getReader();
//            String input = null;
//            while((input = br.readLine()) != null){
//            System.out.println(input);
//        }
                    ObjectMapper om = new ObjectMapper(); //json 데이터를 파싱해주는 class
                    User user = om.readValue(request.getInputStream(), User.class); //User object에 담아준다.
            System.out.println(user);

            //토큰 생성
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());

            //PrincipalDetailsService의 loadUserByUsername()함수가 실행된 후 정상이면 authentication이 리턴된다.
            //db에있는 username과 pw가 일치한다.
            // token에 있는 username을 받음. pw는 스프링이처리
            //authentication 객체에 내 로그인 정보가 담긴다.
            Authentication authentication =
                    authenticationManager.authenticate(authenticationToken); //토큰 전달하면 인증을 실행한다.

            PrincipalDetails principalDetails = (PrincipalDetails)authentication.getPrincipal(); //object를 리턴하기 때문에 다운캐스팅
            System.out.println(principalDetails.getUser().getUsername());//  => 출력이 된다는건 인증(로그인)되었다는 뜻

            //리턴될때 authentication 객체가 Session에 저장된다.
            //권한 관리를 security가 대신 해주기 때문에 편리하게 사용하기 위해 리턴해준다. jwt토큰 사용시 세션을 굳이 사용하지 않아도 되지만 단지 권한 처리 때문에 넣는 것.

            return authentication;
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("======================================");

        return null;
    }

    //attemptAuthentication 실행 종료 후 인증이 정상적으로 되었으면 successfulAuthentication 함수 실행.
    //jwt 토큰을 만들어서 request한 사용자에게 jwt토큰을 response해주면 된다.

    //기존 : username, pw 로그인이 정상이면 서버쪽 세션ID를 생성, 클라이언트 쿠키 세션ID를 응답
    //요청할때마다 쿠키값 세션ID를 항상 들고 서버쪽으로 요청하기 때문에 서버는 세션ID가 유효한지 판단(서버에서 session.getAttribute("세션값확인")만하면된다)해서 유효하면 인증이 필요한 페이지로 접근하게 하면 된다.
    //토큰 : username, pw 로그인이 정상이면 JWT토큰을 생성, 클라이언트쪽으로 JWT토큰을 응답.
    //요청할때마다 JWT토큰을 가지고 요청, 서버는 JWT 토큰이 유효한지를 판단(필터를 만들어야 한다.)
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        System.out.println("successfulAuthentication 실행됨:인증이 완료되었다는 뜻");
        PrincipalDetails principalDetails = (PrincipalDetails)authResult.getPrincipal(); //이정보로 토큰 생성
        System.out.println("principalDetails" + principalDetails);
        //String secretKey = jwtProperties.secret;
       // System.out.println(secretKey);
        //RSA방식이 아닌 Hash 암호방식. HMAC512 특징은 시크릿값을 가지고 있어야 한다.
        String jwtToken = JWT.create()
                //.withSubject(principalDetails.getUsername()) //토큰이름
                .withSubject("cos토큰")
                .withExpiresAt(new Date(System.currentTimeMillis()+(60000)*10)) //만료시간(현재시간+1000이 1초.10분)
                        .withClaim("id",principalDetails.getUser().getId())
                                .withClaim("username",principalDetails.getUser().getUsername())
                                        .sign(Algorithm.HMAC512("limi")); //limi secretkey를 암호화해서 만든거
        response.addHeader("Authorization","Bearer "+jwtToken);

    }
}

//1.username, password 받아서;
//2.정상인지 authenticationManager 로 로그인 시도를 하면, PrincipalDetailsService 가 호출되고 loadUserByUsername() 함수가 실행된다.
//3. PrincipalDetails가 리턴되면 세션에 담고 -> 세션에 담는 이유 : 권한관리를 위해서. 권한관리가 필요없다면 세션에 담을 필요가 없다.
//4. JWT토큰을 만들어 응답해준다.