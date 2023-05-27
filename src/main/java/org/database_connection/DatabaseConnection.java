package org.database_connection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseConnection {

    private Connection connection;

    private boolean authorized;

    final String url = "jdbc:sqlite:src/main/resources/foodTruck.db";

    public DatabaseConnection() throws SQLException {
        this.authorized = false;
        try {
            this.connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println("Error retrieving password: " + e.getMessage());
        }
    }

    public boolean logInAuthorize(String uid, String password) throws SQLException {
        String query = "SELECT password FROM UserPassword WHERE uid = " + uid;
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            String key = resultSet.getString("password");
            if (key.equals(password)) {
                System.out.println("Authorize Success: UID = " + uid);
                this.authorized = true;
                return true;
            } else {
                System.out.println("Incorrect Password: UID = " + uid);
                return false;
            }
        } else {
            System.out.println("Account not existed UID = " + uid);
            return false;
        }
    }
}