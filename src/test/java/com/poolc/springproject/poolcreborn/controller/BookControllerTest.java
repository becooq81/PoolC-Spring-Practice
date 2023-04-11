package com.poolc.springproject.poolcreborn.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.poolc.springproject.poolcreborn.payload.request.book.BookDeleteRequest;
import com.poolc.springproject.poolcreborn.payload.request.book.BookRequest;
import com.poolc.springproject.poolcreborn.payload.request.book.BookSearchRequest;
import com.poolc.springproject.poolcreborn.util.Message;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@Transactional
public class BookControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    private int bookId = 10;

    private static BookRequest createBookRequest() {
        return BookRequest.builder()
                .title("JPA")
                .isbn("8960777331")
                .author("김영한")
                .publisher("에이콘출판사")
                .build();
    }

    private static BookDeleteRequest createBookDeleteRequest() {
        return BookDeleteRequest.builder()
                .message("I confirm the deletion.")
                .build();
    }

    private static BookSearchRequest createBookSearchRequest() {
        return BookSearchRequest.builder()
                .query("Spring")
                .build();
    }
    @Test
    @WithAnonymousUser
    @DisplayName("등록 권한 없음")
    public void access_denied_to_register_book_test() throws Exception {
        BookRequest bookRequest = BookRequest.builder().build();
        String content = objectMapper.writeValueAsString(bookRequest);

        mockMvc.perform(post("/book/new")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andDo(print()
                );
    }

    @Test
    @WithMockUser(username="admin1234", roles={"CLUB_MEMBER", "ADMIN"})
    @DisplayName("책 등록 성공")
    public void successful_register_book() throws Exception {
        String content = objectMapper.writeValueAsString(createBookRequest());

        mockMvc.perform(post("/book/new")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().string(Message.SUCCESSFUL_CREATED_BOOK))
                .andDo(print());
    }

    @Test
    @WithMockUser(username="member1234", roles = {"CLUB_MEMBER"})
    @DisplayName("책 조회 성공")
    public void successful_view_book() throws Exception {
        mockMvc.perform(get(String.format("/book/%d", bookId))
                .param("id", String.valueOf(bookId)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    @Test
    @WithMockUser(username="admin1234", roles={"ADMIN"})
    @DisplayName("책 삭제 성공")
    public void successful_delete_book() throws Exception {
        String content = objectMapper.writeValueAsString(createBookDeleteRequest());
        mockMvc.perform(delete(String.format("/book/%d", bookId))
                        .param("id", String.valueOf(bookId))
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().string(Message.SUCCESSFUL_DELETE_BOOK))
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "member1234", roles={"CLUB_MEMBER"})
    @DisplayName("책 API 접근 제한")
    public void access_denied_api() throws Exception {
        String content = objectMapper.writeValueAsString(createBookSearchRequest());
        mockMvc.perform(get("/book/api/search")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "admin1234", roles = {"ADMIN"})
    @DisplayName("책 API 성공")
    public void successful_get_api() throws Exception {
        String content = objectMapper.writeValueAsString(createBookSearchRequest());
        mockMvc.perform(get("/book/api/search")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "admin1234", roles = {"ADMIN"})
    @DisplayName("책 API로 저장 성공")
    public void successful_save_from_api() throws Exception {
        String content = objectMapper.writeValueAsString(createBookSearchRequest());
        mockMvc.perform(post(String.format("/book/api/search/%d", 1))
                        .param("id", String.valueOf(1))
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().string(Message.SUCCESSFUL_CREATED_BOOK))
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "member1234", roles = {"CLUB_MEMBER"})
    public void successful_borrow_book() throws Exception {
        mockMvc.perform(put(String.format("/library/%d", bookId))
                .param("id", String.valueOf(bookId)))
                .andExpect(content().string(Message.SUCCESSFUL_BORROW_BOOK))
                .andDo(print());
    }
    @Test
    @WithAnonymousUser
    public void fail_borrow_book() throws Exception {
        mockMvc.perform(put(String.format("/library/%d", bookId))
                        .param("id", String.valueOf(bookId)))
                .andExpect(status().is4xxClientError())
                .andDo(print());
    }
}
