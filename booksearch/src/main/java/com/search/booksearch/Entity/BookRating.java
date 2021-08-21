package com.search.booksearch.Entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "Book_Rating")
public class BookRating {
    @Id
    @Column(name = "Book_ID_Number", unique = true)
    private Long bookId;
    @Column(name = "No_Of_Likes")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long likes;

}
