package com.simplaex.rivr.challenge.files;

import com.simplaex.rivr.challenge.servcie.CsvFileReader;
import com.simplaex.rivr.challenge.util.FileExtension;

public class FileReaderFactory {

    public static FileReader fromFileExtension(FileExtension extension) {
        if (extension == FileExtension.CSV) {
            return new CsvFileReader();
        }

        if (extension == FileExtension.LOG) {
            return new LogFileReader();
        }

        return null;
    }
}
