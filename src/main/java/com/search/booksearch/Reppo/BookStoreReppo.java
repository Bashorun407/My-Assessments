package com.search.booksearch.Reppo;

import com.search.booksearch.Entity.BookStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookStoreReppo extends JpaRepository<BookStore, Long> {


     //These methods were written to enbale the dynamic search in the Service
     Optional<BookStore> findBookStoreByBookTitle(String search);
     List<BookStore> findBookStoreByAuthor(String search);
     List <BookStore> findBookStoreByGenre(String search);


}
