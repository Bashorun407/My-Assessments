package com.search.booksearch.Entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity(name = "Book_Store")
public class BookStore {
    @Id
    @Column(name = "ID_Number", unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "Book_ID_Number", unique = true)
    private Long bookNumber;
    private String bookTitle;
    private String genre;
    private String author;
    private BigDecimal price;
    private Long noAvailable;
    private String summary;
    private Boolean status=false;
    private Long likes;
    private Long love;
}
