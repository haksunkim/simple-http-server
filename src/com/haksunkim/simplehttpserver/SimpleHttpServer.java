package com.haksunkim.simplehttpserver;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class SimpleHttpServer {

    public static void main(String[] args) throws Exception {
        final ServerSocket server = new ServerSocket(8080);
        System.out.println("Listening for connection on port 8080 ....");
        while (true) {
            final Socket client = server.accept();
            InputStreamReader isr = new InputStreamReader(client.getInputStream());
            BufferedReader reader = new BufferedReader(isr);
            String line = reader.readLine();
            while (!line.isEmpty()) {
                System.out.println(line);
                line = reader.readLine();
            }
            reader.close();

            PrintWriter os;
            try(Socket socket = server.accept()) {
                Date today = new Date();
                String httpResponse = "HTTP/1.1 200 OK\r\n\r\n" + today;
                os = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                os.write(httpResponse);
                os.flush();
                os.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {

            }
        }
    }
}