package com.search.booksearch.Reppo;

import com.search.booksearch.Entity.BookRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRatingReppo extends JpaRepository<BookRating, Long> {
}
