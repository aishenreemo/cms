package com.nu_dasma.cms.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.nu_dasma.cms.Database;

public class Student extends User {
    public int studentID;
    public int userID;
    public int paidAmount;
    public int tuitionFee;

    private static final double MULTIPLIER_BORROWED_ITEMS = 1.0;
    private static final double MULTIPLIER_TUITION_FEE = 2.0;
    private static final double MULTIPLIER_DOCUMENTS = 2.0;
    private static final double MULTIPLIER_TOTAL = MULTIPLIER_BORROWED_ITEMS +
        MULTIPLIER_TUITION_FEE +
        MULTIPLIER_DOCUMENTS;

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
    }

    public void printBalanceInfo() {
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

    public double getProgressPercentage() {
        Database db = Database.getInstance();

        ArrayList<Document> documents = db.getStudentDocuments(studentID);
        ArrayList<BorrowedItem> items = db.getStudentBorrowedItems(studentID);

        double documentsAmount = 0.0;
        for (Document document : documents) {
            if (document.status.equals("APPROVED")) {
                documentsAmount += 1.0;
            }
        }

        documentsAmount /= documents.size();
        documentsAmount *= MULTIPLIER_DOCUMENTS;

        double borrowedItemsAmount = 0.0;
        if (items.isEmpty()) {
            borrowedItemsAmount = 1.0;
        }

        borrowedItemsAmount *= MULTIPLIER_BORROWED_ITEMS;

        double tuitionFeeAmount = 1.0;
        if (this.paidAmount < this.tuitionFee) {
            tuitionFeeAmount = (double) this.paidAmount / (double) this.tuitionFee;
        }

        tuitionFeeAmount *= MULTIPLIER_TUITION_FEE;

        return (documentsAmount + borrowedItemsAmount + tuitionFeeAmount) / MULTIPLIER_TOTAL;
    }
}
