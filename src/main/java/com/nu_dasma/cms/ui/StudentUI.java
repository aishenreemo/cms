package com.nu_dasma.cms.ui;

import java.util.Scanner;

import com.nu_dasma.cms.model.User;
import com.nu_dasma.cms.Database;

public class StudentUI implements BaseUI {
    private static StudentUI instance;

    private User user;

    private StudentUI() {
        Database db = Database.getInstance();
        this.user = db.loggedInUser;
    }

    @Override
    public void help() {
        System.out.println("\033[32mOn\033[0m: CMS Student CLI");
        System.out.println("Commands:");
        System.out.println("\t\033[1mlogout\033[0m\t\tLogout.");
        System.out.println("\t\033[1minfo\033[0m\t\tPrint info of the logged in user.");
        System.out.println("\t\033[1mclear\033[0m\t\tClear the terminal.");
        System.out.println("\t\033[1mhelp\033[0m\t\tPrint this message.");
        System.out.println("\t\033[1mexit\033[0m\t\tExit the program.");
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
                continue;
            } else if (args[0].equals("info")) {
                this.user.printInfo();
                continue;
            } else if (args[0].equals("clear")) {
                System.out.print("\033[H\033[2J");
                continue;
            } else if (args[0].equals("exit")) {
                Database.getInstance().dispose();
                System.err.println("Bye.");
                System.exit(0);
                break;
            } else if (args[0].equals("logout")) {
                break;
            }

            System.err.println("Unknown command.");
        }
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
