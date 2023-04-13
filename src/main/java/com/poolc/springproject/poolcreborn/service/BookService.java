package com.poolc.springproject.poolcreborn.service;

import com.poolc.springproject.poolcreborn.api.NaverApiInvoker;
import com.poolc.springproject.poolcreborn.api.NaverApiInvokerCommand;
import com.poolc.springproject.poolcreborn.exception.InvalidRequestException;
import com.poolc.springproject.poolcreborn.exception.InvalidStateException;
import com.poolc.springproject.poolcreborn.model.book.Book;
import com.poolc.springproject.poolcreborn.model.user.User;
import com.poolc.springproject.poolcreborn.api.ApiSearchRequest;
import com.poolc.springproject.poolcreborn.payload.request.book.BookRequest;
import com.poolc.springproject.poolcreborn.payload.response.book.BookDto;
import com.poolc.springproject.poolcreborn.repository.BookRepository;
import com.poolc.springproject.poolcreborn.repository.UserRepository;
import com.poolc.springproject.poolcreborn.util.BookMapper;
import com.poolc.springproject.poolcreborn.util.Message;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.json.JSONArray;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookMapper bookMapper;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    public void saveBook(BookRequest bookRequest, String username) throws InvalidRequestException {
        User user = userRepository.findByUsername(username)
                    .orElseThrow(() ->  new InvalidRequestException(Message.USER_DOES_NOT_EXIST));

        if (user != null && user.isAdmin() && !bookRepository.existsByIsbn(bookRequest.getIsbn())) {
            Book book = new Book();
            bookMapper.buildBookFromRequest(bookRequest, book);
            bookRepository.save(book);
        }
    }

    public void deleteBook(Long bookId, String username) throws InvalidRequestException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new InvalidRequestException(Message.USER_DOES_NOT_EXIST));
        if (user != null && user.isAdmin()) {
            Optional<Book> book = bookRepository.findById(bookId);
            book.ifPresent(bookRepository::delete);
        }
    }

    public List<BookDto> findAllBooks(int page, int size) {
        PageRequest pr = PageRequest.of(page, size);
        Page<Book> books = bookRepository.findAll(pr);
        if (books.getNumberOfElements() == 0) {
            return null;
        }
        return books.stream()
                .map(bookMapper::buildBookDtoFromBook)
                .collect(Collectors.toList());
    }

    public void borrowBook(Long currentBookId, String username) throws Exception {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new InvalidRequestException(Message.USER_DOES_NOT_EXIST));
        Book book = bookRepository.findById(currentBookId)
                .orElseThrow(() -> new InvalidRequestException(Message.BOOK_DOES_NOT_EXIST));
        if (book != null && user != null && book.getCount() != 0) {
            book.decreaseCount();
            book.setBorrowerUsername(username);
        } else {
            throw new InvalidStateException(Message.BORROW_BOOK_DENIED);
        }
    }
    public void returnBook(Long currentBookId, String username) throws Exception {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new InvalidRequestException(Message.USER_DOES_NOT_EXIST));
        Book book = bookRepository.findById(currentBookId)
                .orElseThrow(() -> new InvalidRequestException(Message.BOOK_DOES_NOT_EXIST));
        if (book != null && user != null && book.getBorrowerUsername().equals(user.getUsername())) {
            book.increaseCount();
            book.setBorrowerUsername(null);
        } else {
            throw new InvalidRequestException(Message.RETURN_BOOK_DENIED);
        }
    }

    public void registerNaverBook(ApiSearchRequest searchRequest, Long bookId) throws Exception{
        List<BookDto> bookDtoList = bookSearch(searchRequest);
        BookDto bookDto = bookDtoList.get(bookId.intValue());
        Book book = bookMapper.buildBookFromBookDto(bookDto);
        bookRepository.save(book);
    }

    public List<BookDto> fromJSONtoBookDtoList(String result) {
        JSONObject rjson = new JSONObject(result);
        JSONArray naverBooks = rjson.getJSONArray("items");
        List<BookDto> naverBookDtoList = new ArrayList<>();
        for (int i = 0; i < naverBooks.length(); i++) {
            JSONObject naverBooksJson = (JSONObject) naverBooks.get(i);
            BookDto itemDto = new BookDto(naverBooksJson);
            naverBookDtoList.add(itemDto);
        }
        return naverBookDtoList;
    }

    public List<BookDto> bookSearch(ApiSearchRequest searchRequest) throws InvalidRequestException {
        NaverApiInvokerCommand command = new NaverApiInvokerCommand(searchRequest);
        NaverApiInvoker invoker = new NaverApiInvoker(command);
        ResponseEntity<String> result = invoker.naverBookSearchApi();
        return fromJSONtoBookDtoList(result.getBody());
    }
}
