package org.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

public class ServerListenerThread extends Thread{
    private final static Logger LOGGER = LoggerFactory.getLogger(ServerListenerThread.class);

    private final int port;
    private final String webroot;
    private final ServerSocket serverSocket;

    public ServerListenerThread(int port, String webroot) throws IOException {
        this.port = port;
        this.webroot = webroot;
        this.serverSocket = new ServerSocket(this.port);
    }
    @Override
    public void run() {
        try {
            while (serverSocket.isBound() && !serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                LOGGER.info(" * Connection accepted: " + socket.getInetAddress());
                HttpConnectionWorkerThread workerThread = null;
                try {
                    workerThread = new HttpConnectionWorkerThread(socket);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                workerThread.start();
            }

        } catch (IOException e) {
             LOGGER.error("Problem with setting socket", e);
        } finally {
            if (serverSocket != null){
                try{
                    serverSocket.close();
                } catch (IOException e){

                }
            }
        }
    }
}
