package com.poolc.springproject.poolcreborn.controller.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.poolc.springproject.poolcreborn.payload.request.SignupRequest;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class LoginTest {

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

    // @DisplayName("")


}
