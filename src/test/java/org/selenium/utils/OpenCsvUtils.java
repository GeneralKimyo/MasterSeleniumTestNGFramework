package org.selenium.utils;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class OpenCsvUtils {

    public static CSVReader getDataFrom(String fileName) throws FileNotFoundException {
        FileReader fileReader =  new FileReader(new File(fileName));
        CSVReader csvReader = new CSVReaderBuilder(fileReader).
                withSkipLines(1).
                build();
        return csvReader;
    }
}
