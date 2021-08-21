package com.search.booksearch.Entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity(name = "BOOK_STORE")
public class BookStore {
    @Id
    @Column(name = "Id", unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "BOOK_NUMBER")
    private Long bookNumber;
    private String bookTitle;
    private String genre;
    private String author;
    private Long volume;
    private BigDecimal price;
    private Long noAvailable;
    private String summary;
    private Boolean status=false;
    private Long likes;
    private Long love;
}
