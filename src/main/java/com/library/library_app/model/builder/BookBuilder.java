package com.library.library_app.model.builder;

import com.library.library_app.model.Book;

public class BookBuilder {

    private String name;
    private String author;
    private int pageCount;
    private String publisher;
    private int stock;

    public BookBuilder withName(String name) { this.name = name; return this; }
    public BookBuilder withAuthor(String author) { this.author = author; return this; }
    public BookBuilder withPageCount(int pageCount) { this.pageCount = pageCount; return this; }
    public BookBuilder withPublisher(String publisher) { this.publisher = publisher; return this; }
    public BookBuilder withStock(int stock) { this.stock = stock; return this; }

    public BookBuilder() {}

    public Book build() {
        return new Book(name, author, pageCount, publisher, stock);
    }
}
