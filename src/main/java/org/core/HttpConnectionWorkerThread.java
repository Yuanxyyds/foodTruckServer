package org.core;

import org.database_connection.DatabaseConnection;
import org.http.HttpParser;
import org.http.HttpParsingException;
import org.http.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.sql.SQLException;

public class HttpConnectionWorkerThread extends Thread {
    private final static Logger LOGGER = LoggerFactory.getLogger(ServerListenerThread.class);
    private final Socket socket;

    private final DatabaseConnection databaseConnection;

    private HttpParser httpParser;

    public HttpConnectionWorkerThread(Socket socket) throws SQLException {
        this.socket = socket;
        this.httpParser = new HttpParser();

        try {
            this.databaseConnection = new DatabaseConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
            HttpRequest request = httpParser.parseHttpRequest(inputStream);
            String msg = request.getMethod() + request.getRequestTarget();
            String response = "HTTP/1.1 200 OK\r\n" +
                    "Content-Type: text/plain\r\n" +
                    "\r\n" +
                    msg;

            outputStream.write(response.getBytes());


            LOGGER.info("Connection Processing Finished.");
        } catch (IOException e) {
            LOGGER.error("Problem with communication", e);
        } catch (HttpParsingException e) {
            throw new RuntimeException(e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    // Handle or log exception
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    // Handle or log exception
                }
            }
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    // Handle or log exception
                }
            }
            LOGGER.info("Connection Closed.");
        }
    }
}
