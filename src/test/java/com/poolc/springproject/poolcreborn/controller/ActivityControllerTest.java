package com.poolc.springproject.poolcreborn.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.poolc.springproject.poolcreborn.model.activity.ActivityType;
import com.poolc.springproject.poolcreborn.model.activity.Day;
import com.poolc.springproject.poolcreborn.payload.request.activity.ActivityRequest;
import com.poolc.springproject.poolcreborn.repository.UserRepository;
import com.poolc.springproject.poolcreborn.service.UserService;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@Transactional
public class ActivityControllerTest extends TestCase {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;
    /*@Before
    public void init() {
        user = new User(
                "becooq81",
                "hello12345",
                "김수진",
                "becooq81@gmail.com",
                "010-5832-6853",
                "수학과",
                201828312,
                "안녕하세요"
        );
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }*/

    @Before
    public void setObjectMapper() {
        objectMapper.registerModule(new JavaTimeModule());
    }

    private static ActivityRequest createActivityRequest() {
        ActivityRequest activityRequest = ActivityRequest.builder()
                .title("Data structure")
                .startDate(LocalDate.parse("2023-01-08"))
                .activityType(ActivityType.SEMINAR)
                .capacity(10)
                .day(Day.FRIDAY)
                .hours(2)
                .tags(List.of("Java", "algorithms"))
                .plan("매주 열심히 공부합시다!")
                .build();
        return activityRequest;
    }

    @WithAnonymousUser
    @Test
    @DisplayName("익명으로 활동 개설 페이지 접근")
    public void 익명_활동개설_불가() throws Exception {
        String content = objectMapper.writeValueAsString(createActivityRequest());
        mockMvc.perform(post("/activity/new")
               .content(content)
               .contentType(MediaType.APPLICATION_JSON)
               .accept(MediaType.APPLICATION_JSON))
               .andExpect(status().is4xxClientError())
               .andDo(print());
    }

    @WithMockUser(username = "becooq81", password="hello12345")
    @Test
    @DisplayName("준회원으로 활동 개설 페이지 접근")
    public void 준회원_활동개설_불가() throws Exception {
        String content = objectMapper.writeValueAsString(createActivityRequest());
        mockMvc.perform(post("/activity/new")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andDo(print());
    }


    @Test
    @DisplayName("정회원 활동 개설 페이지 접근")
    @WithMockUser(username="member1234",roles={"CLUB_MEMBER"})
    public void 회원_활동개설_성공() throws Exception {
        String content = objectMapper.writeValueAsString(createActivityRequest());
        mockMvc.perform(post("/activity/new")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }


}
