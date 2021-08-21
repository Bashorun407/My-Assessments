package com.search.booksearch.Controller;

import com.search.booksearch.Dao.BookStoreDto;
import com.search.booksearch.Entity.BookStore;
import com.search.booksearch.RestResponse.Restponsepojo;
import com.search.booksearch.Service.BookStoreService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Restponsepojo<List<BookStore>> searcBookStore(@RequestParam(name = "author", required = false) String author,
                                                         @RequestParam(name = "bookNumber", required = false) Long bookNumber,
                                                         @RequestParam(name = "title", required = false) String title,
                                                         @RequestParam(name = "genre", required = false) String genre) {

        return bookStoreService.searcBookStore(author, bookNumber, title, genre);
    }
        //API 4 to get a book by Id
    @GetMapping("/getbookbyid/{id}")
    public Restponsepojo<BookStore> getBookById(@PathVariable(name = "id") Long id){
        return bookStoreService.getBookById(id);
    }

    //API 6 to update a book by admin
    @PutMapping ("/adminupdate")
    public Restponsepojo<BookStore> adminUpdate(@RequestBody BookStoreDto bookStoreDto){
       return bookStoreService.adminUpdate(bookStoreDto);
    }


    //API to 7 delete a book
    @DeleteMapping("/delete/{id}")
    public Restponsepojo<BookStore> removeBook(@PathVariable Long id){
       return bookStoreService.removeBook(id);
    }

}
