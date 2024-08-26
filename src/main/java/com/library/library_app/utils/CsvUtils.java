package com.library.library_app.utils;

import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public class CsvUtils {

    public static Iterable<CSVRecord> getCsvRecordsFromMultipartFile(MultipartFile file) throws IOException {
        Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
        return CSVFormat.RFC4180.withDelimiter(',').withFirstRecordAsHeader().parse(reader);
    }

}
