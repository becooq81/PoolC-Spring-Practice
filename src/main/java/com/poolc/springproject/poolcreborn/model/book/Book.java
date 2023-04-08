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

    private String description;

}
