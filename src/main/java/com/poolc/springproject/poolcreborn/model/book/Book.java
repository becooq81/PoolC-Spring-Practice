package com.poolc.springproject.poolcreborn.model.book;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

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
    private String publisher;
    private int count;
    private String borrowerUsername;

    public Book() {
        this.count = 1;
    }

    public Book(String title, String image, String isbn, String author, String publisher, int count, String borrowerUsername) {
        this.title = title;
        this.image = image;
        this.isbn = isbn;
        this.author = author;
        this.publisher = publisher;
        this.count = count;
        this.borrowerUsername = borrowerUsername;
    }

    public void increaseCount() {
        this.count += 1;
    }
    public void decreaseCount() {
        this.count -= 1;
    }
}
