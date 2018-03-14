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
        StringBuilder stringBuilder = new StringBuilder();
        File parentFile = file.getParentFile();

        // if parent file exists, present Parent link
        try {
            if (parentFile.exists()) {
                stringBuilder.append("<div class='row' style='padding-bottom:10px;'>");
                stringBuilder.append("<div class='col-sm-12'><a href='");
                stringBuilder.append(parentFile.getPath());
                stringBuilder.append("' class='btn btn-primary'>Go to parent directory</a></div>");
                stringBuilder.append("</div>");
                System.out.println(parentFile.getPath());
            }
        } catch (NullPointerException npe) {
            // in case when parent file returns null for path, do nothing
        }

        String line;
        try {
            line = br.readLine();
            while (line != null) {
                stringBuilder.append(line);
                line = br.readLine();
            }

            br.close();

            bodyContent = stringBuilder.toString();
        } catch (IOException ioe) {
            bodyContent = "<h1>" + ioe.getMessage() + "</h>";
        }

        return HTML_TEMPLATE
                .replace("{{title}}", "File Contents")
                .replace("{{body-title}}", "File Contents")
                .replace("{{body-content}}", bodyContent);
    }
}
