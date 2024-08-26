package com.library.library_app.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.library.library_app.model.RentedBook;

import java.util.List;

@Repository("rentedBookRepository")
public interface RentedBookRepository extends JpaRepository<RentedBook, Long> {

    RentedBook getRentedBookById(long id);

    @Query(value = "select new com.library.library_app.model.RentedBook(rb.id, rb.userId, rb.bookId, rb.startDate, rb.dueDate) from RentedBook rb")
    List<RentedBook> getAllRentedBooks();

    List<RentedBook> getAllRentedBooksByUserId(long userId);

    RentedBook getRentedBookByUserIdAndBookId(long userId, long bookId);

    boolean existsByUserIdAndBookId(Long userId, long bookId);
}
