package com.example.jwt.jwt.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@Entity
public class User { //USER object 생성
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment
    private long id;
    private String username;
    private String password;
    private String roles; //USER,ADMIN,MANAGER

    public List<String> getRoleList() { //role이 두개 이상일 경우 사용
        if (this.roles.length() > 0) {
            return Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<>(); //null이 안뜨게
    }
}

