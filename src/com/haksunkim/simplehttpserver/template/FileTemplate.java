package com.haksunkim.simplehttpserver.template;

import java.io.*;

public class FileTemplate implements Template {

    /**
     * Returns content of file as HTTP body
     * @param file Target object
     * @return     HTTP body
     */
    public String render(File file) throws FileNotFoundException {
        String bodyContent;

        //get contents of the file
        BufferedReader br = new BufferedReader(new FileReader(file));
        StringBuffer stringBuffer = new StringBuffer();
        String line;
        try {
            line = br.readLine();
            while (line != null) {
                stringBuffer.append(line);
                line = br.readLine();
            }

            br.close();

            bodyContent = stringBuffer.toString();
        } catch (IOException ioe) {
            bodyContent = "<h1>" + ioe.getMessage() + "</h>";
        }

        return HTML_TEMPLATE
                .replace("{{title}}", "Directory Listing")
                .replace("{{body-title}}", "Directory Listing")
                .replace("{{body-content}}", bodyContent);
    }
}
