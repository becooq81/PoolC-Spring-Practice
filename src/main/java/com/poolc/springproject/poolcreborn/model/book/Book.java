package com.poolc.springproject.poolcreborn.model.book;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@Getter @Setter
public class Book {
    @Id @GeneratedValue
    private Long id;

    private String title;

    private Integer isbn;

    private String author;

    private LocalDate published;

    private String publisher;

    private Integer category;

    public Book() {}

    public Book(String title, Integer isbn, String author, LocalDate published, String publisher, Integer category) {
        this.title = title;
        this.isbn = isbn;
        this.author = author;
        this.published = published;
        this.publisher = publisher;
        this.category = category;
    }
}
