package com.search.booksearch.Service;

import com.search.booksearch.Dao.BookStoreDto;
import com.search.booksearch.Entity.BookStore;
import com.search.booksearch.Reppo.BookRatingReppo;
import com.search.booksearch.Reppo.BookStoreReppo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookStoreService {
    @Autowired
    private BookStoreReppo bookStoreReppo;


    //Method to Create Book
    public BookStore createBook(BookStoreDto bookStoreDto){
        BookStore book= new BookStore();
        //Getting the contents of the book from the Data transfer object
        book.setBookId(bookStoreDto.getBookId());
        book.setBookTitle((bookStoreDto.getBookTitle()));
        book.setGenre(bookStoreDto.getGenre());
        book.setAuthor(bookStoreDto.getAuthor());
        book.setNoAvailable(bookStoreDto.getNoAvailable());
        book.setSummary(bookStoreDto.getSummary());
        book.setPrice(bookStoreDto.getPrice());
        book.setLikes(bookStoreDto.getLikes());
        book.setLove(bookStoreDto.getLove());

        //Saving the book
        return bookStoreReppo.save(book);
    }


    //Method to display all the books available
    public List<BookStore> getAllBooks(){
        List<BookStore> allBooks= bookStoreReppo.findAll();
        if(allBooks.isEmpty()==true){
            return null;
        }
        else
        return bookStoreReppo.findAll();
    }

    //Method to get a book by Id
    public Optional<BookStore> getBookById(Long bookId){
        return bookStoreReppo.findById(bookId);
    }

    //Method to return the last 10 books
    public List<BookStore> lastTenBooks(){

        return null;
    }


    //Method to search a book by Title
    public List<BookStore> titleSearch(String searchWord) {

   return bookStoreReppo.findBookStoreByBookTitle(searchWord);
    }

    //Method to search a book by Title
    public List<BookStore> authorSearch(String searchWord) {

        return bookStoreReppo.findBookStoreByAuthor(searchWord);
    }

    //Method to search a book by Genre
    public List<BookStore> genreSearch(String searchWord) {

        return bookStoreReppo.findBookStoreByGenre(searchWord);
    }

    //Attempting to write a dynamic search method
    public List<BookStore> dynamicSearch(String searchWord) {
            //If the searchWord matches the name of a book's title
            if (bookStoreReppo.findAll().stream()
            .anyMatch(x->x.getBookTitle()==searchWord)) {
                return bookStoreReppo.findAll().stream()
                        .filter(x->x.getBookTitle()==searchWord)
                        .collect(Collectors.toList());
            }

            //If the searchWord matches the name of a book's author
           else if (bookStoreReppo.findAll().stream()
                    .anyMatch(x->x.getAuthor()==searchWord)) {
                return bookStoreReppo.findAll().stream()
                        .filter(x->x.getAuthor()==searchWord)
                        .collect(Collectors.toList());
            }

            //If the searchWord matches the name of a book's genre
            else if (bookStoreReppo.findAll().stream()
                    .anyMatch(x->x.getGenre()==searchWord)) {
                return bookStoreReppo.findAll().stream()
                        .filter(x->x.getGenre()==searchWord)
                        .collect(Collectors.toList());
            }

            else return null;
    }


    //Method to update a book by admin
    public BookStore adminUpdate(BookStoreDto bookStoreDto, Long id){

        if(bookStoreReppo.findById(id).isPresent()) {
            BookStore bookUpdate = bookStoreReppo.findById(id).get();
            bookUpdate.setSummary(bookStoreDto.getSummary());
            bookUpdate.setNoAvailable(bookStoreDto.getNoAvailable());
            bookUpdate.setPrice(bookStoreDto.getPrice());
            bookUpdate.setStatus(true);
            //Saving the updated book
            return bookStoreReppo.save(bookUpdate);
        }

        else
            return null;
    }


    //Method to delete a book
    public void removeBook(Long id){
        bookStoreReppo.deleteById(id);
    }

}
