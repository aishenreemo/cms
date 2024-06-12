
package com.nu_dasma.cms.swing_ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import com.nu_dasma.cms.Database;
import com.nu_dasma.cms.SwingApp;
import com.nu_dasma.cms.model.Student;

public class StudentListUIFrame extends BaseFrame {
    private static StudentListUIFrame instance;

    private Database db;

    public static final int WIDTH = 1000;
    public static final int HEIGHT = 600;
    public static final int COLUMNS = 3;
    public static final int ICON_SIZE = 20;
    public static final int PADDING_SIZE = 10;

    public StudentListUIFrame() {
        super("Student List");

        this.db = Database.getInstance();

        this.setLayout(new BorderLayout());
        this.setSize(WIDTH, HEIGHT);
        this.setBackground(Color.WHITE);

        this.initializeTitlePanel();
        this.initializeMainPanel();

        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
    }

    private void initializeTitlePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.TRAILING, PADDING_SIZE, PADDING_SIZE));
        panel.setPreferredSize(new Dimension(WIDTH, (int) (HEIGHT * 0.1)));
        panel.setBackground(Color.GRAY);

        CustomButton back = new CustomButton("Back", 50, 30, PADDING_SIZE, PADDING_SIZE);
        back.setBackground(Color.WHITE);
        back.setForeground(Color.GRAY);
        back.addActionListener(e -> {
            SwingApp app = SwingApp.getInstance();
            app.ui.dispose();
            app.ui = AdminUIFrame.getInstance();
        });
        panel.add(back);

        CustomButton logout = new CustomButton("Logout", 50, 30, PADDING_SIZE, PADDING_SIZE);
        logout.setBackground(Color.WHITE);
        logout.setForeground(Color.GRAY);
        logout.addActionListener(e -> {
            SwingApp app = SwingApp.getInstance();
            app.ui.dispose();
            app.ui = LoginFrame.getInstance();
            app.db.loggedInUser = null;
        });
        panel.add(logout);

        this.add(panel, BorderLayout.NORTH);
    }

    private void initializeMainPanel() {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(WIDTH, (int) (HEIGHT * 0.9)));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(PADDING_SIZE, PADDING_SIZE, PADDING_SIZE, PADDING_SIZE));
        panel.add(createStudentPanel());

        this.add(panel, BorderLayout.CENTER);
    }

    private JPanel createStudentPanel() {
        JPanel studentPanel = new JPanel();
        studentPanel.setBackground(Color.GRAY);
        studentPanel.setBorder(new RoundedBorder(PADDING_SIZE));
        studentPanel.setLayout(new BorderLayout());

        JPanel titlePanel = new JPanel();
        titlePanel.setPreferredSize(new Dimension((int) (WIDTH * 0.9), 50));
        titlePanel.setBackground(Color.GRAY);
        titlePanel.setLayout(null);

        titlePanel.add(Box.createRigidArea(new Dimension(0, PADDING_SIZE)));
        ImageLabel studentIcon = new ImageLabel("studentIcon.png", ICON_SIZE, ICON_SIZE);
        studentIcon.setBounds(PADDING_SIZE, PADDING_SIZE, ICON_SIZE, ICON_SIZE);
        titlePanel.add(studentIcon);

        TextLabel studentLabel = new TextLabel("Student List", ICON_SIZE);
        studentLabel.setBounds(35, PADDING_SIZE, 300, ICON_SIZE);
        studentLabel.setForeground(Color.WHITE);
        titlePanel.add(studentLabel);
        titlePanel.add(Box.createRigidArea(new Dimension(0, PADDING_SIZE)));

        JSeparator separator = new JSeparator();
        separator.setOrientation(SwingConstants.HORIZONTAL);
        separator.setForeground(Color.WHITE);
        separator.setBounds(PADDING_SIZE, 35, WIDTH - 50, ICON_SIZE);
        titlePanel.add(separator);

        studentPanel.add(titlePanel, BorderLayout.NORTH);

        JPanel tblPanel = new JPanel();
        tblPanel.setPreferredSize(new Dimension((int) (WIDTH * 0.9), 300));
        tblPanel.setBackground(Color.GRAY);
        tblPanel.setLayout(new BoxLayout(tblPanel, BoxLayout.Y_AXIS));
        tblPanel.setBackground(new Color(255, 255, 255, 0));

        tblPanel.add(createTablePanel());

        studentPanel.add(tblPanel, BorderLayout.CENTER);

        return studentPanel;

    }

    private JPanel createTablePanel() {
        String[] tableColumnHeaders = {
            "Student ID",
            "Student Name",
            "Total Tuition Fee",
            "Amount Paid",
            "Remaining Balance",
            "Action"
        };

        JPanel tablePanel = new JPanel();
        tablePanel.setPreferredSize(new Dimension(WIDTH - 60, 100));
        tablePanel.setLayout(new BorderLayout());
        tablePanel.setBackground(new Color(255, 255, 255, 0));

        JPanel titlePanel = new JPanel();
        titlePanel.setPreferredSize(new Dimension((int) (WIDTH * 0.9), 30));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(PADDING_SIZE, 20, 5, 40));
        titlePanel.setLayout(new GridLayout(1, tableColumnHeaders.length, 5, 5));
        titlePanel.setBackground(new Color(255, 255, 255, 100));

        for (String tableHeader : tableColumnHeaders) {
            TextLabel header = new TextLabel(tableHeader, 15);
            header.setForeground(Color.WHITE);
            header.setAlignmentX(Component.CENTER_ALIGNMENT);
            titlePanel.add(header);
        }

        tablePanel.add(titlePanel, BorderLayout.NORTH);

        JPanel rowPanel = new JPanel();
        rowPanel.setLayout(new BoxLayout(rowPanel, BoxLayout.Y_AXIS));
        rowPanel.setBackground(new Color(255, 255, 255, 100));
        rowPanel.setBorder(BorderFactory.createEmptyBorder(PADDING_SIZE, PADDING_SIZE, PADDING_SIZE, PADDING_SIZE));

        ArrayList<Student> students = this.db.getAllStudents();
        for (Student student : students) {
            rowPanel.add(this.createRow(student));
            rowPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        JScrollPane scrollPane = new JScrollPane(rowPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        return tablePanel;

    }

    private JPanel createRow(Student student) {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension((int) (WIDTH * 0.9), 50));
        panel.setMaximumSize(new Dimension((int) (WIDTH * 0.9), 50));
        panel.setLayout(new GridLayout(1, 6, 5, 5));
        panel.setBackground(Color.WHITE);
        panel.setBorder(new RoundedBorder(10));

        panel.add(new TextLabel(String.valueOf(student.id), 12));
        panel.add(new TextLabel(student.getFullName(), 12));
        panel.add(new TextLabel(String.valueOf(student.tuitionFee), 12));
        panel.add(new TextLabel(String.valueOf(student.paidAmount), 12));

        if (student.tuitionFee > student.paidAmount) {
            panel.add(new TextLabel(String.valueOf(student.tuitionFee - student.paidAmount), 12));
        } else {
            panel.add(new TextLabel("CLEARED", 12));
        }

        CustomButton button = new CustomButton("Resolve", 20, 50, PADDING_SIZE, PADDING_SIZE);
        button.setForeground(Color.WHITE);
        panel.add(button);
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        return panel;
    }

    @Override
    public void dispose() {
        synchronized (StudentListUIFrame.class) {
            StudentListUIFrame.instance = null;
            super.dispose();
        }
    }

    public static StudentListUIFrame getInstance() {
        if (StudentListUIFrame.instance != null) {
            return StudentListUIFrame.instance;
        }

        synchronized (StudentListUIFrame.class) {
            StudentListUIFrame.instance = new StudentListUIFrame();
        }

        return StudentListUIFrame.instance;
    }
}
