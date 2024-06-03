package com.nu_dasma.cms;

import java.util.Scanner;

import com.nu_dasma.cms.model.User;
import com.nu_dasma.cms.ui.BaseUI;
import com.nu_dasma.cms.ui.LoginUI;
import com.nu_dasma.cms.ui.StudentUI;
import com.nu_dasma.cms.ui.AdminUI;

public class App {
    private static App instance;

    private Database db;
    private BaseUI ui;

    private Scanner scanner;

    private App() {
        this.scanner = new Scanner(System.in);
        this.db = Database.getInstance();

        while (true) {
            this.ui = LoginUI.getInstance();
            this.ui.run(this.scanner);

            if (this.db.loggedInUser.roleType == User.ROLE_STUDENT) {
                this.ui.dispose();
                this.ui = StudentUI.getInstance();
                this.ui.run(this.scanner);
            } else if (this.db.loggedInUser.roleType == User.ROLE_ADMINISTRATOR) {
                this.ui.dispose();
                this.ui = AdminUI.getInstance();
                this.ui.run(this.scanner);
            }

            this.ui.dispose();
        }
    }

    public static App getInstance() {
        if (App.instance != null) {
            return App.instance;
        }

        synchronized (App.class) {
            App.instance = new App();
        }

        return App.instance;
    }

    public static void main(String[] args) {
        App.getInstance();
    }
}
