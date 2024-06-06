package com.nu_dasma.cms;

import com.nu_dasma.cms.swing_ui.BaseFrame;
import com.nu_dasma.cms.swing_ui.LoginFrame;

public class SwingApp {
    private static SwingApp instance;

    public BaseFrame ui;
    public Database db;

    private SwingApp() {
        this.db = Database.getInstance();
        this.ui = LoginFrame.getInstance();
    }

    public static void main(String[] args) {
        SwingApp.getInstance();
    }

    public static SwingApp getInstance() {
        if (SwingApp.instance != null) {
            return SwingApp.instance;
        }

        synchronized (SwingApp.class) {
            SwingApp.instance = new SwingApp();
        }

        return SwingApp.instance;
    }
}
