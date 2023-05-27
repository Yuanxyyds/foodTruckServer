package org.handler;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PasswordHandler {

    public static List<String> handle(String method, String path) throws IOException {
        List<String> stringList = new ArrayList<>();
        String response = "";
        String LoggerMessage = "";

        if (method.equals("GET")) {
            // Handle GET requests
            // TODO Handle Password Request
            response = "API" + path;
            LoggerMessage = "Received GET message";
        } else if (method.equals("POST")) {
            // Handle POST requests
            // TODO Handle Password Request
            response = "API" + path;
            LoggerMessage = "Received POST message";
        } else {
            // Handle other HTTP methods
            response = "Unsupported method: " + method;
            LoggerMessage = "Unsupported method: " + method;
        }
        stringList.add(response);
        stringList.add(LoggerMessage);

        return stringList;
    }

}