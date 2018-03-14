package com.haksunkim.simplehttpserver.template;

import java.io.File;

public class DirectoryTemplate implements Template {

    /**
     * Returns list of directories and files as HTTP body
     * @param file Target object
     * @return     HTTP body
     */
    public String render(File file) {
        // get the list of file in the directory
        File[] files = file.listFiles();

        // loop through the list of files, and compose htmlBody
        StringBuilder stringBuilder = new StringBuilder();
        for (int ii = 0; ii < files.length; ii ++) {
            stringBuilder.append("<a href='");
            stringBuilder.append(files[ii].getPath());
            stringBuilder.append("'>");
            stringBuilder.append(files[ii].getPath());
            stringBuilder.append("</a><br>");
        }

        return HTML_TEMPLATE
                .replace("{{title}}", "Directory Listing")
                .replace("{{body-title}}", "Directory Listing")
                .replace("{{body-content}}", stringBuilder.toString());
    }
}
