package com.poolc.springproject.poolcreborn.service;

import com.poolc.springproject.poolcreborn.exception.InvalidStateException;
import com.poolc.springproject.poolcreborn.exception.InvalidUserException;
import com.poolc.springproject.poolcreborn.model.book.Book;
import com.poolc.springproject.poolcreborn.model.user.User;
import com.poolc.springproject.poolcreborn.payload.request.book.BookSearchRequest;
import com.poolc.springproject.poolcreborn.payload.request.book.BookRequest;
import com.poolc.springproject.poolcreborn.payload.response.book.BookDto;
import com.poolc.springproject.poolcreborn.repository.BookRepository;
import com.poolc.springproject.poolcreborn.repository.UserRepository;
import com.poolc.springproject.poolcreborn.util.BookMapper;
import com.poolc.springproject.poolcreborn.util.Message;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
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
    @Value("${poolcreborn.app.clientId}")
    private String clientId;
    @Value("${poolcreborn.app.clientSecret}")
    private String clientSecret;

    public void saveBook(BookRequest bookRequest, String username) {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user != null && user.isAdmin() && !bookRepository.existsByIsbn(bookRequest.getIsbn())) {
            Book book = new Book();
            bookMapper.buildBookFromRequest(bookRequest, book);
            bookRepository.save(book);
        }
    }

    public void deleteBook(Long bookId, String username) {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user != null && user.isAdmin()) {
            Optional<Book> book = bookRepository.findById(bookId);
            book.ifPresent(bookRepository::delete);
        }
    }

    public List<BookDto> naverBookSearchApi(BookSearchRequest bookSearchRequest) {
        String url = "https://openapi.naver.com/";

        URI uri = UriComponentsBuilder.fromHttpUrl(url)
                .path("v1/search/book.json")
                .queryParam("query", bookSearchRequest.getQuery())
                .queryParam("display", 10)
                .queryParam("start", 1)
                .queryParam("sort", "sim")
                .encode()
                .build()
                .toUri();

        RestTemplate restTemplate = new RestTemplate();

        RequestEntity<Void> req = RequestEntity
                .get(uri)
                .header("X-Naver-Client-Id", clientId)
                .header("X-Naver-Client-Secret", clientSecret)
                .build();

        ResponseEntity<String> result = restTemplate.exchange(req, String.class);
        List<BookDto> bookDtoList = fromJSONtoBookDtoList(result.getBody());

        return bookDtoList;

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

    public void borrowBook(Long currentBookId, String username) throws InvalidStateException {
        User user = userRepository.findByUsername(username).orElse(null);
        Book book = bookRepository.findById(currentBookId).orElse(null);
        if (book != null && user != null && book.getCount() != 0) {
            book.decreaseCount();
            book.setBorrowerUsername(username);
        } else {
            throw new InvalidStateException(Message.BORROW_BOOK_DENIED);
        }
    }
    public void returnBook(Long currentBookId, String username) throws InvalidUserException {
        User user = userRepository.findByUsername(username).orElse(null);
        Book book = bookRepository.findById(currentBookId).orElse(null);
        if (book != null && user != null && book.getBorrowerUsername().equals(user.getUsername())) {
            book.increaseCount();
            book.setBorrowerUsername(null);
        } else {
            throw new InvalidUserException(Message.RETURN_BOOK_DENIED);
        }
    }

    public void registerNaverBook(BookSearchRequest searchRequest, Long bookId) {
        List<BookDto> bookDtoList = naverBookSearchApi(searchRequest);
        BookDto bookDto = bookDtoList.get(bookId.intValue());
        Book book = bookMapper.buildBookFromBookDto(bookDto);
        bookRepository.save(book);
    }
}
