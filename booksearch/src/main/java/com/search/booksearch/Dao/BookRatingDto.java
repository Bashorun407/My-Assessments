package com.search.booksearch.Dao;

import lombok.Data;

@Data
public class BookRatingDto {
    private Long bookId;
    private Long likes;
}
