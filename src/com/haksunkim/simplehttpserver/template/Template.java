package com.haksunkim.simplehttpserver.template;

import java.io.File;
import java.io.FileNotFoundException;

public interface Template {
    String HTML_TEMPLATE = "<!DOCTYPE HTML PUBLIC \"-//IETF//DTD HTML 2.0//EN\">\n" +
            "<html>\n" +
            "\n" +
            "<head>\n" +
            "   <title>{{title}}</title>\n" +
            "</head>\n" +
            "\n" +
            "<body>\n" +
            "   <h1>{{body-title}}</h1>\n" +
            "   <div>{{body-content}}</div>\n" +
            "</body>\n" +
            "\n" +
            "</html>";
    String render(File file) throws FileNotFoundException;
}
