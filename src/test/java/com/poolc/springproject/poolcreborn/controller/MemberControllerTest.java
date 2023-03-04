package com.poolc.springproject.poolcreborn.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.poolc.springproject.poolcreborn.model.ActivityStatus;
import com.poolc.springproject.poolcreborn.model.User;
import com.poolc.springproject.poolcreborn.payload.request.LoginRequest;
import com.poolc.springproject.poolcreborn.payload.request.SignupRequest;
import com.poolc.springproject.poolcreborn.payload.request.UserDeleteRequest;
import com.poolc.springproject.poolcreborn.payload.request.UserUpdateRequest;
import com.poolc.springproject.poolcreborn.repository.UserRepository;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.event.annotation.BeforeTestExecution;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import static com.poolc.springproject.poolcreborn.security.SecurityUtil.getLoginUsername;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class MemberControllerTest extends TestCase {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private  UserRepository userRepository;

    private User createUser() {
        User user = new User(
                "becooq81",
                "hello12345",
                "지니",
                "jinny8748@gmail.com",
                "010-2341-5243",
                "computer science",
                2015232333,
                "hello"
        );
        userRepository.save(user);
        return user;
    }



    @Test
    @WithAnonymousUser
    @DisplayName("수정 권한 없음")
    public void access_denied_to_update_test() throws Exception {
        String content = objectMapper.writeValueAsString(new UserUpdateRequest());
        mockMvc.perform(patch("/my-info")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andDo(print()
                );

    }

    @Test
    @DisplayName("회원 정보 수정 성공")
    @WithMockUser(username = "becooq81", password = "hello12345")
    public void update_test() throws Exception {
        UserUpdateRequest updateRequest = new UserUpdateRequest();
        updateRequest.setEmail("new@gmail.com");
        String content = objectMapper.writeValueAsString(updateRequest);
        mockMvc.perform(patch("/my-info")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("회원 정보 수정 예외: 비밀번호 일치 X")
    @WithMockUser(username = "becooq81", password = "hello12345")
    public void update_test_wrong_password() throws Exception {
        UserUpdateRequest updateRequest = new UserUpdateRequest();
        updateRequest.setPassword("newpassword");
        updateRequest.setConfirmPassword("wrongpassword");
        String content = objectMapper.writeValueAsString(updateRequest);
        mockMvc.perform(patch("/my-info")
                .with(user("becooq81").password("hello12345"))
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andDo(print()
                );
    }

    @Test
    @WithMockUser(username = "becooq81", password = "hello12345")
    @DisplayName("회원 정보 수정 예외: 전화번호 포맷 일치 X")
    public void update_test_wrong_mobileNumber() throws Exception {
        UserUpdateRequest updateRequest = new UserUpdateRequest();
        updateRequest.setMobileNumber("382-58583838");
        String content = objectMapper.writeValueAsString(updateRequest);
        mockMvc.perform(patch("/my-info")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andDo(print()
                );
    }

    @Test
    @WithAnonymousUser
    @DisplayName("삭제 권한 없음")
    public void access_denied_to_delete_test() throws Exception {
        String content = objectMapper.writeValueAsString(new UserDeleteRequest());
        mockMvc.perform(patch("/my-info")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andDo(print()
                );
    }

    @Test
    @DisplayName("회원 탈퇴 성공")
    @WithMockUser(username = "becooq81", password = "hello12345")
    public void delete_test() throws Exception {
        User user = createUser();
        UserDeleteRequest deleteRequest = new UserDeleteRequest("탈퇴를 확인합니다.");
        String content = objectMapper.writeValueAsString(deleteRequest);
        mockMvc.perform(delete("/my-info")
                        .with(user("becooq81").password("hello12345"))
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print()
                );
    }

    @Test
    @WithMockUser(username = "becooq81", password = "hello12345")
    @DisplayName("회원 탈퇴 실패: 틀린 메시지")
    public void delete_test_wrong_message() throws Exception {
        User user = createUser();
        UserDeleteRequest deleteRequest = new UserDeleteRequest("틀린 문구.");
        String content = objectMapper.writeValueAsString(deleteRequest);
        mockMvc.perform(delete("/my-info")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andDo(print()
                );
    }

}
