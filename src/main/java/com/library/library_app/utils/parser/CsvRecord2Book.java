package com.library.library_app.utils.parser;

import com.library.library_app.model.Book;
import com.library.library_app.model.builder.BookBuilder;
import org.apache.commons.csv.CSVRecord;

import java.util.ArrayList;
import java.util.List;

public class CsvRecord2Book {

    public static List<Book> getBookListFromCsvRecords(Iterable<CSVRecord> bookCsvRecords) {
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
        return bookList;
    }
}
