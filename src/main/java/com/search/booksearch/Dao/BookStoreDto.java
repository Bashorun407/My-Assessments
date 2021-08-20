package com.search.booksearch.Dao;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BookStoreDto {
    private Long id;
    private Long bookNumber;
    private String bookTitle;
    private String genre;
    private String author;
    private BigDecimal price;
    private Long noAvailable;
    private String summary;
    private Boolean status;
    private Long likes;
    private Long love;
}
