package com.library.library_app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import java.io.IOException;

import org.apache.commons.csv.CSVRecord;

import com.library.library_app.dto.ReqBodies.BookControllerReqBodies.BookIdDTO;
import com.library.library_app.dto.ReqBodies.BookControllerReqBodies.BookNameDTO;
import com.library.library_app.dto.ServiceResponse;
import com.library.library_app.model.Book;
import com.library.library_app.model.builder.BookBuilder;
import com.library.library_app.repository.BookRepository;
import com.library.library_app.utils.CsvUtils;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service("bookService")
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    public ServiceResponse getAllBooks() {
        try {
            List<Book> allBooks = bookRepository.getAllBooks();
            return new ServiceResponse(HttpStatus.OK, "Retrieved All Books", allBooks);
        } catch (Exception e) {
            return new ServiceResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving books", e);
        }
    }

    public ServiceResponse getBookByName(BookNameDTO bookNameDTO) {
        String bookName = bookNameDTO.name();
        try {
            List<Book> books = bookRepository.getBookByName(bookName);
            return new ServiceResponse(HttpStatus.FOUND, "Books successfully retrieved", books);
        } catch (Exception e) {
            return new ServiceResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving book.", null);
        }
    }

    public ServiceResponse getBookById(BookIdDTO bookIdDTO) {
        Long bookId = bookIdDTO.id();
        try {
            Optional<Book> book = bookRepository.getBookById(bookId);
            if (book.isPresent()) {
                return new ServiceResponse(HttpStatus.FOUND, "Book successfully retrieved", book);
            }
            return new ServiceResponse(HttpStatus.NOT_FOUND, "Book not found", null);
        } catch (Exception e) {
            return new ServiceResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving book.", null);
        }
    }

    public ServiceResponse createBook(Book book) {
        try {
            bookRepository.save(book);
            return new ServiceResponse(HttpStatus.OK, "Book successfully created", book);
        }
        catch (DataIntegrityViolationException e) {
            return new ServiceResponse(HttpStatus.CONFLICT, "Book already exists", null);
        }
        catch (Exception e) {
            return new ServiceResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error creating book.", null);
        }
    }

//    public ServiceResponse createBulk(MultipartFile file) throws IOException { return null;}

    public ServiceResponse createBulk(MultipartFile file) throws IOException {
        Iterable<CSVRecord> bookCsvRecords = CsvUtils.getCsvRecordsFromMultipartFile(file);
        List<Book> bookList = new ArrayList<>();
        for (CSVRecord bookCsvRecord : bookCsvRecords) {
            Book book = new BookBuilder()
                    .withName(bookCsvRecord.get("name"))
                    .withAuthor(bookCsvRecord.get("author"))
                    .withPublisher(bookCsvRecord.get("publisher"))
                    .withStock(Integer.parseInt(bookCsvRecord.get("stock")))
                    .withPageCount(Integer.parseInt(bookCsvRecord.get("page_count")))
                    .build();
            bookList.add(book);
        }

        try {
            for (Book book: bookList) {
                this.createBook(book);
            }
            return new ServiceResponse(HttpStatus.CREATED, "Books are successfully created", null);
        }
        catch (Exception e) {
            return new ServiceResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error creating book.", null);
        }
    }
}
