package com.poolc.springproject.poolcreborn.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.poolc.springproject.poolcreborn.model.activity.ActivityType;
import com.poolc.springproject.poolcreborn.model.activity.Day;
import com.poolc.springproject.poolcreborn.payload.request.activity.ActivityRequest;
import com.poolc.springproject.poolcreborn.payload.request.participation.ParticipationRequest;
import com.poolc.springproject.poolcreborn.payload.response.RequestedParticipationDto;
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
import java.util.ArrayList;
import java.util.List;

import static com.poolc.springproject.poolcreborn.util.Message.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@Transactional
public class ActivityControllerTest extends TestCase {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;


    private int activityId;

    @Before
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        activityId = 7;
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

    private static List<RequestedParticipationDto> createRqDtos() {
        RequestedParticipationDto rqDto = new RequestedParticipationDto(
                "admin1234",
                "자료구조",
                "경제학과"
        );
        List<RequestedParticipationDto> rqDtos = new ArrayList<>();
        rqDtos.add(rqDto);
        return rqDtos;
    }

    private static ParticipationRequest createApprovedParticipationRequest() {
        ParticipationRequest participationRequest = ParticipationRequest.builder()
                .isApproved(true)
                .build();
        return participationRequest;
    }
    private static ParticipationRequest createParticipationRequest() {
        ParticipationRequest participationRequest = ParticipationRequest.builder()
                .isApproved(false)
                .reason("세미나에 참가하여 열심히 공부하고자합니다!")
                .build();
        return participationRequest;
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
                .andExpect(content().string(SUCCESSFUL_CREATED_ACTIVITY))
                .andDo(print());
    }

    @Test
    @DisplayName("익명으로 활동 상세 페이지 접근")
    @WithAnonymousUser
    public void 익명_활동_상세_성공() throws Exception {
        mockMvc.perform(get(String.format("/activity/%d", activityId)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("익명으로 활동 신청")
    @WithAnonymousUser
    public void 익명_활동_신청_실패() throws Exception {
        String content = objectMapper.writeValueAsString(createApprovedParticipationRequest());
        mockMvc.perform(post(String.format("/activity/%d/participants", activityId))
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andDo(print());
    }

    @Test
    @DisplayName("준회원으로 활동 신청")
    @WithMockUser(username = "becooq81", password="hello12345")
    public void 준회원_활동_신청_실패() throws Exception {
        String content = objectMapper.writeValueAsString(createParticipationRequest());
        mockMvc.perform(post(String.format("/activity/%d/participants", activityId))
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andDo(print());
    }

    @Test
    @DisplayName("본인으로 활동 신청")
    @WithMockUser(username="member1234",roles={"CLUB_MEMBER"})
    public void 본인_활동_신청_실패() throws Exception {
        String content = objectMapper.writeValueAsString(createParticipationRequest());
        mockMvc.perform(post(String.format("/activity/%d/participants", activityId))
                        .param("id", String.valueOf(activityId))
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().string(SELF_SIGNUP_DENIED))
                .andDo(print());
    }

    @Test
    @DisplayName("활동 신청 성공")
    @WithMockUser(username="admin1234", roles={"CLUB_MEMBER", "ADMIN"})
    public void 활동_신청_성공() throws Exception {
        String content = objectMapper.writeValueAsString(createApprovedParticipationRequest());
        mockMvc.perform(post(String.format("/activity/%d/participants", activityId))
                        .param("id", String.valueOf(activityId))
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().string(SUCCESSFUL_SIGNUP_ACTIVITY))
                .andDo(print());
    }

    @Test
    @DisplayName("활동 요청 신청 성공")
    @WithMockUser(username="admin1234", roles={"CLUB_MEMBER", "ADMIN"})
    public void 활동_요청_신청_성공() throws Exception {
        String content = objectMapper.writeValueAsString(createParticipationRequest());
        mockMvc.perform(post(String.format("/activity/%d/participants", activityId))
                        .param("id", String.valueOf(activityId))
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().string(SUCCESSFUL_SIGNUP_REQUEST))
                .andDo(print());
    }

    @Test
    @DisplayName("활동 정원 초과")
    @WithMockUser(username="admin1234", roles={"CLUB_MEMBER", "ADMIN"})
    public void 활동_정원_초과() throws Exception {
        String content = objectMapper.writeValueAsString(createApprovedParticipationRequest());
        mockMvc.perform(post(String.format("/activity/%d/participants", 9))
                        .param("id", String.valueOf(9))
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().string(FAIL_SIGNUP_ACTIVITY))
                .andDo(print());
    }

    @Test
    @DisplayName("활동 요청 조회 성공")
    @WithMockUser(username="member1234", roles = {"CLUB_MEMBER"})
    public void 활동_요청_조회() throws Exception {
        mockMvc.perform(get(String.format("/activity/%d/participants/requested", activityId))
                .param("id", String.valueOf(activityId)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    @Test
    @DisplayName("활동 요청 조회 실패")
    @WithMockUser(username="member5678", roles = {"CLUB_MEMBER"})
    public void 활동_요청_조회_실패() throws Exception {
        mockMvc.perform(get(String.format("/activity/%d/participants/requested", activityId))
                        .param("id", String.valueOf(activityId)))
                .andExpect(content().json("[]"))
                .andDo(print());
    }

    @Test
    @DisplayName("활동 요청 승인 성공")
    @WithMockUser(username="member1234", roles = {"CLUB_MEMBER"})
    public void 활동_요청_승인_성공() throws Exception {
        String content = objectMapper.writeValueAsString(createRqDtos());
        mockMvc.perform(post(String.format("/activity/%d/participants/requested", activityId))
                .param("id", String.valueOf(activityId))
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().string(SUCCESSFUL_REQUEST_APPROVAL))
                .andDo(print());
    }

}
