package com.example.jwt.jwt.controller;

import com.example.jwt.jwt.config.repository.UserRepository;
import com.example.jwt.jwt.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController //필터3을 통과하지 못하면 controller 진입 자체가 안된다.
public class RestApiController {
    private User user;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;


    @GetMapping("/home")
    public String home() {
        System.out.println("home");
        return "<h1>home</h1>";
    }

    @PostMapping("/token")
    public String token() {
        return "<h1>token</h1>";
    }

    @PostMapping("/join")
    public String join(@RequestBody User user) {
        System.out.println("join");

        System.out.println(user);
        System.out.println(user.getPassword());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles("ROLE_USER");
        userRepository.save(user);
        return "회원가입완료";
    }

    //user, manager, admin 권한 접근가능
    @GetMapping("/api/v1/user")
    public String user(){
        return "user";
    }

    //manager, admin 권한만 접근가능
    @GetMapping("/api/v1/manager")
    public String manager(){
        return "manager";
    }

    //admin 권한만 접근가능
    @GetMapping("/api/v1/admin")
    public String admin(){
        return "admin";
    }
}