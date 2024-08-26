package com.library.library_app.controller;


import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.CSVFormat;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.Reader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.library.library_app.model.builder.BookBuilder;
import com.library.library_app.model.Book;
import com.library.library_app.service.BookService;
import com.library.library_app.dto.ServiceResponse;
import com.library.library_app.dto.ReqBodies.BookControllerReqBodies.*;


@RestController
public class BookController {

    @Autowired
    private BookService bookService;


    @GetMapping("/books")
    public ResponseEntity<ServiceResponse> getAllBooks() {
        ServiceResponse response = bookService.getAllBooks();
        return new ResponseEntity<ServiceResponse> (response, response.getStatus());
    }


    @PostMapping("/book")
    public ResponseEntity<ServiceResponse> getBookByName(@RequestBody BookNameDTO bookNameDTO) {
        ServiceResponse response = bookService.getBookByName(bookNameDTO);
        return new ResponseEntity<ServiceResponse> (response, response.getStatus());
    }

//    @PostMapping("/book")
//    public ResponseEntity<ServiceResponse> getBookById(@RequestBody BookIdDTO bookIdDTO) {
//        ServiceResponse response = bookService.getBookById(bookIdDTO);
//        return new ResponseEntity<ServiceResponse> (response, response.getStatus());
//    }


    @PostMapping("/book/create")
    public ResponseEntity<ServiceResponse> createBook(@RequestBody Book book) {
        ServiceResponse response = bookService.createBook(book);
        return new ResponseEntity<ServiceResponse> (response, response.getStatus());
    }

    @PostMapping(value = "/bulk", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ServiceResponse> createBulk(@RequestParam("file") MultipartFile file) throws IOException {
        ServiceResponse response = bookService.createBulk(file);
        return new ResponseEntity<ServiceResponse> (response, response.getStatus());
    }
}
