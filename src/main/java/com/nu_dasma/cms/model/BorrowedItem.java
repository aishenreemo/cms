package com.nu_dasma.cms.model;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

public class BorrowedItem {
    public String itemName;
    public java.util.Date dueDate;

    public int itemID;
    public int studentID;

    public BorrowedItem(int itemID, int studentID, String itemName, java.sql.Date sqlDueDate) {
        this.itemID = itemID;
        this.itemName = itemName;
        this.studentID = studentID;

        if (sqlDueDate != null) {
            this.dueDate = new java.util.Date(sqlDueDate.getTime());
        }
    }

    public void printInfo() {
        System.out.printf(
            "item#%d %s: owned by student#%d (due: %s)",
            this.itemID,
            this.itemName,
            this.studentID,
            this.dueDateString()
        );
    }

    public String dueDateString() {
        return this.dueDate == null ? "no due date" : this.dueDate.toString();
    }

    public int getPenalty() {
        if (dueDate == null) {
            return 0;
        }

        LocalDate localDueDate = this.dueDate.toInstant()
               .atZone(ZoneId.systemDefault())
               .toLocalDate();

        int days = (int) ChronoUnit.DAYS.between(localDueDate, LocalDate.now());
        int penalty = days < 0 ? 0 : days * 5;

        return penalty;
    }
}
