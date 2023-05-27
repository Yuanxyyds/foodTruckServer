package org.handler;


import org.core.ServerListenerThread;
import org.database_connection.DatabaseConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PasswordHandler {

    private final static Logger LOGGER = LoggerFactory.getLogger(ServerListenerThread.class);

    public static boolean handle(String method, Map<String, String> parameters, DatabaseConnection db) throws IOException, SQLException {
        Boolean success = false;

        if (method.equals("GET")) {
            success = db.logInAuthorize(parameters.get("uid"), parameters.get("password"));

        } else if (method.equals("POST")) {
            // TODO Handle Password Request
        } else {
            // TODO Handle other HTTP methods
        }
        return success;
    }

}