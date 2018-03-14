package com.haksunkim.simplehttpserver.controller;

import com.haksunkim.simplehttpserver.service.FileService;
import com.haksunkim.simplehttpserver.template.Template;

import java.io.FileNotFoundException;
import java.util.Date;

public class MainController {

    /**
     * Returns response body as String
     * @param path relative path from URL
     * @return     response body
     */
    public synchronized String get(String path) {

        StringBuilder httpResponse = new StringBuilder();
        Date today = new Date();
        String httpContent;
        String responseBody = null;
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
            responseBody = httpResponse.toString();
        } catch (FileNotFoundException fnf) {
            if (path.equals("/favicon.ico")) {
                // ignore an exception caused by favicon
                httpResponse.append("HTTP/1.1 200 OK\r\n\r\n");
            } else {
                responseBody = getError(fnf);
            }
        }
        System.out.println(responseBody);

        return responseBody;
    }

    public synchronized String getError(Exception ex) {
        StringBuilder httpResponse = new StringBuilder();
        Date today = new Date();
        String httpContent;
        String htmlTitle = null;
        String bodyTitle = null;
        String bodyContent = null;
        String responseStatus = null;

        if (ex.getClass().getTypeName().equals("java.io.FileNotFoundException")) {
            // file not found exception
            htmlTitle = "404 - Not Found";
            bodyTitle = "File Not Found";
            bodyContent = "Cannot find a file requested.</br><a href='/'>Back to main page.</a>";
            responseStatus = "HTTP/1.1 404 Not Found";
        } else if (ex.getClass().getTypeName().equals("com.haksunkim.simplehttpserver.exception.MethodNotAllowedException")) {
            htmlTitle = "405 - Not Allowed";
            bodyTitle = "Method Not Allowed";
            bodyContent = "This site only allows GET method.</br><a href='/'>Back to main page.</a>";
            responseStatus = "HTTP/1.1 405 Method Not Allowed";
        } else {
            htmlTitle = "500 - Internal Server Error";
            bodyTitle = "Internal Server Error";
            bodyContent = ex.getMessage();
            responseStatus = "HTTP/1.1 500 Internal Server Error";
        }

        httpContent = Template.HTML_TEMPLATE
                .replace("{{title}}", htmlTitle)
                .replace("{{body-title}}", bodyTitle)
                .replace("{{body-content}}", bodyContent);

        httpResponse.append(responseStatus);
        httpResponse.append("\r\n");
        httpResponse.append("Date: ").append(today).append("\r\n");
        httpResponse.append("Server: SimpleHTTPServer\r\n");
        httpResponse.append("Content-Length: ");
        httpResponse.append(httpContent.getBytes().length);
        httpResponse.append("\r\n");
        httpResponse.append("Content-Type: text/html; charset=utf-8\r\n");
        httpResponse.append("Connection: Closed\r\n\r\n");
        httpResponse.append(httpContent);

        return httpResponse.toString();
    }
}
