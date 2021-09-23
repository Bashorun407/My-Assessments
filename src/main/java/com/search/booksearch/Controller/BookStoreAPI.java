package com.search.booksearch.Controller;

import com.search.booksearch.Dao.BookStoreDto;
import com.search.booksearch.Entity.BookStore;
import com.search.booksearch.RestResponse.Restponsepojo;
import com.search.booksearch.Service.BookStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookStoreAPI {
    @Autowired
    private BookStoreService bookStoreService;


    //API 1 to Create Book
    @PostMapping("/create")
    public Restponsepojo<BookStore> createBook(@RequestBody BookStoreDto bookStoreDto){
        return bookStoreService.createBook(bookStoreDto);
    }

    @GetMapping("/search")
    public Restponsepojo<Page<BookStore>> searcBookStore(@RequestParam(name = "author", required = false) String author,
                                                         @RequestParam(name = "bookNumber", required = false) Long bookNumber,
                                                         @RequestParam(name = "title", required = false) String title,
                                                         @RequestParam(name = "genre", required = false) String genre,
                                                         Pageable pageable) {

        return bookStoreService.searchBookStore(author, bookNumber, title, genre, pageable);
    }
        //API 4 to get a book by Id
    @GetMapping("/getbookbyid/{id}")
    public Restponsepojo<BookStore> getBookById(@PathVariable(name = "id") Long id){
        return bookStoreService.getBookById(id);
    }

    //Repeating search here to practice
    @GetMapping("/dynamicSearch")
    public  Restponsepojo<Page<BookStore>> dynamicSearch(@RequestParam(name = "author", required = false) String author,
                                                         @RequestParam(name = "title", required = false) String title,
                                                         @RequestParam(name = "bookNumber", required = false) Long bookNumber,
                                                         @RequestParam(name = "genre", required = false) String genre,
                                                         Pageable pageable, Long nLikes){
        return bookStoreService.dynamicSearch(author, title, bookNumber, genre, pageable, nLikes);
    }

    //API 6 to update a book by admin
    @PutMapping ("/adminupdate")
    public Restponsepojo<BookStore> adminUpdate(@RequestBody BookStoreDto bookStoreDto){
       return bookStoreService.adminUpdate(bookStoreDto);
    }

    //API 7a to like a student
    @PutMapping("/likeStudent/{id}")
    public Restponsepojo<Long> likeAStudent( @PathVariable Long id){
        return bookStoreService.likeAStudent(id);
    }

    //API 7b to love a student
    @PutMapping("/loveStudent/{id}")
    public Restponsepojo<Long> loveAStudent( @PathVariable Long id){
        return bookStoreService.loveAStudent(id);
    }

    //API to 8 delete a book
    @DeleteMapping("/delete/{id}")
    public Restponsepojo<BookStore> removeBook(@PathVariable Long id){
       return bookStoreService.removeBook(id);
    }

}

