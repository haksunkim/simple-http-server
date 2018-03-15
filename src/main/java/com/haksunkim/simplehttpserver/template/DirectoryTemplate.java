package com.haksunkim.simplehttpserver.template;

import java.io.File;
import java.text.DecimalFormat;
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
        String cwd = new File("").getAbsolutePath();

        StringBuilder stringBuilder = new StringBuilder();
        // if parent file exists, present Parent link
        try {
            if (parentFile.exists() && !cwd.equals(file.getAbsolutePath())) {
                String returnPath = parentFile.getPath().replaceFirst(cwd,"");

                stringBuilder.append("<div class='row' style='padding-bottom:10px;'>");
                stringBuilder.append("<div class='col-sm-12'><a href='");
                stringBuilder.append((returnPath.equals("")) ? "/" : returnPath);
                stringBuilder.append("' class='btn btn-primary'>Go to parent directory</a></div>");
                stringBuilder.append("</div>");
            }
        } catch (NullPointerException npe) {
            // in case when parent file returns null for path, do nothing
        }

        // loop through the list of files, and compose htmlBody
        try {
            for (File childFile : files) {
                stringBuilder.append("<div class='row'>");
                stringBuilder.append("<div class='col-sm-1'><span class='badge badge-success'>");
                stringBuilder.append(childFile.isDirectory() ? "d" : "-");
                stringBuilder.append(childFile.canRead() ? "r" : "-");
                stringBuilder.append(childFile.canWrite() ? "w" : "-");
                stringBuilder.append(childFile.canExecute() ? "x" : "-");
                stringBuilder.append("</span></div>");
                stringBuilder.append("<div class='col-sm-5'><a href='");
                stringBuilder.append(childFile.getPath().replaceFirst(cwd,""));
                stringBuilder.append("' class='badge ");
                stringBuilder.append(childFile.isDirectory() ? "badge-primary'>" : "badge-secondary'>");
                stringBuilder.append(childFile.getPath().replaceFirst(cwd,""));
                stringBuilder.append("</a></div>");
                stringBuilder.append("<div class='col-sm-4'>");
                stringBuilder.append(new Date(childFile.lastModified()));
                stringBuilder.append("</div>");
                if (childFile.isFile()) {
                    DecimalFormat formatter = new DecimalFormat("#,###");
                    stringBuilder.append("<div class='col-sm-2'>");
                    stringBuilder.append(String.valueOf(formatter.format(childFile.length())));
                    stringBuilder.append(" bytes</div>");
                } else {
                    stringBuilder.append("<div class='col-sm-2'>&nbsp;</div>");
                }
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
