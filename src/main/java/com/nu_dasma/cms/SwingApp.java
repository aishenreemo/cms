package com.nu_dasma.cms;

import com.nu_dasma.cms.swing_ui.BaseFrame;
import com.nu_dasma.cms.swing_ui.LoginFrame;

/**
 * SwingApp
 */
public class SwingApp {
    private BaseFrame ui;
    private Database db;

    public SwingApp() {
        this.db = Database.getInstance();
        this.ui = LoginFrame.getInstance();
    }

    public static void main(String[] args) {
        new SwingApp();
    }
}
