
package com.nu_dasma.cms.swing_ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import com.nu_dasma.cms.Database;
import com.nu_dasma.cms.SwingApp;
import com.nu_dasma.cms.model.Student;

public class LedgerUIFrame extends BaseFrame {
    private static LedgerUIFrame instance;

    private Database db;
    private JPanel table;

    public static final int WIDTH = 1000;
    public static final int HEIGHT = 800;
    public static final int COLUMNS = 3;
    public static final int ICON_SIZE = 20;
    public static final int PADDING_SIZE = 10;

    public LedgerUIFrame() {
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
        panel.setLayout(null);
        panel.setPreferredSize(new Dimension(WIDTH, (int) (HEIGHT * 0.075)));
        panel.setBackground(Palette.ROYAL_BLUE.getColor());

        ImageLabel titleIcon = new ImageLabel("NULogoAdmins.png", 170, 50);
        titleIcon.setBounds(5, 5, 170, 50);
        panel.add(titleIcon);

        CustomButton back = new CustomButton("Back", 50, 30, PADDING_SIZE, PADDING_SIZE);
        back.setBounds(WIDTH - 125, 15, 50, 30);
        back.setBackground(Palette.WHITE.getColor());
        back.setForeground(Palette.ROYAL_BLUE.getColor());
        back.addActionListener(e -> {
            SwingApp app = SwingApp.getInstance();
            app.ui.dispose();
            app.ui = AdminUIFrame.getInstance();
        });
        panel.add(back);

        CustomButton logout = new CustomButton("Logout", 50, 30, PADDING_SIZE, PADDING_SIZE);
        logout.setBounds(WIDTH - 65, 15, 50, 30);
        logout.setBackground(Palette.WHITE.getColor());
        logout.setForeground(Palette.ROYAL_BLUE.getColor());
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
        panel.setBackground(Palette.GOLDEN_YELLOW.getColor());
        panel.setBorder(BorderFactory.createEmptyBorder(PADDING_SIZE, PADDING_SIZE, PADDING_SIZE, PADDING_SIZE));
        panel.add(createLedgerPanel());

        this.add(panel, BorderLayout.CENTER);
    }

    private JPanel createLedgerPanel() {
        JPanel studentPanel = new JPanel();
        studentPanel.setBackground(Palette.ROYAL_BLUE.getColor());
        studentPanel.setBorder(new RoundedBorder(PADDING_SIZE));
        studentPanel.setLayout(new BorderLayout());

        JPanel titlePanel = new JPanel();
        titlePanel.setPreferredSize(new Dimension((int) (WIDTH * 0.9), 50));
        titlePanel.setBackground(Palette.ROYAL_BLUE.getColor());
        titlePanel.setLayout(null);

        titlePanel.add(Box.createRigidArea(new Dimension(0, PADDING_SIZE)));
        ImageLabel ledgerIcon = new ImageLabel("ledgerIcon.png", ICON_SIZE, ICON_SIZE);
        ledgerIcon.setBounds(PADDING_SIZE, PADDING_SIZE, ICON_SIZE, ICON_SIZE);
        titlePanel.add(ledgerIcon);

        TextLabel ledgerLabel = new TextLabel("Ledger", ICON_SIZE);
        ledgerLabel.setBounds(35, PADDING_SIZE, 300, ICON_SIZE);
        ledgerLabel.setForeground(Palette.WHITE.getColor());
        titlePanel.add(ledgerLabel);
        titlePanel.add(Box.createRigidArea(new Dimension(0, PADDING_SIZE)));

        JSeparator separator = new JSeparator();
        separator.setOrientation(SwingConstants.HORIZONTAL);
        separator.setForeground(Palette.WHITE.getColor());
        separator.setBounds(PADDING_SIZE, 35, WIDTH - 50, ICON_SIZE);
        titlePanel.add(separator);

        studentPanel.add(titlePanel, BorderLayout.NORTH);

        this.table = new JPanel();
        this.table.setPreferredSize(new Dimension((int) (WIDTH * 0.9), 300));
        this.table.setBackground(Color.GRAY);
        this.table.setLayout(new BoxLayout(this.table, BoxLayout.Y_AXIS));
        this.table.setBackground(new Color(255, 255, 255, 0));

        this.table.add(this.createTablePanel());

        studentPanel.add(this.table, BorderLayout.CENTER);

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
            header.setForeground(Palette.WHITE.getColor());
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
        scrollPane.setBackground(Palette.WHITE.getColor());
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        return tablePanel;
    }

    private JPanel createRow(Student student) {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension((int) (WIDTH * 0.9), 50));
        panel.setMaximumSize(new Dimension((int) (WIDTH * 0.9), 50));
        panel.setLayout(new GridLayout(1, 6, 5, 5));
        panel.setBackground(Palette.WHITE.getColor());
        panel.setBorder(new RoundedBorder(10));

        panel.add(new TextLabel(String.valueOf(student.studentID), 12));
        panel.add(new TextLabel(student.getFullName(), 12));
        panel.add(new TextLabel(String.valueOf(student.tuitionFee), 12));
        panel.add(new TextLabel(String.valueOf(student.paidAmount), 12));

        if (student.tuitionFee > student.paidAmount) {
            panel.add(new TextLabel(String.valueOf(student.tuitionFee - student.paidAmount), 12));
        } else {
            panel.add(new TextLabel("CLEARED", 12));
        }

        CustomButton button = new CustomButton("Resolve", 20, 50, PADDING_SIZE, PADDING_SIZE);
        button.setBackground(Palette.ROYAL_BLUE.getColor());
        button.setForeground(Color.WHITE);
        button.addActionListener(e -> {
            String[] options = {"Add paid amount", "Set paid amount", "Cancel"};

            int choice = JOptionPane.showOptionDialog(null,
                "What do you want to do:",
                "Selection",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                options[0]
            );

            if (choice == 2) {
                return;
            }

            LedgerUIFrame frame = LedgerUIFrame.getInstance();

            try {
                String inputAmount = JOptionPane.showInputDialog(null, "Enter Amount:", "CMS Amount Input", JOptionPane.QUESTION_MESSAGE);
                int newAmount = choice == 0 ? student.paidAmount + Integer.parseInt(inputAmount) : Integer.parseInt(inputAmount);

                this.db.setPaidAmountOfStudent(student.studentID, newAmount);
            } catch (NumberFormatException err) {
                JOptionPane.showMessageDialog(null, err.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }

            frame.table.remove(frame.table.getComponentCount() - 1);
            frame.table.add(frame.createTablePanel());
            frame.revalidate();
            frame.repaint();

            JOptionPane.showMessageDialog(null, "Succesful!", "Success", JOptionPane.INFORMATION_MESSAGE);
        });

        panel.add(button);
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        return panel;
    }

    @Override
    public void dispose() {
        synchronized (LedgerUIFrame.class) {
            LedgerUIFrame.instance = null;
            super.dispose();
        }
    }

    public static LedgerUIFrame getInstance() {
        if (LedgerUIFrame.instance != null) {
            return LedgerUIFrame.instance;
        }

        synchronized (LedgerUIFrame.class) {
            LedgerUIFrame.instance = new LedgerUIFrame();
        }

        return LedgerUIFrame.instance;
    }
}
