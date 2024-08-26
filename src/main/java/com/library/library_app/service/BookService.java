package com.library.library_app.service;


import com.library.library_app.model.Book;
import com.library.library_app.dto.ServiceResponse;
import com.library.library_app.dto.ReqBodies.BookControllerReqBodies.*;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public interface BookService {

    ServiceResponse getAllBooks();

    ServiceResponse getBookByName(BookNameDTO bookNameDTO);

    ServiceResponse getBookById(BookIdDTO bookIdDTO);

    ServiceResponse createBook(Book book);

    ServiceResponse createBulk(MultipartFile file) throws IOException;
}
