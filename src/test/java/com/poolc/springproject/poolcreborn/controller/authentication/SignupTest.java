package com.poolc.springproject.poolcreborn.controller.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.poolc.springproject.poolcreborn.payload.request.user.SignupRequest;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static com.poolc.springproject.poolcreborn.util.Message.SUCCESSFUL_SIGNUP_USER;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@Transactional
public class SignupTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    private static SignupRequest createSignupRequest() {
        SignupRequest signupRequest = SignupRequest.builder()
                .username("jinny4823")
                .password("hello12345")
                .confirmPassword("hello12345")
                .name("지니")
                .email("jinny8748@gmail.com")
                .mobileNumber("010-2381-2312")
                .major("computer science")
                .studentId(2021727321)
                .description("hello")
                .build();
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
                .andExpect(content().string(SUCCESSFUL_SIGNUP_USER))
                .andDo(print());

    }

    @Test
    @DisplayName("회원가입 예외: 아이디 조건 불만족 (숫자 없음)")
    public void signup_test_wrong_username1() throws Exception {
        SignupRequest signupRequest = createSignupRequest();
        signupRequest = signupRequest.toBuilder().username("hello").build();
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
        signupRequest = signupRequest.toBuilder().username("12345").build();
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
        signupRequest = signupRequest.toBuilder().password("wrongpassword").build();
        String content = objectMapper.writeValueAsString(signupRequest);

        mockMvc.perform(post("/signup")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andDo(print());
    }

    @Test
    @DisplayName("회원가입 예외: 전화번호 포맷 X")
    public void signup_test_wrong_mobileNumber_format() throws Exception {
        SignupRequest signupRequest = createSignupRequest();
        signupRequest = signupRequest.toBuilder().mobileNumber("28381-3823").build();
        String content = objectMapper.writeValueAsString(signupRequest);

        mockMvc.perform(post("/signup")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andDo(print());
    }

    @Test
    @DisplayName("회원가입 예외: 빈 항목")
    public void signup_test_empty() throws Exception {
        SignupRequest signupRequest = SignupRequest.builder()
                .username("")
                .major("")
                .name("")
                .email("")
                .studentId(0)
                .email("")
                .description("")
                .password("")
                .confirmPassword("")
                .build();

        String content = objectMapper.writeValueAsString(signupRequest);

        mockMvc.perform(post("/signup")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andDo(print());
    }


}
