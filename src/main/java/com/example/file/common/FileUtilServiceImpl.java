package com.example.file.common;

import org.springframework.scheduling.annotation.Async;

import java.io.File;

public class FileUtilServiceImpl implements FileUtilService {

    @Async
    @Override
    public void delFile(String fileName) {
        File file = new File(fileName);
        if (file.exists()) {
            file.delete();
        }
    }
}
