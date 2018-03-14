package com.haksunkim.simplehttpserver;

import com.haksunkim.simplehttpserver.controller.MainController;
import com.haksunkim.simplehttpserver.exception.MethodNotAllowedException;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleHttpServer {

    public static void main(String[] args) throws Exception {
        int port = 8080;

        // gets port number from arguments if defined
        if (args.length > 0) port = Integer.valueOf(args[0]);

        final ServerSocket server = new ServerSocket(port);
        System.out.println("Listening for connection on port 8080 ....");

        // Controller
        MainController controller = new MainController();

        while (true) {
            final Socket client = server.accept();
            InputStreamReader isr = new InputStreamReader(client.getInputStream());
            BufferedReader requestReader = new BufferedReader(isr);

            String requestLine = requestReader.readLine();

            try {
                // first request line has method, path and http version info
                // extract method and path information
                String[] requestElements = requestLine.split(" ");
                String requestMethod = requestElements[0];
                String requestPath = requestElements[1];

                // read request lines and compose request String
                StringBuilder stringBuilder = new StringBuilder();
                while (!requestLine.isEmpty()) {
                    stringBuilder.append(requestLine);
                    stringBuilder.append("\r\n");
                    requestLine = requestReader.readLine();
                }
                System.out.println(stringBuilder.toString());

                requestReader.close();
                isr.close();

                String httpResponse;

                // check if request method is GET, and run get method from controller to get response body
                if (requestMethod.toLowerCase().equals("get")) {
                    httpResponse = controller.get(requestPath);
                } else {
                    //only allows GET method
                    httpResponse = controller.getError(new MethodNotAllowedException());
                }

                PrintWriter os;
                try(Socket socket = server.accept()) {
                    os = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                    os.write(httpResponse);
                    os.flush();
                    os.close();
                } catch (Exception ex) {
                    // cannot write to response, dump stack trace to console
                    ex.printStackTrace();
                }
            } catch (NullPointerException npe) {
                // request being empty or cannot be parsed, do nothing
            }
        }
    }
}