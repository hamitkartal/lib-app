package com.library.library_app.repository;


import java.util.List;
import java.util.Optional;
import java.util.concurrent.Flow;

import com.library.library_app.model.Book;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository("bookRepository")
public interface BookRepository extends JpaRepository<Book, Long> {

    boolean existsById(Long id);

    boolean existsByNameAndPublisher(String bookName, String publisher);

    @Query("select (0 < b.stock) from Book b where b.id = :id")
    boolean existsInStockById(@Param("id") Long id);

    @Query(value = "select new com.library.library_app.model.Book(b.id, b.name, b.author, b.pageCount, b.publisher, b.stock) from Book b")
    List<Book> getAllBooks();

    @Query(value = "select new com.library.library_app.model.Book(b.id, b.name, b.author, b.pageCount, b.publisher, b.stock) from Book b where b.name = :name")
    List<Book> getBookByName(@Param("name") String bookName);

    @Query(value = "select new com.library.library_app.model.Book(b.id, b.name, b.author, b.pageCount, b.publisher, b.stock) from Book b where b.id = :id")
    Optional<Book> getBookById(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query("update Book b set b.stock = b.stock - 1 where b.id = :bookId")
    void decreaseBookStockByOneByBookId(@Param("bookId") Long bookId);

    @Modifying
    @Transactional
    @Query("update Book b set b.stock = b.stock + 1 where b.id = :bookId")
    void increaseBookStockByOneByBookId(@Param("bookId") Long bookId);
}
