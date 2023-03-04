package com.poolc.springproject.poolcreborn.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.poolc.springproject.poolcreborn.model.ActivityStatus;
import com.poolc.springproject.poolcreborn.payload.request.LoginRequest;
import com.poolc.springproject.poolcreborn.payload.request.SignupRequest;
import com.poolc.springproject.poolcreborn.payload.request.UserUpdateRequest;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    @Test
    public void signupUser() throws Exception {
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
        String content = objectMapper.writeValueAsString(signupRequest);
        mockMvc.perform(post("/signup")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    @WithMockUser(username = "becooq81", password = "hello12345")
    @DisplayName("회원 정보 수정 성공")
    public void updateUser() throws Exception {
        UserUpdateRequest updateRequest = new UserUpdateRequest();
        updateRequest.setPassword("newpassword");
        updateRequest.setConfirmPassword("newpassword");
        updateRequest.setEmail("newemail@gmail.com");
        updateRequest.setMobileNumber("010-2353-8585");
        updateRequest.setDescription("new description");
        updateRequest.setActivityStatus(ActivityStatus.GRADUATED);
        String content = objectMapper.writeValueAsString(updateRequest);
        mockMvc.perform(patch("/my-info")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    /*@Test
    @DisplayName("회원 정보 수정 예외: 비밀번호 일치 X")
    public void updateUser

    @Test
    @DisplayName("회원 정보 수정 예외: 전화번호 포맷 일치 X")

    @Test
    @DisplayName("회원 탈퇴 성공")

    @Test
    @DisplayName("회원 탈퇴 실패: 틀린 메시지")
    */
}
