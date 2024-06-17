package com.nu_dasma.cms.swing_ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import com.nu_dasma.cms.SwingApp;
import com.nu_dasma.cms.model.Student;


public class ClearanceFrame extends BaseFrame {
    private static ClearanceFrame instance;
    public static final int WIDTH = 500;
    public static final int HEIGHT = 500;
    public Student student;

    public ClearanceFrame(Student student) {
        super("CMS Login");

        this.student = student;
        this.setLayout(new BorderLayout());
        this.setSize(WIDTH, HEIGHT);
        this.setBackground(Palette.WHITE.getColor());

        JPanel titlePanel = new JPanel();
        JPanel panel = new JPanel();

        titlePanel.setPreferredSize(new Dimension(WIDTH, (int)(HEIGHT * 0.1)));
        titlePanel.setBackground(Palette.ROYAL_BLUE.getColor());
        titlePanel.setLayout(null);

        panel.setSize(WIDTH, (int)(HEIGHT * 0.85));
        panel.setPreferredSize(new Dimension(WIDTH, (int)(HEIGHT * 0.9)));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        panel.setBackground(Palette.GOLDEN_YELLOW.getColor());

        CustomButton back = new CustomButton("Back", 50, 30, 10, 10);
        back.setBounds(WIDTH - 65, 10, 50, 30);
        back.setBackground(Palette.WHITE.getColor());
        back.setForeground(Palette.ROYAL_BLUE.getColor());

        back.addActionListener(e -> {
            SwingApp app = SwingApp.getInstance();
            app.ui.dispose();
            app.ui = StudentUIFrame.getInstance();
        });

        titlePanel.add(back);

        ImageLabel icon = new ImageLabel("NULogoDasma.png", 280, 90);
        icon.setAlignmentX(Component.CENTER_ALIGNMENT);

        JSeparator separator1 = new JSeparator();
        separator1.setOrientation(SwingConstants.HORIZONTAL);
        separator1.setSize(1, 500);
        separator1.setForeground(Palette.ROYAL_BLUE.getColor());
        separator1.setBackground(Palette.ROYAL_BLUE.getColor());

        TextLabel clearanceLabel = new TextLabel("STUDENT CLEARANCE", 25);
        clearanceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        clearanceLabel.setFont(new Font("Arial", Font.BOLD, 25));
        clearanceLabel.setForeground(Palette.ROYAL_BLUE.getColor());

        JSeparator separator2 = new JSeparator();
        separator2.setOrientation(SwingConstants.HORIZONTAL);
        separator2.setSize(1, 500);
        separator2.setForeground(Palette.ROYAL_BLUE.getColor());
        separator2.setBackground(Palette.ROYAL_BLUE.getColor());

        JTextArea textArea = new JTextArea(1, 5); 
        textArea.setText(String.format("This is to certify that %s, a student of NATIONAL UNIVERSITY, has fulfilled all financial obligations, submitted all required documents, and returned all borrowed equipment and school property.", this.student.getFullName()));
        textArea.setEditable(false); 
        textArea.setLineWrap(true);
        textArea.setBackground(null);
        textArea.setForeground(Palette.ROYAL_BLUE.getColor());
        textArea.setFont(new Font("Sans Serif", Font.PLAIN, 15));       
        textArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        textArea.setHighlighter(null);
        textArea.setFocusable(false);

        panel.add(icon);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(separator1);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(clearanceLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(textArea);
        panel.add(separator2);
        panel.add(Box.createRigidArea(new Dimension(0, 50)));


        this.add(titlePanel, BorderLayout.NORTH);
        this.add(panel, BorderLayout.CENTER);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
    }

    @Override
    public void dispose() {
        synchronized (ClearanceFrame.class) {
            ClearanceFrame.instance = null;
            super.dispose();
        }
    }

    public static ClearanceFrame getInstance(Student student) {
        if (ClearanceFrame.instance != null) {
            return ClearanceFrame.instance;
        }

        synchronized (ClearanceFrame.class) {
            ClearanceFrame.instance = new ClearanceFrame(student);
        }

        return ClearanceFrame.instance;
    }
}

