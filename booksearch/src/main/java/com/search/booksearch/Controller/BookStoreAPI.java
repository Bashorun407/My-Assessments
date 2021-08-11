package com.search.booksearch.Controller;

import com.search.booksearch.Dao.BookStoreDto;
import com.search.booksearch.Entity.BookStore;
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
    public BookStore createBook(@RequestBody BookStoreDto bookStoreDto){
        return bookStoreService.createBook(bookStoreDto);
    }

    //API 2 to display all the books available
    @GetMapping("/allbooks")
    public List<BookStore> getAllBooks(){
        return bookStoreService.getAllBooks();
    }

    //API 4 to get a book by Id
    @GetMapping("/getbookbyid/{bookId}")
    public Optional<BookStore> getBookById(@PathVariable Long bookId){
        return bookStoreService.getBookById(bookId);
    }


    //API 5(a) to search a book by Title
    @GetMapping("/titlesearch/{searchWord}")
    public List<BookStore> titleSearch(@PathVariable String searchWord){
        return bookStoreService.titleSearch(searchWord);
    }

    //API 5(b) to search a book by Author
    @GetMapping("/authorsearch/{searchWord}")
    public List<BookStore> authorSearch( @PathVariable String searchWord) {
        return bookStoreService.authorSearch(searchWord);
    }

    //API 5(c) to search a book by Genre
    @GetMapping("/genresearch/{searchWord}")
    public List<BookStore> genreSearch(@PathVariable String searchWord){
        return bookStoreService.genreSearch(searchWord);
    }

    //API to test dynamic search if it works this time
    @GetMapping("/dynamicsearch/{searchWord}")
    public List<BookStore> dynamicSearch(@PathVariable String searchWord) {
        return bookStoreService.dynamicSearch(searchWord);
    }
    //API 6 to update a book by admin
    @PutMapping ("/adminupdate/{id}")
    public BookStore adminUpdate(@RequestBody BookStoreDto bookStoreDto, @PathVariable Long id){
       return bookStoreService.adminUpdate(bookStoreDto, id);
    }


    //API to 7 delete a book
    @DeleteMapping("/delete/{id}")
    public void removeBook(@PathVariable Long id){
        bookStoreService.removeBook(id);
    }

}
