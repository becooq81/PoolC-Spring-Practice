package com.poolc.springproject.poolcreborn.controller;

import com.poolc.springproject.poolcreborn.exception.InvalidRequestException;
import com.poolc.springproject.poolcreborn.model.book.Book;
import com.poolc.springproject.poolcreborn.payload.request.book.BookDeleteRequest;
import com.poolc.springproject.poolcreborn.api.ApiSearchRequest;
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
public class BookController {

    private final BookRepository bookRepository;
    private final BookService bookService;
    private final BookMapper bookMapper;

    @PostMapping("/book/new")
    public ResponseEntity<?> registerBook(@RequestBody @Valid BookRequest bookRequest) {
        String username = getLoginUsername();
        try {
            bookService.saveBook(bookRequest, username);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(Message.SUCCESSFUL_CREATED_BOOK);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/book/{id}")
    public ResponseEntity<BookDto> viewBook(@PathVariable("id") @Min(1) Long bookId) {
        Book book = bookRepository.findById(bookId).orElse(null);
        return new ResponseEntity<>(bookMapper.buildBookDtoFromBook(book), HttpStatus.OK);
    }
    @DeleteMapping("/book/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable("id") @Min(1) Long bookId,
                                        @Valid @RequestBody BookDeleteRequest bookDeleteRequest) {
        String username = getLoginUsername();
        try {
            bookService.deleteBook(bookId, username);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(Message.SUCCESSFUL_DELETE_BOOK);
        } catch (InvalidRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }
    @GetMapping("/book/api/search")
    public ResponseEntity<?> searchBooks(@RequestBody @Valid ApiSearchRequest searchRequest) {
        try {
            List<BookDto> bookDtoList = bookService.bookSearch(searchRequest);
            return new ResponseEntity<>(bookDtoList, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @PostMapping("/book/api/search/{id}")
    public ResponseEntity<?> registerBookFromSearch(@RequestBody @Valid ApiSearchRequest searchRequest,
                                                          @PathVariable("id") @Min(1) Long bookId) {
        try {
            bookService.registerNaverBook(searchRequest, bookId);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(Message.SUCCESSFUL_CREATED_BOOK);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/library")
    public ResponseEntity<List<BookDto>> viewLibrary(@RequestParam int page, @RequestParam int size) {
        List<BookDto> bookDtoList = bookService.findAllBooks(page, size);
        return new ResponseEntity<>(bookDtoList, HttpStatus.OK);
    }
    @PutMapping("/library/{id}")
    public ResponseEntity<?> borrowBook(@PathVariable("id") @Min(1) Long bookId) {
        String username = getLoginUsername();
        try {
            bookService.borrowBook(bookId, username);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(Message.SUCCESSFUL_BORROW_BOOK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }
    @PatchMapping("/library/{id}")
    public ResponseEntity<?> returnBook(@PathVariable("id") @Min(1) Long bookId) {
        String username = getLoginUsername();
        try {
            bookService.returnBook(bookId, username);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(Message.SUCCESSFUL_RETURN_BOOK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }
}
