package com.nu_dasma.cms.ui;

import java.util.Scanner;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import com.nu_dasma.cms.Database;

public class LoginUI implements BaseUI {
    private static LoginUI instance;

    private MessageDigest md;

    private LoginUI() {
        try {
            this.md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            System.err.println("MessageDigest: " + e.getMessage());
        }
    }

    public void help() {
        System.out.println("\033[32mOn\033[0m: CMS Login CLI");
        System.out.println("Commands:");
        System.out.println("\t\033[1mlogin\033[0m <email> <password>\tLogin with email and password.");
        System.out.println("\t\033[1mclear\033[0m\t\t\t\tClear the terminal.");
        System.out.println("\t\033[1mhelp\033[0m\t\t\t\tPrint this message.");
        System.out.println("\t\033[1mexit\033[0m\t\t\t\tExit the program.");
    }

    @Override
    public void run(Scanner scanner) {
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
            } else if (args[0].equals("clear")) {
                System.out.print("\033[H\033[2J");
                continue;
            } else if (args[0].equals("exit")) {
                Database.getInstance().dispose();
                System.err.println("Bye.");
                System.exit(0);
                break;
            }

            if (!args[0].equals("login")) {
                System.err.println("Unknown command.");
                continue;
            }

            if (args.length < 3) {
                System.err.println("Insufficient arguments.");
                continue;
            }

            if (this.loginUser(args[1], args[2])) {
                break;
            }

            System.err.println("Login failed, try again.");
        }
    }

    public boolean loginUser(String email, String password) {
        this.md.update(password.getBytes(StandardCharsets.UTF_8));
        String password_hash = Base64.getEncoder().encodeToString(md.digest());

        if (password_hash.length() > 60) {
            password_hash = password_hash.substring(0, 60);
        }

        return Database.getInstance().validateCredentials(email, password_hash);
    }

    @Override
    public void dispose() {
        LoginUI.instance = null;
    }

    public static LoginUI getInstance() {
        if (LoginUI.instance != null) {
            return LoginUI.instance;
        }

        synchronized (LoginUI.class) {
            LoginUI.instance = new LoginUI();
        }

        return LoginUI.instance;
    }
}
