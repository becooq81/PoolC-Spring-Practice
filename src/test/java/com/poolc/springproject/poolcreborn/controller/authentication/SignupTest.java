package com.poolc.springproject.poolcreborn.controller.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.poolc.springproject.poolcreborn.payload.request.SignupRequest;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class SignupTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    private static SignupRequest createSignupRequest() {
        SignupRequest signupRequest = new SignupRequest(
                "becooq81",
                "hello12345",
                "hello12345",
                "지니",
                "jinny8748@gmail.com",
                "010-2341-5243",
                "computer science",
                2015232333,
                "hello"
        );
        return signupRequest;
    }

    @Test
    @DisplayName("회원가입 테스트")
    public void signup_test() throws Exception {

        String content = objectMapper.writeValueAsString(createSignupRequest());
        mockMvc.perform(post("/signup")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    @DisplayName("회원가입 예외: 아이디 조건 불만족 (숫자 없음)")
    public void signup_test_wrong_username1() throws Exception {
        SignupRequest signupRequest = createSignupRequest();
        signupRequest.setUsername("hello");
        String content = objectMapper.writeValueAsString(signupRequest);

        mockMvc.perform(post("/signup")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andDo(print());
    }

    @Test
    @DisplayName("회원가입 예외: 아이디 조건 불만족 (영어 없음)")
    public void signup_test_wrong_username2() throws Exception {
        SignupRequest signupRequest = createSignupRequest();
        signupRequest.setUsername("12345");
        String content = objectMapper.writeValueAsString(signupRequest);

        mockMvc.perform(post("/signup")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andDo(print());
    }

    @Test
    @DisplayName("회원가입 예외: 비밀번호 일치 X")
    public void signup_test_wrong_password() throws Exception {
        SignupRequest signupRequest = createSignupRequest();
        signupRequest.setPassword("wrongpassword");
        String content = objectMapper.writeValueAsString(signupRequest);

        mockMvc.perform(post("/signup")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andDo(print());
    }

    // @DisplayName("회원가입 예외: 전화번호 포맷 X")

    // @DisplayName("회원가입 예외: 학번 포맷 X")

    // @DisplayName("회원가입 예외: 자기소개 X")

}
