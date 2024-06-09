package com.nu_dasma.cms.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
    public static final int ROLE_UNKNOWN = 0;
    public static final int ROLE_STUDENT = 1;
    public static final int ROLE_ADMINISTRATOR = 2;

    public int id;
    public int roleType;
    public String roleName;

    public String email;
    public String firstName;
    public String lastName;
    public String middleName;

    public User(int id, int roleType, String email, String firstName, String lastName, String middleName) {
        this.id = id;
        this.roleType = roleType;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
    }

    public User(Connection connection, int userID) throws SQLException {
        this.id = userID;

        String sql = String.format(
            "SELECT * FROM users " +
            "JOIN role_type ON users.role_type_id = role_type.id " +
            "WHERE users.id = ? LIMIT 1;"
        );

        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setInt(1, userID);

        ResultSet resultSet = statement.executeQuery();

        statement.close();

        if (!resultSet.next()) {
            throw new SQLException(String.format("Unknown user %d.\n", userID));
        }

        this.roleType = resultSet.getInt("users.role_type_id");
        this.roleName = resultSet.getString("role_type.name");
        this.email = resultSet.getString("users.email");
        this.firstName = resultSet.getString("users.first_name");
        this.lastName = resultSet.getString("users.last_name");
        this.middleName = resultSet.getString("users.middle_name");
    }

    public void printInfo() {
        System.out.println("\033[1mUser Info\033[0m");
        System.out.println("---------");
        System.out.printf("ID: %d\n", this.id);
        System.out.printf("Name: %s\n", this.getFullName());
        System.out.printf("Email: %s\n", this.email);
        System.out.printf("Role: %s\n", this.roleName);
    }

    public String getFullName() {
        return String.format("%s%s, %s", this.firstName, this.middleName == null ? "" : " " + this.middleName, this.lastName);
    }
}
