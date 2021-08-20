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

    //API 2 to display all the books available
    @GetMapping("/allbooks")
    public Restponsepojo<BookStore> getAllBooks(){
        return bookStoreService.getAllBooks();
    }

    //API 4 to get a book by Id
    @GetMapping("/getbookbyid/{id}")
    public Restponsepojo<BookStore> getBookById(@PathVariable(name = "id") Long id){
        return bookStoreService.getBookById(id);
    }


    //API 5(a) to search a book by Title
    @GetMapping("/titlesearch/{searchWord}")
    public Restponsepojo<BookStore> titleSearch(@PathVariable String searchWord){
        return bookStoreService.titleSearch(searchWord);
    }

    //API 5(b) to search a book by Author
    @GetMapping("/authorsearch/{searchWord}")
    public Restponsepojo<BookStore> authorSearch( @PathVariable String searchWord) {
        return bookStoreService.authorSearch(searchWord);
    }

    //API 5(c) to search a book by Genre
    @GetMapping("/genresearch/{searchWord}")
    public Restponsepojo<BookStore> genreSearch(@PathVariable String searchWord){
        return bookStoreService.genreSearch(searchWord);
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
