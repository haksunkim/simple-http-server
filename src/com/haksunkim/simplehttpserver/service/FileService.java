package com.haksunkim.simplehttpserver.service;

import com.haksunkim.simplehttpserver.template.DirectoryTemplate;
import com.haksunkim.simplehttpserver.template.FileTemplate;
import com.haksunkim.simplehttpserver.template.Template;

import java.io.File;
import java.io.FileNotFoundException;

public class FileService {

    /**
     * Returns content of file if the path is pointing a file,
     * or returns list of files in a directory if the path is pointing a directory
     * @param path path to the file or directory
     * @return     HTML content as String
     */
    public static String getContent(String path) throws FileNotFoundException {
        File file = new File(path);

        if (!file.exists()) throw new FileNotFoundException("File not found.");
        Boolean isDirectory = file.isDirectory();

        Template httpTemplate;

        if (isDirectory) {
            // the path is representing a directory
            httpTemplate = new DirectoryTemplate();
        } else {
            // the path is representing a file
            httpTemplate = new FileTemplate();
        }

        return httpTemplate.render(file);
    }
}
