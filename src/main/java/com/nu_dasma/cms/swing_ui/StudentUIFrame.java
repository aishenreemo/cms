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

import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

public class StudentUIFrame extends BaseFrame {
    private static StudentUIFrame instance;
    public static final int WIDTH = 400;
    public static final int HEIGHT = 600;
    public static final int COLUMNS = 3;
    public static final int ROWS = 5;
    String[] labelHeaders = { "Course:", "ID Number: ", "Department: ", "Email:" };
    String[] requirements = { "Grade 12 Report Card", "Good Moral Certificate", "PSA Birth Certificate",
        "2x2 Colored Picture", "Form 137" };
    String[] inventoryColumns = {"Item Name", "Due Date", "Total Penalty"};

    public StudentUIFrame() {
        super("CMS Student Home");
        this.setLayout(new BorderLayout());
        this.setSize(WIDTH, HEIGHT);
        this.setBackground(Color.WHITE);

        JPanel titlePanel = new JPanel();
        JPanel mainPanel = new JPanel();
        JPanel studentInfo = new JPanel();
        JPanel matriculation = new JPanel();
        JPanel rqGrid = new JPanel();
        JPanel miniTitlePanel = new JPanel();
        JPanel financial = new JPanel();
        JPanel miscellanous = new JPanel();

        CustomButton[] submitButtons = new CustomButton[requirements.length];
        ImageLabel idImage = new ImageLabel("dummyImage.png", 150, 150);

        TextLabel balanceAmount = new TextLabel("CLEARED", 40);
        TextLabel paidAmount = new TextLabel("99999999", 20);
        TextLabel totalTF = new TextLabel("99999999", 20);
        TextLabel name = new TextLabel("SAMPLE NAME", 20);
        TextLabel course = new TextLabel("Bachelor of fuckall", 10);
        TextLabel idNum = new TextLabel("i84937248", 10);
        TextLabel department = new TextLabel("SECA", 10);
        TextLabel email = new TextLabel("dnuts@students.nu-narnia.edu.ph", 8);
        TextLabel rqmt = new TextLabel("Requirements", 20);

        JSeparator separator = new JSeparator();
        separator.setOrientation(SwingConstants.HORIZONTAL);
        separator.setForeground(Color.BLACK);

        JSeparator separator1 = new JSeparator();
        separator1.setOrientation(SwingConstants.HORIZONTAL);
        separator1.setForeground(Color.BLACK);

        JSeparator separator2 = new JSeparator();
        separator2.setOrientation(SwingConstants.HORIZONTAL);
        separator2.setForeground(Color.BLACK);

        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(inventoryColumns);

        JTable borrowedItems = new JTable();
        borrowedItems.setModel(model);
        borrowedItems.getTableHeader().setReorderingAllowed(false);

        JScrollPane borrowedItemsScroll = new JScrollPane(borrowedItems);
        borrowedItemsScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        titlePanel.setPreferredSize(new Dimension(WIDTH, (int) (HEIGHT * 0.1)));
        titlePanel.setBackground(Color.GRAY);

        mainPanel.setPreferredSize(new Dimension(WIDTH, (int) (HEIGHT * 1.7)));
        mainPanel.setMinimumSize(new Dimension(WIDTH, (int) (HEIGHT * 1.7)));

        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.WHITE);


        studentInfo.setLayout(null);
        studentInfo.setBounds(5, 5, WIDTH, 150);
        studentInfo.setBackground(Color.WHITE);
        studentInfo.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        int headerX = 160;
        int headerY = 10;
        idImage.setBounds(0, 0, 150, 150);
        name.setBounds(headerX, headerY, 200, 35);
        course.setBounds(headerX + 65, headerY + 31, 200, 30);
        idNum.setBounds(headerX + 85, headerY + 56, 200, 30);
        department.setBounds(headerX + 95, headerY + 81, 200, 30);
        email.setBounds(headerX + 45, headerY + 106, 200, 30);

        studentInfo.add(name);
        for (String label : labelHeaders) {
            TextLabel header = new TextLabel(label, 10);
            header.setBounds(headerX, headerY + 30, 200, 30);
            header.setFont(new Font("Arial", Font.BOLD, 12));
            headerY += 25;
            studentInfo.add(header);
        }
        studentInfo.add(course);
        studentInfo.add(idNum);
        studentInfo.add(department);
        studentInfo.add(email);
        studentInfo.add(idImage);

        matriculation.setLayout(new BorderLayout());
        matriculation.setBounds(5, 165, (int) (WIDTH * 0.9), 250);
        matriculation.setBackground(Color.WHITE);
        matriculation.setBorder(new RoundedBorder(10));

        miniTitlePanel.setLayout(new BoxLayout (miniTitlePanel, BoxLayout.Y_AXIS));
        miniTitlePanel.setBackground(Color.WHITE);
        miniTitlePanel.setPreferredSize(new Dimension((int) (WIDTH * 0.9), (int)(250 * 0.2)));
        miniTitlePanel.add(rqmt);
        miniTitlePanel.add(separator1);

        rqGrid.setPreferredSize(new Dimension((int) (WIDTH * 0.9), (int)(250 * 0.8)));
        rqGrid.setLayout(new GridLayout(5, 3, 1, 1));
        rqGrid.setBackground(Color.WHITE);

        for (int i = 0; i < requirements.length; i++) {
            TextLabel rq = new TextLabel(requirements[i], 12);
            rq.setFont(new Font("Arial", Font.BOLD, 12));
            rqGrid.add(rq);

            submitButtons[i] = new CustomButton("submit", 5, 10, 15, 15);
            submitButtons[i].setForeground(Color.WHITE);
            rqGrid.add(submitButtons[i]);

        }

        matriculation.add(miniTitlePanel, BorderLayout.NORTH);
        matriculation.add(rqGrid, BorderLayout.CENTER);

        financial.setBounds(5, 425, (int) (WIDTH * 0.9), 200);
        financial.setLayout(new BoxLayout(financial, BoxLayout.Y_AXIS));
        financial.setBackground(Color.WHITE);
        financial.setBorder(new RoundedBorder(10));
        financial.add(new TextLabel("Student Ledger", 20));
        financial.add(separator);

        financial.add(Box.createRigidArea(new Dimension(0, 10)));
        financial.add(new TextLabel("Total Tuition Fee: ", 10));
        financial.add(totalTF);
        financial.add(Box.createRigidArea(new Dimension(0, 10)));

        financial.add(new TextLabel("Amount Paid: ", 10));
        financial.add(paidAmount);
        financial.add(Box.createRigidArea(new Dimension(0, 10)));

        financial.add(new TextLabel("Remaining Balance:", 10));
        financial.add(balanceAmount);

        miscellanous.setBounds(5, 635, (int) (WIDTH * 0.9), 350);
        miscellanous.setLayout(new BoxLayout(miscellanous, BoxLayout.Y_AXIS));
        miscellanous.setBackground(Color.WHITE);
        miscellanous.setBorder(new RoundedBorder(10));
        miscellanous.add(new TextLabel("Student Inventory", 20));
        miscellanous.add(separator2);
        miscellanous.add(Box.createRigidArea(new Dimension(0, 10)));
        miscellanous.add(borrowedItemsScroll);

        mainPanel.add(studentInfo);
        mainPanel.add(matriculation);
        mainPanel.add(financial);
        mainPanel.add(miscellanous);

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

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
