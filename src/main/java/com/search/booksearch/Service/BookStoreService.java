package com.search.booksearch.Service;

import com.search.booksearch.Dao.BookStoreDto;
import com.search.booksearch.Entity.BookStore;
import com.search.booksearch.Exception.ApiException;
import com.search.booksearch.Reppo.BookStoreReppo;
import com.search.booksearch.RestResponse.Restponsepojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityManager;
import java.awt.print.Book;
import java.util.List;
import java.util.Optional;

@Service
public class BookStoreService {
    @Autowired
    private BookStoreReppo bookStoreReppo;

    //For QueryDsl
    private EntityManager entityManager;

    //Method to Create Book
    public Restponsepojo<BookStore> createBook(BookStoreDto bookStoreDto){

        //To check if empty payload was sent by user
        if(ObjectUtils.isEmpty(bookStoreDto.getBookNumber()))
            throw new ApiException("Invalid Book Can Not Be Created!!");

        //To check that a book with the same id does not already exist
        Optional<BookStore> bookStoreOptional = bookStoreReppo.findById(bookStoreDto.getBookNumber());

        if(bookStoreOptional.isPresent())
        throw new ApiException(String.format("Book with this id %s already exists",
                bookStoreReppo.findById(bookStoreDto.getBookNumber())));

        BookStore book= new BookStore();
        //Getting the contents of the book from the Data transfer object
        book.setBookNumber(bookStoreDto.getBookNumber());
        book.setBookTitle((bookStoreDto.getBookTitle()));
        book.setGenre(bookStoreDto.getGenre());
        book.setAuthor(bookStoreDto.getAuthor());
        book.setNoAvailable(bookStoreDto.getNoAvailable());
        book.setSummary(bookStoreDto.getSummary());
        book.setPrice(bookStoreDto.getPrice());
        book.setLikes(bookStoreDto.getLikes());
        book.setLove(bookStoreDto.getLove());

        //Saving the book and assigning the new book to an object of the class
        BookStore newBook= bookStoreReppo.save(book);

        //Instantiating the response pojo
        Restponsepojo<BookStore> restponsepojo = new Restponsepojo<>();
        restponsepojo.setData(newBook);
        restponsepojo.setSuccess(true);
        restponsepojo.setStatus(201);
        restponsepojo.setMessage("The New Book has been added to the List.");

        return restponsepojo;
    }


    //Method to display all the books available
    public Restponsepojo<BookStore> getAllBooks(){
        List<BookStore> bookStoreList= bookStoreReppo.findAll();
        if(bookStoreList.isEmpty())
            throw new ApiException("Books List Empty!");

        Restponsepojo<BookStore> restponsepojo = new Restponsepojo<>();
        restponsepojo.setList(bookStoreList);
        restponsepojo.setSuccess(true);
        restponsepojo.setStatus(200);
        restponsepojo.setMessage("Books Successfully displayed!!");

        return restponsepojo;
    }

    //Method to get a book by Id
    public Restponsepojo<BookStore> getBookById(Long id){
        Optional<BookStore> bookStoreOptional = bookStoreReppo.findById(id);
        bookStoreOptional.orElseThrow(() -> new ApiException(String.format("Book with this id %s not found", id)));

        Restponsepojo<BookStore> restponsepojo = new Restponsepojo<>();
        restponsepojo.setMessage("Book result");
        restponsepojo.setData(bookStoreOptional.get());

        return restponsepojo;
    }

    //Method to return the last 10 books
    public List<BookStore> lastTenBooks(){

        return null;
    }


    //Method to search a book by Title
    public Restponsepojo<BookStore> titleSearch(String searchWord) {

        Optional<BookStore> bookStoreOptional=bookStoreReppo.findBookStoreByBookTitle(searchWord);
        bookStoreOptional.orElseThrow(()->new ApiException(String.format("The book with the name %s not found!!",
                bookStoreReppo.findBookStoreByBookTitle(searchWord))));

        BookStore theBook = bookStoreReppo.findBookStoreByBookTitle(searchWord).get();

        //Instantiating the response pojo
        Restponsepojo<BookStore> restponsepojo = new Restponsepojo<>();
        restponsepojo.setData(theBook);
        restponsepojo.setMessage("Book Found!!");
        restponsepojo.setSuccess(true);
        restponsepojo.setStatus(200);
        return restponsepojo;
    }

    //Method to search a book by Title
    public Restponsepojo<BookStore> authorSearch(String searchWord) {

        List<BookStore> bookStoreList=bookStoreReppo.findBookStoreByAuthor(searchWord);
       if( bookStoreList.isEmpty())
           throw new ApiException(String.format("Book search with the author-name %s not found!!", bookStoreReppo.findBookStoreByAuthor(searchWord)));

       //Instantiating the response pojo
       Restponsepojo<BookStore> restponsepojo = new Restponsepojo<>();
       restponsepojo.setList(bookStoreList);
       restponsepojo.setSuccess(true);
       restponsepojo.setStatus(200);
       restponsepojo.setMessage("Books by author specified found!!");

        return restponsepojo;
    }

    //Method to search a book by Genre
    public Restponsepojo<BookStore> genreSearch(String searchWord) {
        List<BookStore> bookStoreList = bookStoreReppo.findBookStoreByGenre(searchWord);
        if(bookStoreList.isEmpty())
            throw new ApiException("Book Genre Input Not Found");

        Restponsepojo<BookStore> restponsepojo = new Restponsepojo<>();
        restponsepojo.setList(bookStoreList);
        restponsepojo.setSuccess(true);
        restponsepojo.setMessage("Book search by genre found!!");


        return restponsepojo;
    }

    public Restponsepojo<BookStore> adminUpdate(BookStoreDto bookStoreDt){

        if (ObjectUtils.isEmpty(bookStoreDt.getId()))
            throw new ApiException("Id is required for update");

        Optional<BookStore> bookStoreOptional = bookStoreReppo.findById(bookStoreDt.getId());
        bookStoreOptional.orElseThrow(() -> new ApiException(String.format("Book with this id %s not found", bookStoreDt.getId() )));

            BookStore bookUpdate =bookStoreOptional.get();
            bookUpdate.setSummary(bookStoreDt.getSummary());
            bookUpdate.setNoAvailable(bookStoreDt.getNoAvailable());
            bookUpdate.setPrice(bookStoreDt.getPrice());
            bookUpdate.setStatus(true);
            //Saving the updated book
            BookStore abook =  bookStoreReppo.save(bookUpdate);

        Restponsepojo<BookStore> restponsepojo = new Restponsepojo<>();
        restponsepojo.setData(abook);
        restponsepojo.setMessage("Book was updated successfully");
        return restponsepojo;
    }


    //Method to delete a book
    public Restponsepojo<BookStore> removeBook(Long id){
        Optional<BookStore> bookStoreOptional = bookStoreReppo.findById(id);
        bookStoreOptional.orElseThrow(()->new ApiException(String.format("The book with the id %s does not exist",
                id)));

        BookStore delBook = bookStoreReppo.findById(id).get();
        //This is where the book is deleted
        //Deleting the variable
        bookStoreReppo.deleteById(id);

        //Instantiating the response pojo
        Restponsepojo<BookStore> restponsepojo = new Restponsepojo<>();
        restponsepojo.setData(delBook);
        restponsepojo.setSuccess(true);
        restponsepojo.setStatus(201);
        restponsepojo.setMessage("The book specified by id has been deleted!");



        return restponsepojo;
    }

}
