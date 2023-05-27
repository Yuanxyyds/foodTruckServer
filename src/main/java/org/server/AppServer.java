package org.server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.handler.PasswordHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class AppServer {
    public static void main(String[] args) throws IOException {
        // Create an HTTP server instance
        HttpServer server = HttpServer.create(new InetSocketAddress(54802), 0);

        // Set the context path and the handler
        server.createContext("/api", new PasswordHandler());

        // Start the server
        server.start();
        System.out.println("Server started on port 54802");
    }

}