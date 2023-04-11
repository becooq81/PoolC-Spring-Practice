package com.poolc.springproject.poolcreborn.model.book;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Getter @Setter
public class Book {

    @Id @GeneratedValue
    private Long id;
    @NotEmpty @Size(max = 50)
    private String title;
    private String image;
    @NotEmpty
    private String isbn;
    @NotEmpty @Size(max = 30)
    private String author;
    private LocalDate published;
    private String publisher;
    private int count;
    private String borrowerUsername;

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
