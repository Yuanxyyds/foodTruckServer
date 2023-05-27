package org.handler;


import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

public class PasswordHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();

        if (method.equals("GET")) {
            // Handle GET requests
            String response = "Hello, this is my API!";
            System.out.println(response);
            sendResponse(exchange, response);
        } else if (method.equals("POST")) {
            // Handle POST requests
            String requestData = exchange.getRequestBody().toString();
            String response = "Received: " + requestData;
            System.out.println(response);
            sendResponse(exchange, response);
        } else {
            // Handle other HTTP methods
            String response = "Unsupported method: " + method;
            sendResponse(exchange, response);
        }
    }

    private void sendResponse(HttpExchange exchange, String response) throws IOException {
        exchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream outputStream = exchange.getResponseBody();
        outputStream.write(response.getBytes());
        outputStream.flush();
        outputStream.close();
    }
}