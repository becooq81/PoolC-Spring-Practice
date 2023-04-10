package com.poolc.springproject.poolcreborn.controller;

import com.poolc.springproject.poolcreborn.model.book.Book;
import com.poolc.springproject.poolcreborn.payload.request.book.BookSearchRequest;
import com.poolc.springproject.poolcreborn.payload.request.book.BookRequest;
import com.poolc.springproject.poolcreborn.payload.response.book.BookDto;
import com.poolc.springproject.poolcreborn.repository.BookRepository;
import com.poolc.springproject.poolcreborn.service.BookService;
import com.poolc.springproject.poolcreborn.util.BookMapper;
import com.poolc.springproject.poolcreborn.util.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import java.util.List;

import static com.poolc.springproject.poolcreborn.security.SecurityUtil.getLoginUsername;

@RestController
@RequiredArgsConstructor
@RequestMapping("/book")
public class BookController {

    private final BookRepository bookRepository;
    private final BookService bookService;
    private final BookMapper bookMapper;

    @PostMapping("/new")
    public ResponseEntity<?> registerBook(@RequestBody @Valid BookRequest bookRequest) {
        String username = getLoginUsername();
        bookService.saveBook(bookRequest, username);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Message.SUCCESSFUL_CREATED_BOOK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDto> viewBook(@PathVariable("id") @Min(1) Long currentBookId) {
        Book book = bookRepository.findById(currentBookId).orElse(null);
        return new ResponseEntity<>(bookMapper.buildBookDtoFromBook(book), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable("id") @Min(1) Long currentBookId) {
        String username = getLoginUsername();
        bookService.deleteBook(currentBookId, username);
        return ResponseEntity.status(HttpStatus.OK)
                .body(Message.SUCCESSFUL_DELETE_BOOK);
    }

    @GetMapping("/api/search")
    public ResponseEntity<List<BookDto>> searchBooks(BookSearchRequest bookSearchRequest) {
        List<BookDto> bookDtoList = bookService.naverBookSearchApi(bookSearchRequest);
        return new ResponseEntity<>(bookDtoList, HttpStatus.OK);
    }

    @GetMapping("/api/search/")
}
