package com.library.library_app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import java.io.IOException;

import org.apache.commons.csv.CSVRecord;

import com.library.library_app.dto.ReqBodies.BookControllerReqBodies.BookIdDTO;
import com.library.library_app.dto.ReqBodies.BookControllerReqBodies.BookNameDTO;
import com.library.library_app.dto.FailedBookCreation;
import com.library.library_app.dto.ServiceResponse;
import com.library.library_app.model.Book;
import com.library.library_app.repository.BookRepository;
import com.library.library_app.utils.CsvUtils;
import com.library.library_app.utils.parser.CsvRecord2Book;

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

    public ServiceResponse createBulk(MultipartFile file) throws IOException {
        Iterable<CSVRecord> bookCsvRecords = CsvUtils.getCsvRecordsFromMultipartFile(file);
        List<Book> bookList = CsvRecord2Book.getBookListFromCsvRecords(bookCsvRecords);
        List<FailedBookCreation> failedBookCreationList = new ArrayList<>();
        for (Book book : bookList) {
            try {
                bookRepository.save(book);
            }
            catch (Exception e) {
                failedBookCreationList.add(new FailedBookCreation(book.getName(), book.getAuthor(), e.getMessage()));
            }
        }
        if (failedBookCreationList.isEmpty()) {
            return new ServiceResponse(HttpStatus.OK, "Book successfully created", null);
        }
        else if (failedBookCreationList.size() == bookList.size()) {
            return new ServiceResponse(HttpStatus.INTERNAL_SERVER_ERROR, "No book could be created", failedBookCreationList);
        }
        return new ServiceResponse(HttpStatus.PARTIAL_CONTENT, "Some books could not be created.", failedBookCreationList);
    }
}
