package com.poolc.springproject.poolcreborn.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.poolc.springproject.poolcreborn.payload.request.SignupRequest;
import com.poolc.springproject.poolcreborn.payload.request.UserUpdateRequest;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

}
