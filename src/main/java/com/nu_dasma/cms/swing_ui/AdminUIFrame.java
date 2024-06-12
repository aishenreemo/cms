
package com.nu_dasma.cms.swing_ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import com.nu_dasma.cms.SwingApp;

public class AdminUIFrame extends BaseFrame {
    private static AdminUIFrame instance;
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    public static final int COLUMNS = 3;
    public static final int ROWS = 5;

    public AdminUIFrame() {
        super("CMS Admin Home");
        this.setLayout(new BorderLayout());
        this.setSize(WIDTH, HEIGHT);
        this.setBackground(Palette.WHITE.getColor());

        JPanel titlePanel = new JPanel();
        JPanel mainPanel = new JPanel();
        JPanel documents = new JPanel();
        JPanel inventory = new JPanel();
        JPanel ledger = new JPanel();

        TextLabel greetingLabel = new TextLabel("Hello, welcome to your control panel!", 20);
        TextLabel documentLabel = new TextLabel("Documents", 20);
        TextLabel inventoryLabel = new TextLabel("Inventory", 20);
        TextLabel ledgerLabel = new TextLabel("Ledger", 20);

        TextLabel documentText = new TextLabel("View submission of requirements", 10);
        TextLabel inventoryText = new TextLabel("Borrowable Items", 10);
        TextLabel ledgerText = new TextLabel("Student Tuition Fees", 10);

        CustomButton documentButton = new CustomButton("➜", 30, 30, 10, 10);
        CustomButton inventoryButton = new CustomButton("➜", 30, 30, 10, 10);
        CustomButton ledgerButton = new CustomButton("➜", 30, 30, 10, 10);

        JSeparator separator = new JSeparator();
        separator.setOrientation(SwingConstants.HORIZONTAL);
        separator.setForeground(Palette.ROYAL_BLUE.getColor());
        separator.setBackground(Palette.ROYAL_BLUE.getColor());

        ImageLabel titleIcon = new ImageLabel("NULogoAdmins.png", 170, 50);
        ImageLabel documentIcon = new ImageLabel("documentIcon.png", 120, 120);
        ImageLabel inventoryIcon = new ImageLabel("inventoryIcon.png", 120, 120);
        ImageLabel ledgerIcon = new ImageLabel("ledgerIcon.png", 120, 120);

        Dimension panelSize = new Dimension((WIDTH - 200) / 3, 100);

        greetingLabel.setForeground(Palette.ROYAL_BLUE.getColor());

        titlePanel.setPreferredSize(new Dimension(WIDTH, (int) (HEIGHT * 0.1)));
        titlePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        titlePanel.setBackground(Palette.ROYAL_BLUE.getColor());
        titlePanel.add(titleIcon);

        mainPanel.setPreferredSize(new Dimension(WIDTH, (int) (HEIGHT * 0.9)));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Palette.GOLDEN_YELLOW.getColor());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        documents.setPreferredSize(panelSize);
        documents.setLayout(null);
        documents.setBackground(Palette.ROYAL_BLUE.getColor());
        documents.setBorder(new RoundedBorder(10));

        documentIcon.setBounds(10, 10, 120, 120);
        documentLabel.setBounds(140, 20, 100, 20);
        documentLabel.setForeground(Palette.WHITE.getColor());
        documentText.setBounds(140, 40, 200, 20);
        documentText.setForeground(Palette.WHITE.getColor());
        documentButton.setBounds(310, 100, 50, 30);
        documentButton.setBackground(Palette.WHITE.getColor());

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
        inventory.setBackground(Palette.ROYAL_BLUE.getColor());
        inventory.setBorder(new RoundedBorder(10));

        inventoryIcon.setBounds(10, 10, 120, 120);
        inventoryLabel.setBounds(140, 20, 100, 20);
        inventoryLabel.setForeground(Palette.WHITE.getColor());
        inventoryText.setBounds(140, 40, 200, 20);
        inventoryText.setForeground(Palette.WHITE.getColor());
        inventoryButton.setBounds(310, 100, 50, 30);
        inventoryButton.setBackground(Palette.WHITE.getColor());

        inventoryButton.addActionListener(e -> {
            SwingApp app = SwingApp.getInstance();
            app.ui.dispose();
            app.ui = InventoryUIFrame.getInstance();
        });

        inventory.add(inventoryLabel);
        inventory.add(inventoryIcon);
        inventory.add(inventoryText);
        inventory.add(inventoryButton);

        ledger.setPreferredSize(panelSize);
        ledger.setLayout(null);
        ledger.setBackground(Palette.ROYAL_BLUE.getColor());
        ledger.setBorder(new RoundedBorder(10));

        ledgerIcon.setBounds(10, 10, 120, 120);
        ledgerLabel.setBounds(140, 20, 100, 20);
        ledgerLabel.setForeground(Palette.WHITE.getColor());
        ledgerText.setBounds(140, 40, 200, 20);
        ledgerText.setForeground(Palette.WHITE.getColor());
        ledgerButton.setBounds(310, 100, 50, 30);
        ledgerButton.setBackground(Palette.WHITE.getColor());

        ledgerButton.addActionListener(e -> {
            SwingApp app = SwingApp.getInstance();
            app.ui.dispose();
            app.ui = LedgerUIFrame.getInstance();
        });

        ledger.add(ledgerIcon);
        ledger.add(ledgerLabel);
        ledger.add(ledgerText);
        ledger.add(ledgerButton);

        mainPanel.add(greetingLabel);
        mainPanel.add(separator);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(documents);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(inventory);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(ledger);
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
