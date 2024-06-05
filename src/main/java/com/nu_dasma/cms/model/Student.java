package com.nu_dasma.cms.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Student extends User {
    private int userID;
    private int studentID;
    private int paidAmount;
    private int tuitionFee;

    public Student(Connection connection, int userID) throws SQLException {
        super(connection, userID);

        String sql = String.format("SELECT * FROM students WHERE user_id = ? LIMIT 1;");
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setInt(1, userID);

        ResultSet resultSet = statement.executeQuery();

        statement.close();

        if (!resultSet.next()) {
            throw new SQLException(String.format("Unknown user %d.\n", userID));
        }

        this.userID = resultSet.getInt("students.user_id");
        this.studentID = resultSet.getInt("students.id");
        this.paidAmount = resultSet.getInt("paid_amount");
        this.tuitionFee = resultSet.getInt("tuition_fee");
    }

    @Override
    public void printInfo() {
        System.out.println("\033[1mStudent Info\033[0m");
        System.out.println("------------");
        System.out.printf(
            "Name: %s%s, %s\n",
            this.firstName,
            this.middleName == null ? "" : " " + this.middleName,
            this.lastName
        );

        System.out.printf("User ID: %d\n", this.userID);
        System.out.printf("Student ID: %d\n", this.studentID);
        System.out.printf("Email: %s\n\n", this.email);
        System.out.println("\033[1mBalance\033[0m");
        System.out.println("-------");
        System.out.printf("Paid Amount: %d\n", this.paidAmount);
        System.out.printf("Tuition Fee: %d\n", this.tuitionFee);

        if (this.tuitionFee > this.paidAmount) {
            System.out.printf("Amount Left to Pay: %d\n\n", this.tuitionFee - this.paidAmount);
        } else {
            System.out.println("\033[32mCLEARED\033[0m\n\n");
        }
    }
}
