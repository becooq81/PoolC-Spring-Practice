package com.poolc.springproject.poolcreborn.controller.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.poolc.springproject.poolcreborn.payload.request.user.LoginRequest;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class LoginTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    private static LoginRequest createLoginRequest() {
        LoginRequest loginRequest = LoginRequest.builder()
                .username("becooq81")
                .password("hello12345")
                .build();
        return loginRequest;
    }


    @Test
    @DisplayName("로그인 성공")
    public void login() throws Exception {
        String content = objectMapper.writeValueAsString(createLoginRequest());
        mockMvc.perform(post("/login")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("로그인 실패 (틀린 사용자 이름)")
    public void login_wrong_username() throws Exception {
        LoginRequest loginRequest = LoginRequest.builder().username("wrongusername").password("wrongpassword").build();
        String content = objectMapper.writeValueAsString(loginRequest);

        mockMvc.perform(post("/login")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andDo(print());
    }

    @Test
    @DisplayName("로그인 실패 (틀린 비밀번호)")
    public void login_wrong_password() throws Exception {
        LoginRequest loginRequest = LoginRequest.builder().username("becooq81").password("wrongpassword").build();

        String content = objectMapper.writeValueAsString(loginRequest);

        mockMvc.perform(post("/login")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andDo(print());
    }


}
