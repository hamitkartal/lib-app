package com.library.library_app.repository;

import com.library.library_app.model.ReturnedBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.library.library_app.model.ReturnedBook;

@Repository("returnedBookRepository")
public interface ReturnedBookRepository extends JpaRepository<ReturnedBook, Long> {

    List<ReturnedBook> getAllReturnedBooksById(Long id);

    List<ReturnedBook> getAllReturnedBooksByUserId(Long id);

    List<ReturnedBook> getReturnedBookByBookId(Long id);

    List<ReturnedBook> getAllReturnedBooksByUserIdAndBookId(Long id, Long userId);

    ReturnedBook getReturnedBookById(Long id);
}
