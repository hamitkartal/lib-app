package com.library.library_app.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "returned_books")
public class ReturnedBook {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "returned_books_sequence")
    @SequenceGenerator(name = "returned_books_sequence", sequenceName = "returned_books_seq")
    Long id;

    @Column(name = "user_id")
    Long userId;

    @Column(name = "book_id")
    Long bookId;

    @Column(name = "start_date")
    LocalDateTime startDate;

    @Column(name = "due_date")
    LocalDateTime dueDate;

    @Column(name = "returned_date")
    LocalDateTime returnedDate;

    public ReturnedBook() {}

    public ReturnedBook(Long userId, Long bookId, LocalDateTime startDate, LocalDateTime dueDate, LocalDateTime returnedDate) {
        this.userId = userId;
        this.bookId = bookId;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.returnedDate = returnedDate;
    }

    public ReturnedBook(Long id, Long userId, Long bookId, LocalDateTime startDate, LocalDateTime dueDate, LocalDateTime returnedDate) {
        this.id = id;
        this.userId = userId;
        this.bookId = bookId;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.returnedDate = returnedDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDateTime getReturnedDate() {
        return returnedDate;
    }

    public void setReturnedDate(LocalDateTime returnedDate) {
        this.returnedDate = returnedDate;
    }
}
