package com.nu_dasma.cms.swing_ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginFrame extends BaseFrame {
    private static LoginFrame instance;
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;


    public LoginFrame() {
        super("CMS Login");
        this.setLayout(new BorderLayout());
        this.setSize(WIDTH, HEIGHT);
        this.setBackground(Color.WHITE);

        JTextField username = new JTextField(100);
        JPanel titlePanel = new JPanel();
        JPanel panel = new JPanel();
        JPasswordField password = new JPasswordField(100);
        JButton enter = new JButton("Enter");

        titlePanel.setPreferredSize(new Dimension(WIDTH, (int)(HEIGHT * 0.1)));
        titlePanel.setBackground(Color.GRAY);

        panel.setSize(WIDTH, (int)(HEIGHT * 0.9));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(50, 10, 10, 10));

        username.setMaximumSize(new Dimension(300, 40));
        password.setMaximumSize(new Dimension(300, 40));
        enter.setMaximumSize(new Dimension(90, 30));
        enter.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(new TextLabel("Log In", 50));
        panel.add(username);
        panel.add(password);
        panel.add(enter);

        this.add(titlePanel, BorderLayout.NORTH);
        this.add(panel, BorderLayout.CENTER);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
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

class TextLabel extends JLabel {
    TextLabel(String text, int size){
        super(text);
        this.setFont(new Font("Arial", Font.PLAIN, size));
        this.setForeground(Color.BLACK);
        this.setAlignmentX(Component.CENTER_ALIGNMENT);
    }
}
