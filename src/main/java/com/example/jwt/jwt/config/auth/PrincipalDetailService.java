package com.example.jwt.jwt.config.auth;

import com.example.jwt.jwt.config.repository.UserRepository;
import com.example.jwt.jwt.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//http://localhost:8080/login 요청이 올때 동작./login은 security의 로그인 기본 경로
//security config에 disable 해두었기 때문에 동작 안함.

@Service
@RequiredArgsConstructor
public class PrincipalDetailService implements UserDetailsService {

    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("username: "+username);
        System.out.println("PrincipleDetailService 의 loadUserByUsername()");
        User userEntity = userRepository.findByUsername(username);
        System.out.println("userEntity" + userEntity);
        return new PrincipalDetails(userEntity);
    }
}
