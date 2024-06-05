package com.nu_dasma.cms.swing_ui;

import javax.swing.JFrame;

public class BaseFrame extends JFrame {
    public BaseFrame(String title) {
        super();
        this.setTitle(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
    }
}

