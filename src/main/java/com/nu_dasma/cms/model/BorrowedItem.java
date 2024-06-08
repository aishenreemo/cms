package com.nu_dasma.cms.model;

public class BorrowedItem {

    private int itemID;
    private int studentID;
    private String itemName;
    private java.util.Date dueDate;

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
            this.dueDate == null ? "no due date" : this.dueDate.toString()
        );
    }
}
