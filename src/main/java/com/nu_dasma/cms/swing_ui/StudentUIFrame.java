package com.nu_dasma.cms.swing_ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class StudentUIFrame extends BaseFrame {
    private static StudentUIFrame instance;
    public static final int WIDTH = 400;
    public static final int HEIGHT = 600;
    public static final int COLUMNS = 3;
    public static final int ROWS = 5;
    String[] labelHeaders = { "Course:", "ID Number: ", "Department: ", "Email:" };
    String[] requirements = { "Grade 12 Report Card", "Good Moral Certificate", "PSA Birth Certificate",
            "2x2 Colored Picture", "Form 137" };

    public StudentUIFrame() {
        super("CMS Student Home");
        this.setLayout(new BorderLayout());
        this.setSize(WIDTH, HEIGHT);
        this.setBackground(Color.WHITE);

        JPanel titlePanel = new JPanel();
        JPanel mainPanel = new JPanel();
        JPanel studentInfo = new JPanel();
        JPanel matriculation = new JPanel();
        JPanel financial = new JPanel();
        // JPanel miscellanous = new JPanel();
        CustomButton[] submitButtons = new CustomButton[requirements.length];

        titlePanel.setPreferredSize(new Dimension(WIDTH, (int) (HEIGHT * 0.1)));
        titlePanel.setBackground(Color.GRAY);

        mainPanel.setPreferredSize(new Dimension(WIDTH - 30, (int) (HEIGHT * 3)));
        mainPanel.setMinimumSize(new Dimension(WIDTH - 30, (int) (HEIGHT * 3)));
        mainPanel.setLayout(new GridLayout(5, 1, 5, 5));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setBackground(Color.WHITE);

        studentInfo.setLayout(new BoxLayout(studentInfo, BoxLayout.Y_AXIS));
        studentInfo.setBackground(Color.WHITE);
        studentInfo.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        studentInfo.setBorder(new RoundedBorder(10));

        studentInfo.add(new TextLabel("SAMPLE NAME", 30));
        for (String label : labelHeaders) {
            TextLabel header = new TextLabel(label, 20);
            studentInfo.add(header);
        }

        matriculation.setLayout(new GridLayout(6, 2, 20, 20));
        matriculation.setBackground(Color.WHITE);
        matriculation.setBorder(new RoundedBorder(10));
        matriculation.add(new TextLabel("Requirements", 15));
        matriculation.add(Box.createRigidArea(new Dimension(0, 20)));

        for (int i = 0; i < requirements.length; i++) {
            TextLabel rq = new TextLabel(requirements[i], 15);
            rq.setFont(new Font("Arial", Font.BOLD, 10));
            matriculation.add(rq);

            submitButtons[i] = new CustomButton("submit", 20, 15, 10, 10);
            submitButtons[i].setForeground(Color.WHITE);
            matriculation.add(submitButtons[i]);
        }

        financial.setBackground(Color.WHITE);
        financial.setBorder(new RoundedBorder(10));
        financial.setLayout(new BoxLayout(financial, BoxLayout.Y_AXIS));
        financial.add(new TextLabel("Student Ledger", 20));
        financial.add(new TextLabel("Remaining Balance:", 10));
        financial.add(new TextLabel("CLEARED", 40));

        mainPanel.add(studentInfo);
        mainPanel.add(matriculation);
        mainPanel.add(financial);

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        this.add(titlePanel, BorderLayout.NORTH);
        this.add(scrollPane, BorderLayout.CENTER);

        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
    }

    @Override
    public void dispose() {
        synchronized (StudentUIFrame.class) {
            StudentUIFrame.instance = null;
            super.dispose();
        }
    }

    public static StudentUIFrame getInstance() {
        if (StudentUIFrame.instance != null) {
            return StudentUIFrame.instance;
        }

        synchronized (StudentUIFrame.class) {
            StudentUIFrame.instance = new StudentUIFrame();
        }

        return StudentUIFrame.instance;
    }
}
