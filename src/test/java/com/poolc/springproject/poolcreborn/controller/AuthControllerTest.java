package com.poolc.springproject.poolcreborn.controller;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class AuthControllerTest {

    MockMvc mockMvc;

    @Test
    @DisplayName("회원가입 테스트")
    public void signup_test() throws Exception {

        String username;
        String password;
        String confirmpassword;
        String name;
        String email;
        String mobileNumber;
        String major;
        int studentId;
        String description;



    }

    // @DisplayName("회원가입 예외: 아이디 조건 불만족")

    // @DisplayName("회원가입 예외: 비밀번호 일치 X")

    // @DisplayName("회원가입 예외: 전화번호 포맷 X")

    // @DisplayName("회원가입 예외: 학번 포맷 X")

    // @DisplayName("회원가입 예외: 자기소개 X")

}
