package com.nu_dasma.cms;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.nu_dasma.cms.model.User;

import java.sql.Connection;

public class Database {
    public final int ROLE_UNKNOWN = -1;
    public final int ROLE_STUDENT = 0;
    public final int ROLE_ADMINISTRATOR = 1;

    private static Database instance;

    private static final String DATABASE_URL = "jdbc:mariadb://localhost/cms";
    private static final String DATABASE_USERNAME = "cms_user";
    private static final String DATABASE_PASSWORD = "cms_user_password";

    private Connection connection;

    public User loggedInUser;

    private Database() {
        try {
            this.loggedInUser = null;
            this.connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);

            System.out.println("Database connection established successfully!");
        } catch (SQLException e) {
            System.err.println("Database connection error: " + e.getMessage());
        }
    }

    public boolean validateCredentials(String email, String password_hash) {
        try {
            String sql = String.format("SELECT * FROM users WHERE email = ? LIMIT 1;");
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, email);

            ResultSet resultSet = statement.executeQuery();

            statement.close();

            if (!resultSet.next()) {
                System.err.println("Unknown email.");
                return false;
            }

            if (!resultSet.getString("password_hash").equals(password_hash)) {
                System.err.println("Incorrect password.");
                return false;
            }

            this.loggedInUser = new User(
                resultSet.getInt("id"),
                resultSet.getInt("role_type_id"),
                resultSet.getString("email"),
                resultSet.getString("first_name"),
                resultSet.getString("last_name"),
                resultSet.getString("middle_name")
            );

            return true;
        } catch (SQLException e) {
            System.err.println("Read error: " + e.getMessage());
            return false;
        }
    }

    public void dispose() {
        try {
            this.connection.close();
            System.out.println("Database connection closed successfully!");
        } catch (SQLException e) {
            System.err.println("Connection error: " + e.getMessage());
        }

        Database.instance = null;
    }

    public static Database getInstance() {
        if (Database.instance != null) {
            return Database.instance;
        }

        synchronized (Database.class) {
            Database.instance = new Database();
        }

        return Database.instance;
    }
}
