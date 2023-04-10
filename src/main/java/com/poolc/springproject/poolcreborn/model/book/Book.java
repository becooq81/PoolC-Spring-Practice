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

    private String isbn;

    private String author;

    private LocalDate published;

    private String publisher;

    private int count;

    

    public Book() {}

    public Book(String title, String isbn, String author, LocalDate published, String publisher) {
        this.title = title;
        this.isbn = isbn;
        this.author = author;
        this.published = published;
        this.publisher = publisher;
        this.count = 1;
    }
    public void increaseCount() {
        this.count += 1;
    }
    public void decreaseCount() {
        this.count -= 1;
    }
}
