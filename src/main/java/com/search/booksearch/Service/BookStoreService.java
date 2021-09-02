package com.search.booksearch.Service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.search.booksearch.Dao.BookStoreDto;
import com.search.booksearch.Entity.BookStore;
import com.search.booksearch.Entity.QBookStore;
import com.search.booksearch.Exception.ApiException;
import com.search.booksearch.Reppo.BookStoreReppo;
import com.search.booksearch.RestResponse.Restponsepojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.NumberUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.awt.print.Book;
import java.util.Date;
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

        //To check that a book with the same id does not already exist

        QBookStore qBookStore = QBookStore.bookStore;
        BooleanBuilder predicate = new BooleanBuilder();
        predicate.and(qBookStore.bookTitle.equalsIgnoreCase(bookStoreDto.getBookTitle()));
        predicate.and(qBookStore.volume.eq(bookStoreDto.getVolume()));

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        long bookCount = jpaQueryFactory.selectFrom(qBookStore).where(predicate).fetchCount();

        if (bookCount > 0)
            throw new ApiException("A book with this title and volume already exits");

        BookStore book= new BookStore();
        //Getting the contents of the book from the Data transfer object
        book.setBookNumber(new Date().getTime());
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
        restponsepojo.setMessage("Book Successfully Created");

        return restponsepojo;
    }

    //Method to get a book by Id
    public Restponsepojo<BookStore> getBookById(Long id){
        Optional<BookStore> bookStoreOptional = bookStoreReppo.findById(id);
        bookStoreOptional.orElseThrow(() -> new ApiException(String.format("Book with this id %s not found", id)));

        Restponsepojo<BookStore> restponsepojo = new Restponsepojo<>();
        restponsepojo.setMessage("Result of Book Search by Id");
        restponsepojo.setData(bookStoreOptional.get());

        return restponsepojo;
    }


    public Restponsepojo<Page<BookStore>> searchBookStore(String author, Long bookNumber, String title, String genre, Pageable pageable){
        QBookStore qBookStore = QBookStore.bookStore;
        BooleanBuilder predicate = new BooleanBuilder();

        if (StringUtils.hasText(author))
            predicate.and(qBookStore.author.likeIgnoreCase("%"+ author + "%"));

        if (StringUtils.hasText(genre))
            predicate.and(qBookStore.genre.equalsIgnoreCase(genre));

        if (StringUtils.hasText(title))
            predicate.and(qBookStore.bookTitle.likeIgnoreCase("%" + title + "%"));

        if (!ObjectUtils.isEmpty(bookNumber))
            predicate.and(qBookStore.bookNumber.eq(bookNumber));

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        JPAQuery<BookStore> jpaQuery = jpaQueryFactory.selectFrom(qBookStore)
                .where(predicate)
                .orderBy(qBookStore.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        Page<BookStore> bookStorePage = new PageImpl<>(jpaQuery.fetch(), pageable, jpaQuery.fetchCount());

        Restponsepojo<Page<BookStore>> restponsepojo = new Restponsepojo<>();
        restponsepojo.setData(bookStorePage);
        restponsepojo.setMessage("Query result of Book Store");

        return restponsepojo;

    }

    public Restponsepojo<BookStore> adminUpdate(BookStoreDto bookStoreDt){

        if (ObjectUtils.isEmpty(bookStoreDt.getId()))
            throw new ApiException("Id is required for update");

        Optional<BookStore> bookStoreOptional = bookStoreReppo.findById(bookStoreDt.getId());
        bookStoreOptional.orElseThrow(() -> new ApiException(String.format("Book with this id %s not found", bookStoreDt.getId())));

        QBookStore qBookStore = QBookStore.bookStore;
        BooleanBuilder predicate = new BooleanBuilder();
        predicate.and(qBookStore.bookTitle.equalsIgnoreCase(bookStoreDt.getBookTitle()));
        predicate.and(qBookStore.volume.eq(bookStoreDt.getVolume()));

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        long bookCount = jpaQueryFactory.selectFrom(qBookStore).where(predicate).fetchCount();

        if (bookCount > 1)
            throw new ApiException("A book with this title and volume already exists");

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

        //This is where the book is deleted
        //Deleting the variable
        bookStoreReppo.deleteById(id);

        //Instantiating the response pojo
        Restponsepojo<BookStore> restponsepojo = new Restponsepojo<>();
        restponsepojo.setData(null);
        restponsepojo.setMessage("The book specified by id has been deleted!");
        return restponsepojo;
    }

}
