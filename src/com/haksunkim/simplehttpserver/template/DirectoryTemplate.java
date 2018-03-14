package com.haksunkim.simplehttpserver.template;

import java.io.File;
import java.util.Date;

public class DirectoryTemplate implements Template {

    /**
     * Returns list of directories and files as HTTP body
     * @param file Target object
     * @return     HTTP body
     */
    public String render(File file) {
        // get the list of file in the directory
        File[] files = file.listFiles();
        File parentFile = file.getParentFile();

        // loop through the list of files, and compose htmlBody
        StringBuilder stringBuilder = new StringBuilder();
        try {
            for (File childFile : files) {
                stringBuilder.append("<div class='row'>");
                stringBuilder.append("<div class='col-sm-2'>");
                stringBuilder.append(childFile.isDirectory() ? "d" : "-");
                stringBuilder.append(childFile.canRead() ? "r" : "-");
                stringBuilder.append(childFile.canWrite() ? "w" : "-");
                stringBuilder.append(childFile.canExecute() ? "x" : "-");
                stringBuilder.append("</div>");
                stringBuilder.append("<div class='col-sm-6'><a href='");
                stringBuilder.append(childFile.getPath());
                stringBuilder.append("'>");
                stringBuilder.append(childFile.getPath());
                stringBuilder.append("</a></div>");
                stringBuilder.append("<div class='col-sm-4'>");
                stringBuilder.append(new Date(childFile.lastModified()));
                stringBuilder.append("</div>");
                stringBuilder.append("</div>");
            }
        } catch (NullPointerException npe) {
            // there is no file from listFiles
            stringBuilder.append("<h2>Directory is empty</h2>");
        }

        return HTML_TEMPLATE
                .replace("{{title}}", "Directory Listing")
                .replace("{{body-title}}", "Directory Listing")
                .replace("{{body-content}}", stringBuilder.toString());
    }
}
