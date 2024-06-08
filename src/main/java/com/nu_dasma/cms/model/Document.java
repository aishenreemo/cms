package com.nu_dasma.cms.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Document {
    public int type;
    public String path;

    private int studentID;
    private String name;
    private String status;

    public Document(Connection connection, String name, int type, int studentID) {
        try {
            this.type = type;
            this.studentID = studentID;
            this.name = name;

            String sql = String.format(
                "SELECT * FROM documents " +
                "JOIN status_type ON documents.status_type_id = status_type.id " +
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

            this.path = resultSet.getString("documents.document_path");
            this.status = resultSet.getString("status_type.name");
        } catch (SQLException e) {
            this.path = "";
            this.status = "";
        }
    }

    public void printInfo() {
        if (this.path.isEmpty()) {
            System.out.printf("%s: not-uploaded-yet\n", this.name);
            return;
        }

        System.out.printf(
            "%s: %s (owned by student#%d : %s)\n",
            this.name,
            this.path,
            this.studentID,
            this.status
        );
    }
}
