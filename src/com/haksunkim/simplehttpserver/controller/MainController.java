package com.haksunkim.simplehttpserver.controller;

import com.haksunkim.simplehttpserver.service.FileService;

import java.io.FileNotFoundException;
import java.util.Date;

public class MainController {

    /**
     * Returns response body as String
     * @param path relative path from URL
     * @return     response body
     */
    public synchronized String get(String path) {

        StringBuffer httpResponse = new StringBuffer();
        Date today = new Date();
        String httpContent = "";
        try {
            httpContent = FileService.getContent(path);

            httpResponse.append("HTTP/1.1 200 OK\r\n");
            httpResponse.append("Date: ").append(today).append("\r\n");
            httpResponse.append("Server: SimpleHTTPServer\r\n");
            httpResponse.append("Content-Length: ");
            httpResponse.append(httpContent.getBytes().length);
            httpResponse.append("\r\n");
            httpResponse.append("Content-Type: text/html; charset=utf-8\r\n");
            httpResponse.append("Connection: Closed\r\n\r\n");
            httpResponse.append(httpContent);
        } catch (FileNotFoundException fnf) {
            if (path.equals("/favicon.ico")) {
                // ignore an exception caused by favicon
                httpResponse.append("HTTP/1.1 200 OK\r\n\r\n");
            } else {
                // return file not found page
                httpContent = "File Not Found";

                httpResponse.append("HTTP/1.1 404 Not Found\r\n");
                httpResponse.append("Date: ").append(today).append("\r\n");
                httpResponse.append("Server: SimpleHTTPServer\r\n");
                httpResponse.append("Content-Length: ");
                httpResponse.append(httpContent.getBytes().length);
                httpResponse.append("\r\n");
                httpResponse.append("Content-Type: text/html; charset=utf-8\r\n");
                httpResponse.append("Connection: Closed\r\n\r\n");
                httpResponse.append(httpContent);
            }
        }

        System.out.println(httpResponse.toString());

        return httpResponse.toString();
    }
}
