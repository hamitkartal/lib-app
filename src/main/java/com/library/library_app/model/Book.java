package com.library.library_app.model;

import jakarta.persistence.*;


@Entity
@Table(name = "books", uniqueConstraints = @UniqueConstraint(columnNames = {"name", "publisher"}))
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "books_sequence")
    @SequenceGenerator(name = "books_sequence", sequenceName = "books_seq", allocationSize = 1)
    Long id;

    @Column(name = "name", nullable = false)
    String name;

    @Column(name = "author", nullable = false)
    String author;

    @Column(name = "page_count", nullable = false)
    int pageCount;
    @Column(name = "publisher", nullable = false)
    String publisher;

    @Column(name = "stock", nullable = false)
    int stock;

    public Book () {}

    public Book (String name, String author, int pageCount, String publisher, int stock) {
        this.name = name;
        this.author = author;
        this.pageCount = pageCount;
        this.publisher = publisher;
        this.stock = stock;
    }

    public Book (Long id, String name, String author, int pageCount, String publisher, int stock) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.pageCount = pageCount;
        this.publisher = publisher;
        this.stock = stock;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "Book[id=" + id + ", name=" + name + ", author=" + author + ", pageCount=" + pageCount + ", publisher=" + publisher + ", stock=" + stock + "]";
    }
}
