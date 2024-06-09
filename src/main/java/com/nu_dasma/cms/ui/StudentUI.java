package com.nu_dasma.cms.ui;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import com.nu_dasma.cms.model.BorrowedItem;
import com.nu_dasma.cms.model.Document;
import com.nu_dasma.cms.model.Student;
import com.nu_dasma.cms.Database;

public class StudentUI implements BaseUI {
    private static StudentUI instance;

    private Database db;
    private Student user;

    private StudentUI() {
        try {
            this.db = Database.getInstance();
            this.user = new Student(this.db.connection, this.db.loggedInUser.id);
        } catch (SQLException e) {
            System.err.println("Read error: " + e.getMessage());
        }
    }

    @Override
    public void help() {
        System.out.println("\033[32mOn\033[0m: CMS Student CLI");
        System.out.println("Commands:");
        System.out.println("\t\033[1mlogout\033[0m\t\t\t\t\t\tLogout.");
        System.out.println("\t\033[1mupload-document <document_type> <absolute_path>\033[0m\tUpload document with absolute path.");
        System.out.println("\t\033[1mview-document <document_type>\033[0m\t\t\tView document.");
        System.out.println("\t\033[1mget-borrowed-items\033[0m\t\t\t\tFetch borrowed items.");
        System.out.println("\t\033[1mget-documents\033[0m\t\t\t\t\tFetch documents.");
        System.out.println("\t\033[1mget-balance\033[0m\t\t\t\t\tFetch balance.");
        System.out.println("\t\033[1minfo\033[0m\t\t\t\t\t\tPrint info of the logged in user.");
        System.out.println("\t\033[1mclear\033[0m\t\t\t\t\t\tClear the terminal.");
        System.out.println("\t\033[1mhelp\033[0m\t\t\t\t\t\tPrint this message.");
        System.out.println("\t\033[1mexit\033[0m\t\t\t\t\t\tExit the program.");
    }

    @Override
    public void run(Scanner scanner) {
        System.out.println("Logged in as \033[32mStudent\033[0m.");
        this.help();

        while (true) {
            System.out.print("> ");
            String command = scanner.nextLine();
            String args[] = command.split(" +");

            if (command.isEmpty()) {
                continue;
            }

            if (args[0].equals("help")) {
                this.help();
            } else if (args[0].equals("upload-document")) {
                this.uploadDocument(args);
            } else if (args[0].equals("view-document")) {
                this.viewDocument(args);
            } else if (args[0].equals("get-borrowed-items")) {
                this.getBorrowedItems();
            } else if (args[0].equals("get-documents")) {
                this.getDocuments();
            } else if (args[0].equals("get-balance")) {
                this.user.printBalanceInfo();
            } else if (args[0].equals("info")) {
                this.user.printInfo();
            } else if (args[0].equals("clear")) {
                System.out.print("\033[H\033[2J");
            } else if (args[0].equals("exit")) {
                this.db.dispose();
                System.err.println("Bye.");
                System.exit(0);
                break;
            } else if (args[0].equals("logout")) {
                break;
            } else if (args[0].equals("#")) {
                continue;
            } else {
                System.err.println("Unknown command.");
            }
        }
    }

    public void uploadDocument(String[] args) {
        try {
            if (args.length < 3) {
                System.err.println("Insufficient arguments.");
                return;
            }

            this.db.uploadStudentDocument(this.user.studentID, Integer.parseInt(args[1]), args[2]);
        } catch (NumberFormatException e) {
            System.out.println("Invalid argument: " + e.getMessage());
        }
    }

    public void viewDocument(String[] args) {
        try {
            if (args.length < 2) {
                System.err.println("Insufficient arguments.");
                return;
            }

            this.db.viewStudentDocument(this.user.studentID, Integer.parseInt(args[1]));
        } catch (SQLException | NumberFormatException e) {
            System.out.println("Invalid argument: " + e.getMessage());
        }
    }

    public void getBorrowedItems() {
        ArrayList<BorrowedItem> items = this.db.getStudentBorrowedItems(this.user.studentID);
        if (items.size() == 0) {
            System.out.println("No borrowed items.");
            return;
        }

        System.out.println("Borrowed Items");
        System.out.println("--------------");
        for (BorrowedItem item : items) {
            item.printInfo();
        }
        System.out.println();
    }

    public void getDocuments() {
        ArrayList<Document> documents = this.db.getStudentDocuments(this.user.studentID);
        System.out.println("Documents");
        System.out.println("--------------");
        for (Document document : documents) {
            document.printInfo();
        }
        System.out.println();
    }

    @Override
    public void dispose() {
        synchronized (StudentUI.class) {
            StudentUI.instance = null;
        }
    }

    public static StudentUI getInstance() {
        if (StudentUI.instance != null) {
            return StudentUI.instance;
        }

        synchronized (StudentUI.class) {
            StudentUI.instance = new StudentUI();
        }

        return StudentUI.instance;
    }
}
