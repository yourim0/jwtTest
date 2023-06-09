package com.example.jwt.jwt.controller;

import com.example.jwt.jwt.config.repository.UserRepository;
import com.example.jwt.jwt.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@RestController //필터3을 통과하지 못하면 controller 진입 자체가 안된다.
@RequestMapping("/auth")
public class RestApiController {
    private User user;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;

    ///login은 spring security 기본 루트

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
            user.setEmail(user.getEmail());
            //user.setRoles(user.getRoles());
            user.setRoles("ROLE_USER");
            userRepository.save(user);
            return "회원가입완료";
    }

    //중복id체크
    @PostMapping("/checkId")
    public String checkId(@RequestBody Map<String, String> username) {
        System.out.println("checkId");
        String user = username.get("username");
        System.out.println("username: " + username);
        User checkUser = userRepository.findByUsername(user);
        System.out.println(checkUser);

        if (checkUser == null) {
            return "사용가능한 닉네임입니다.";
        } else if (checkUser != null) {
            return "이미 가입된 회원입니다.";
        }
        return "조회완료";
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