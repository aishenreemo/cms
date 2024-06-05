package com.nu_dasma.cms.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Document {
    public int type;

    private int studentID;
    private String name;
    private String path;

    public Document(Connection connection, String name, int type, int studentID) {
        try {
            this.type = type;
            this.name = name;
            this.studentID = studentID;

            String sql = String.format(
                "SELECT * FROM documents " +
                "JOIN document_type ON documents.document_type_id = document_type.id " +
                "WHERE documents.document_type_id = ? AND documents.student_id = ? LIMIT 1;"
            );

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, type);
            statement.setInt(2, studentID);

            ResultSet resultSet = statement.executeQuery();

            statement.close();

            if (!resultSet.next()) {
                throw new SQLException(String.format("No document found.\n"));
            }

            this.path = resultSet.getString("documents.path");
        } catch (SQLException e) {
            this.path = "";
        }
    }

    public void printInfo() {
        System.out.printf("%s: %s (owned by student#%d)", this.name, this.path, this.studentID);
    }
}
