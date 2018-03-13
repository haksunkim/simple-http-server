package com.haksunkim.simplehttpserver.controller;

public class MainController {

    /**
     * Returns response body as String
     * @param path relative path from URL
     * @return     response body
     */
    public synchronized String get(String path) {
        String responseBody = null;

        //TODO: implement way to check directory or file using path
        responseBody = "PATH: " + path;

        return responseBody;
    }
}
