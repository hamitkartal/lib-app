package com.library.library_app.model;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "rented_books")
public class RentedBook {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rented_books_sequence")
    @SequenceGenerator(name = "rented_books_sequence", sequenceName = "rented_books_seq")
    Long id;

    @Column(name = "user_id")
    Long userId;

    @Column(name = "book_id")
    Long bookId;

    @Column(name = "start_date")
    LocalDateTime startDate;

    @Column(name = "due_date")
    LocalDateTime dueDate;

    public RentedBook() {}

    public RentedBook(Long userId, Long bookId, LocalDateTime startDate, LocalDateTime dueDate) {
        this.userId = userId;
        this.bookId = bookId;
        this.startDate = startDate;
        this.dueDate = dueDate;
    }

    public RentedBook(Long id, Long userId, Long bookId, LocalDateTime startDate, LocalDateTime dueDate) {
        this.id = id;
        this.userId = userId;
        this.bookId = bookId;
        this.startDate = startDate;
        this.dueDate = dueDate;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getBookId() { return bookId; }
    public void setBookId(Long bookId) { this.bookId = bookId; }

    public LocalDateTime getStartDate() { return startDate; }
    public void setStartDate(LocalDateTime startDate) { this.startDate = startDate; }

    public LocalDateTime getDueDate() { return dueDate; }
    public void setDueDate(LocalDateTime dueDate) { this.dueDate = dueDate; }

}
