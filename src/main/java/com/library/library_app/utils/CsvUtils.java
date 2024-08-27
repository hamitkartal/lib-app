package com.library.library_app.utils;

import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import com.library.library_app.config.CsvConfig;

public class CsvUtils {

    public static Iterable<CSVRecord> getCsvRecordsFromMultipartFile(MultipartFile file) throws IOException {
        Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
        CSVFormat csvFormat = CSVFormat.RFC4180.withDelimiter(CsvConfig.delimiter);

        if (CsvConfig.hasheader) {
            return csvFormat.withFirstRecordAsHeader().parse(reader);
        }
        return csvFormat.parse(reader);
    }

}
