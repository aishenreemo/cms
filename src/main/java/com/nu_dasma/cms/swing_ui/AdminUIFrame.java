
package com.nu_dasma.cms.swing_ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import com.nu_dasma.cms.SwingApp;

public class AdminUIFrame extends BaseFrame {
    private static AdminUIFrame instance;
    public static final int WIDTH = 400;
    public static final int HEIGHT = 600;
    public static final int COLUMNS = 3;
    public static final int ROWS = 5;

    public AdminUIFrame() {
        super("CMS Admin Home");
        this.setLayout(new BorderLayout());
        this.setSize(WIDTH, HEIGHT);
        this.setBackground(Color.WHITE);

        JPanel titlePanel = new JPanel();
        JPanel mainPanel = new JPanel();
        JPanel documents = new JPanel();
        JPanel inventory = new JPanel();
        JPanel students = new JPanel();

        TextLabel documentLabel = new TextLabel("Documents", 20);
        TextLabel inventoryLabel = new TextLabel("Inventory", 20);
        TextLabel studentLabel = new TextLabel("Students", 20);

        TextLabel documentText = new TextLabel("View submission of requirements", 10);
        TextLabel inventoryText = new TextLabel("Borrowable Items", 10);
        TextLabel studentText = new TextLabel("Student Tuition Fees", 10);

        CustomButton documentButton = new CustomButton("➜", 30, 30, 10, 10);
        CustomButton inventoryButton = new CustomButton("➜", 30, 30, 10, 10);
        CustomButton studentButton = new CustomButton("➜", 30, 30, 10, 10);

        JSeparator separator = new JSeparator();
        separator.setOrientation(SwingConstants.HORIZONTAL);
        separator.setForeground(Color.BLACK);

        ImageLabel documentIcon = new ImageLabel("documentIcon.png", 120, 120);
        ImageLabel inventoryIcon = new ImageLabel("inventoryIcon.png", 120, 120);
        ImageLabel studentIcon = new ImageLabel("studentIcon.png", 120, 120);

        Dimension panelSize = new Dimension(WIDTH - 200, 100);

        titlePanel.setPreferredSize(new Dimension(WIDTH, (int) (HEIGHT * 0.1)));
        titlePanel.setBackground(Color.GRAY);

        mainPanel.setPreferredSize(new Dimension(WIDTH, (int) (HEIGHT * 0.9)));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        documents.setPreferredSize(panelSize);
        documents.setLayout(null);
        documents.setBackground(new Color(0x919191));
        documents.setBorder(new RoundedBorder(10));

        documentIcon.setBounds(10, 10, 120, 120);
        documentLabel.setBounds(140, 20, 100, 20);
        documentLabel.setForeground(Color.WHITE);
        documentText.setBounds(140, 40, 200, 20);
        documentText.setForeground(Color.WHITE);
        documentButton.setBounds(310, 100, 50, 30);
        documentButton.setBackground(Color.WHITE);

        documentButton.addActionListener(e -> {
            SwingApp app = SwingApp.getInstance();
            app.ui.dispose();
            app.ui = DocumentUIFrame.getInstance();
        });

        documents.add(documentIcon);
        documents.add(documentLabel);
        documents.add(documentText);
        documents.add(documentButton);

        inventory.setPreferredSize(panelSize);
        inventory.setLayout(null);
        inventory.setBackground(new Color(0x919191));
        inventory.setBorder(new RoundedBorder(10));

        inventoryIcon.setBounds(10, 10, 120, 120);
        inventoryLabel.setBounds(140, 20, 100, 20);
        inventoryLabel.setForeground(Color.WHITE);
        inventoryText.setBounds(140, 40, 200, 20);
        inventoryText.setForeground(Color.WHITE);
        inventoryButton.setBounds(310, 100, 50, 30);
        inventoryButton.setBackground(Color.WHITE);

        inventoryButton.addActionListener(e -> {
            SwingApp app = SwingApp.getInstance();
            app.ui.dispose();
            app.ui = InventoryUIFrame.getInstance();
        });

        inventory.add(inventoryLabel);
        inventory.add(inventoryIcon);
        inventory.add(inventoryText);
        inventory.add(inventoryButton);

        students.setPreferredSize(panelSize);
        students.setLayout(null);
        students.setBackground(new Color(0x919191));
        students.setBorder(new RoundedBorder(10));

        studentIcon.setBounds(10, 10, 120, 120);
        studentLabel.setBounds(140, 20, 100, 20);
        studentLabel.setForeground(Color.WHITE);
        studentText.setBounds(140, 40, 200, 20);
        studentText.setForeground(Color.WHITE);
        studentButton.setBounds(310, 100, 50, 30);
        studentButton.setBackground(Color.WHITE);

        studentButton.addActionListener(e -> {
            SwingApp app = SwingApp.getInstance();
            app.ui.dispose();
            app.ui = StudentListUIFrame.getInstance();
        });

        students.add(studentIcon);
        students.add(studentLabel);
        students.add(studentText);
        students.add(studentButton);

        mainPanel.add(new TextLabel(" Hello, welcome to your control panel!", 20));
        mainPanel.add(separator);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(documents);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(inventory);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(students);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));


        this.add(titlePanel, BorderLayout.NORTH);
        this.add(mainPanel, BorderLayout.CENTER);

        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
    }

    @Override
    public void dispose() {
        synchronized (AdminUIFrame.class) {
            AdminUIFrame.instance = null;
            super.dispose();
        }
    }

    public static AdminUIFrame getInstance() {
        if (AdminUIFrame.instance != null) {
            return AdminUIFrame.instance;
        }

        synchronized (AdminUIFrame.class) {
            AdminUIFrame.instance = new AdminUIFrame();
        }

        return AdminUIFrame.instance;
    }
}
