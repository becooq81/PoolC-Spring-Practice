package com.poolc.springproject.poolcreborn.repository;

import com.poolc.springproject.poolcreborn.model.book.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Boolean existsByIsbn(String isbn);
    Page<Book> findAll(Pageable pageable);
}
