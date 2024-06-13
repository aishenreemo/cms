package com.nu_dasma.cms.swing_ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.nu_dasma.cms.Database;
import com.nu_dasma.cms.SwingApp;

public class LoginFrame extends BaseFrame {
    private static LoginFrame instance;
    public static final int WIDTH = 400;
    public static final int HEIGHT = 500;

    private MessageDigest md;
    private JTextField email;
    private JPasswordField password;

    public LoginFrame() {
        super("CMS Login");

        try {
            this.md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            System.err.println("MessageDigest: " + e.getMessage());
        }

        this.setLayout(new BorderLayout());
        this.setSize(WIDTH, HEIGHT);
        this.setBackground(Palette.WHITE.getColor());

        JPanel titlePanel = new JPanel();
        JPanel panel = new JPanel();
        TextLabel loginLabel = new TextLabel("Log In", 30);
        TextLabel instruction = new TextLabel("Enter your credentials.", 15);
        this.email = new JTextField(20);
        this.password = new JPasswordField(20);
        CustomButton enter = new CustomButton("Enter", 90, 30, 10, 10);
        ImageLabel icon = new ImageLabel("NULogoClearanceSystem.png", 280, 90);
        JSeparator separator = new JSeparator();

        titlePanel.setPreferredSize(new Dimension(WIDTH, (int)(HEIGHT * 0.1)));
        titlePanel.setBackground(Palette.ROYAL_BLUE.getColor());

        panel.setSize(WIDTH, (int)(HEIGHT * 0.85));
        panel.setPreferredSize(new Dimension(WIDTH, (int)(HEIGHT * 0.9)));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        panel.setBackground(Palette.GOLDEN_YELLOW.getColor());

        separator.setOrientation(SwingConstants.HORIZONTAL);
        separator.setSize(10, 500);
        separator.setForeground(Palette.ROYAL_BLUE.getColor());
        separator.setBackground(Palette.ROYAL_BLUE.getColor());

        loginLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginLabel.setForeground(Palette.ROYAL_BLUE.getColor());

        instruction.setAlignmentX(Component.CENTER_ALIGNMENT);
        instruction.setForeground(Palette.ROYAL_BLUE.getColor());
        icon.setAlignmentX(Component.CENTER_ALIGNMENT);

        email.setMaximumSize(new Dimension(300, 40));
        email.setAlignmentX(Component.CENTER_ALIGNMENT);
        email.setBorder(new RoundedBorder(10));

        password.setMaximumSize(new Dimension(300, 40));
        password.setAlignmentX(Component.CENTER_ALIGNMENT);
        password.setBorder(new RoundedBorder(10));

        enter.setAlignmentX(Component.CENTER_ALIGNMENT);
        enter.setBackground(Palette.ROYAL_BLUE.getColor());
        enter.setForeground(Color.WHITE);

        enter.addActionListener(e -> {
            SwingApp app = SwingApp.getInstance();

            if (!this.loginUser()) {
                JOptionPane.showMessageDialog(null, "Invalid credentials; try again.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            app.ui.dispose();

            if (app.db.loggedInUser.roleName.equals("STUDENT")) {
                app.ui = StudentUIFrame.getInstance();
                app.ui.setSize(StudentUIFrame.WIDTH - 1, StudentUIFrame.HEIGHT);
                app.ui.setResizable(false);
                app.ui.revalidate();
                app.ui.repaint();
            } else if (app.db.loggedInUser.roleName.equals("ADMINISTRATOR")) {
                app.ui = AdminUIFrame.getInstance();
            }
        });

        panel.add(icon);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(separator);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(loginLabel);
        panel.add(instruction);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(email);
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

    public boolean loginUser() {
        this.md.update(String.valueOf(this.password.getPassword()).getBytes(StandardCharsets.UTF_8));
        String password_hash = Base64.getEncoder().encodeToString(md.digest());

        if (password_hash.length() > 60) {
            password_hash = password_hash.substring(0, 60);
        }

        return Database.getInstance().validateCredentials(this.email.getText(), password_hash);
    }

    @Override
    public void dispose() {
        synchronized (LoginFrame.class) {
            LoginFrame.instance = null;
            super.dispose();
        }
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

