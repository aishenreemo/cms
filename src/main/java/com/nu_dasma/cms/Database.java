package com.nu_dasma.cms;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.nu_dasma.cms.model.BorrowedItem;
import com.nu_dasma.cms.model.Document;

import com.nu_dasma.cms.model.User;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;

public class Database {
    public final int ROLE_UNKNOWN = -1;
    public final int ROLE_STUDENT = 0;
    public final int ROLE_ADMINISTRATOR = 1;

    private static Database instance;

    private static final String DATABASE_URL = "jdbc:mariadb://localhost/cms";
    private static final String DATABASE_USERNAME = "cms_user";
    private static final String DATABASE_PASSWORD = "cms_user_password";

    private static final String DATABASE_UPLOAD_DIRECTORY = String.format(
        "/home/%s/cms/cms_database",
        System.getProperty("user.name")
    );

    public Connection connection;
    public User loggedInUser;
    public File uploadDir;

    private Database() {
        try {
            this.uploadDir = new File(DATABASE_UPLOAD_DIRECTORY);
            this.loggedInUser = null;
            this.connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);

            System.out.println("Database connection established successfully!");
            this.uploadDir.mkdirs();
        } catch (SQLException e) {
            System.err.println("Database connection error: " + e.getMessage());
        }
    }

    public boolean validateCredentials(String email, String password_hash) {
        try {
            String sql = String.format("SELECT * FROM users WHERE email = ? LIMIT 1;");
            PreparedStatement statement = this.connection.prepareStatement(sql);

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

            int userID = resultSet.getInt("id");

            this.loggedInUser = new User(this.connection, userID);

            return true;
        } catch (SQLException e) {
            System.err.println("Read error: " + e.getMessage());
            return false;
        }
    }

    public ArrayList<Document> getStudentDocuments(int studentID) {
        ArrayList<Document> documents = new ArrayList<>();

        try {
            String sql = String.format("SELECT * FROM document_type;");
            PreparedStatement statement = this.connection.prepareStatement(sql);
            ResultSet documentTypeSet = statement.executeQuery();
            statement.close();

            while (documentTypeSet.next()) {
                String documentName = documentTypeSet.getString("name");
                int documentType = documentTypeSet.getInt("id");
                documents.add(new Document(this.connection, documentName, documentType, studentID));
            }
        } catch (SQLException e) {
            System.err.println("Read error: " + e.getMessage());
        }

        return documents;
    }

    public ArrayList<BorrowedItem> getStudentBorrowedItems(int studentID) {
        ArrayList<BorrowedItem> items = new ArrayList<>();

        try {
            String sql = String.format(
                "SELECT * FROM borrowed_items " +
                "JOIN items ON borrowed_items.item_id = items.id " +
                "JOIN students ON borrowed_items.student_id = students.id " +
                "WHERE borrowed_items.student_id = ?;"
            );

            PreparedStatement statement = this.connection.prepareStatement(sql);
            statement.setInt(1, studentID);
            ResultSet resultSet = statement.executeQuery();
            statement.close();

            while (resultSet.next()) {
                String itemName = resultSet.getString("items.name");
                int itemID = resultSet.getInt("items.id");
                java.sql.Date dueDate = resultSet.getDate("borrowed_items.due");
                items.add(new BorrowedItem(itemID, studentID, itemName, dueDate));
            }
        } catch (SQLException e) {
            System.err.println("Read error: " + e.getMessage());
        }

        return items;
    }

    public void uploadStudentDocument(int studentID, int documentTypeID, String srcAbsolutePath) {
        try {
            if (!new File(srcAbsolutePath).isFile()) {
                throw new IOException("Invalid file path " + srcAbsolutePath);
            }

            int statusTypeID = this.getStatusType("PENDING");

            Path source = Paths.get(srcAbsolutePath);
            Path destination = Paths.get(this.getDocumentPath(studentID, documentTypeID, srcAbsolutePath));
            File destinationDir = new File(destination.getParent().toString());
            File destinationFile = new File(destination.toString());

            destinationDir.mkdirs();
            destinationFile.delete();
            Files.copy(source, destination);

            PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO documents (student_id, document_type_id, document_path, status_type_id) " +
                "VALUES (?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE document_path = VALUES(document_path), status_type_id = VALUES(status_type_id);"
            );

            statement.setInt(1, studentID);
            statement.setInt(2, documentTypeID);
            statement.setString(3, destination.toString());
            statement.setInt(4, statusTypeID);

            statement.executeUpdate();
            System.out.println("Student document uploaded successfully!");
        } catch (IOException e) {
            System.err.println("IO error: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Insertion error: " + e.getMessage());
        }
    }

    private int getStatusType(String status) throws SQLException {
        PreparedStatement statement = this.connection.prepareStatement("SELECT (id) FROM status_type WHERE name = ? LIMIT 1;");
        statement.setString(1, status);
        ResultSet resultSet = statement.executeQuery();

        if (!resultSet.next()) {
            throw new SQLException("Unknown status type");
        }

        return resultSet.getInt("id");
    }

    public String getDocumentName(int documentTypeID) throws SQLException {
        PreparedStatement statement = this.connection.prepareStatement("SELECT (name) FROM document_type WHERE id = ? LIMIT 1;");
        statement.setInt(1, documentTypeID);
        ResultSet resultSet = statement.executeQuery();

        if (!resultSet.next()) {
            throw new SQLException("Unknown document type");
        }

        return resultSet.getString("name");
    }

    private String getDocumentPath(int studentID, int documentTypeID, String srcAbsolutePath) throws SQLException {
        String fileName = new File(srcAbsolutePath).getName();
        String extension = fileName.substring(fileName.lastIndexOf("."));
        String documentName = this.getDocumentName(documentTypeID);

        return String.format(
            "%s/student-%d/%s%s",
            DATABASE_UPLOAD_DIRECTORY,
            studentID,
            documentName,
            extension
        );
    }

    public void viewStudentDocument(int studentID, int documentTypeID) throws SQLException {
        try {
            String documentName = this.getDocumentName(documentTypeID);
            Document document = new Document(this.connection, documentName, documentTypeID, studentID);

            if (document.path.isEmpty()) {
                throw new SQLException(documentName + " not found.");
            }

            URI uri = URI.create("file://" + document.path);
            String command = String.format("xdg-open %s", uri.toString());
            Runtime.getRuntime().exec(command.split(" +"));
        } catch (SQLException e) {
            System.err.println("Read error: " + e.getMessage());
            throw e;
        } catch (IllegalArgumentException e) {
            System.err.println("Error in IO: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error in IO: " + e.getMessage());
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
