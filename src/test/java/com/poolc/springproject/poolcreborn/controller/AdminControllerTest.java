package com.poolc.springproject.poolcreborn.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.poolc.springproject.poolcreborn.model.EMessage;
import com.poolc.springproject.poolcreborn.payload.request.user.UserVo;
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

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@Transactional
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    private static List<UserVo> createUserVos() {
        List<UserVo> userVos = new ArrayList<>();
        UserVo userVo = UserVo.builder()
                .username("member1234")
                .isMember(true)
                .isAdmin(true)
                .isClubMember(true)
                .build();
        userVos.add(userVo);
        return userVos;
    }

    @Test
    @DisplayName("익명으로 관리자페이지 접근")
    @WithAnonymousUser
    public void 익명_관리자페이지() throws Exception {
        mockMvc.perform(get("/admin"))
                .andExpect(status().is4xxClientError())
                .andDo(print());
    }

    @Test
    @DisplayName("준회원으로 관리자페이지 접근")
    @WithMockUser(username = "becooq81", password = "hello12345")
    public void 준회원_관리자페이지() throws Exception {
        mockMvc.perform(get("/admin"))
                .andExpect(status().is4xxClientError())
                .andDo(print());
    }

    @Test
    @DisplayName("정회원으로 관리자페이지 접근")
    @WithMockUser(username = "member1234", password = "hello12345")
    public void 정회원_관리자페이지() throws Exception {
        mockMvc.perform(get("/admin"))
                .andExpect(status().is4xxClientError())
                .andDo(print());
    }

    @Test
    @DisplayName("관리자가 관리페이지 접근")
    @WithMockUser(username = "admin1234", password = "admin1234", roles={"ADMIN", "USER"})
    public void 관리자_관리자페이지() throws Exception {
        mockMvc.perform(get("/admin")
                        .param("page", "1")
                        .param("size", "100"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("회원 권한 추가 성공")
    @WithMockUser(username = "admin1234", password = "admin1234", roles={"ADMIN", "USER"})
    public void 관리자_권한_추가() throws Exception {
        String content = objectMapper.writeValueAsString(createUserVos());
        mockMvc.perform(patch("/admin/roles")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().string(EMessage.SUCCESSFUL_ROLE_ADD.getMessage()))
                .andDo(print());
    }
}
