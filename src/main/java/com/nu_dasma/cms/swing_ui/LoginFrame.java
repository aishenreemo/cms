package com.nu_dasma.cms.swing_ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginFrame extends BaseFrame {
    private static LoginFrame instance;
    public static final int WIDTH = 400;
    public static final int HEIGHT = 600;


    public LoginFrame() {
        super("CMS Login");
        this.setLayout(new BorderLayout());
        this.setSize(WIDTH, HEIGHT);
        this.setBackground(Color.WHITE);

        JPanel titlePanel = new JPanel();
        JPanel panel = new JPanel();
        TextLabel loginLabel = new TextLabel("Log In", 50);
        JTextField username = new JTextField(100);
        JPasswordField password = new JPasswordField(100);
        CustomButton enter = new CustomButton("Enter", 90, 30, 10, 10);

        titlePanel.setPreferredSize(new Dimension(WIDTH, (int)(HEIGHT * 0.1)));
        titlePanel.setBackground(Color.GRAY);

        panel.setSize(WIDTH, (int)(HEIGHT * 0.9));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(50, 10, 10, 10));
        panel.setBackground(Color.WHITE);

        loginLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        username.setMaximumSize(new Dimension(300, 40));
        username.setAlignmentX(Component.CENTER_ALIGNMENT);
        username.setBorder(new RoundedBorder(20));

        password.setMaximumSize(new Dimension(300, 40));
        password.setAlignmentX(Component.CENTER_ALIGNMENT);
        password.setBorder(new RoundedBorder(20));

        enter.setBorder(new RoundedBorder(10));
        enter.setAlignmentX(Component.CENTER_ALIGNMENT);
        enter.setBackground(Color.GRAY);
        enter.setForeground(Color.WHITE);

        panel.add(loginLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(username);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(password);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(enter);

        this.add(titlePanel, BorderLayout.NORTH);
        this.add(panel, BorderLayout.CENTER);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
    }

    @Override
    public void dispose() {
        super.dispose();
        LoginFrame.instance = null;
    }

    public static LoginFrame getInstance() {
        if (LoginFrame.instance != null) {
            return LoginFrame.instance;
        }

        synchronized (LoginFrame.class) {
            LoginFrame.instance = new LoginFrame();
        }

        return LoginFrame.instance;
    }
}

