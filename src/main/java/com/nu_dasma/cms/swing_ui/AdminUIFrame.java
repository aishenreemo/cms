
package com.nu_dasma.cms.swing_ui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import com.nu_dasma.cms.SwingApp;

public class AdminUIFrame extends BaseFrame {
    private static AdminUIFrame instance;
    public static final int WIDTH = 900;
    public static final int HEIGHT = 600;
    public static final int PADDING_SIZE = 10;
    public static final int PANEL_WIDTH = ((WIDTH - 50) / 3);
    public static final int PANEL_HEIGHT = 120;
    public static final int PANEL_Y_AXIS = 50;
    public static final int PANEL_BUTTON_WIDTH= 50;
    public static final int PANEL_BUTTON_HEIGHT= 30;
    public static final int PANEL_ICON_SIZE= 100;

    public AdminUIFrame() {
        super("CMS Admin Home");
        this.setLayout(new BorderLayout());
        this.setSize(WIDTH, HEIGHT);
        this.setBackground(Palette.WHITE.getColor());

        this.initializeTitlePanel();
        this.initializeMainPanel();

        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
    }

    private void initializeTitlePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setPreferredSize(new Dimension(WIDTH, (int) (HEIGHT * 0.1)));
        panel.setBackground(Palette.ROYAL_BLUE.getColor());

        ImageLabel titleIcon = new ImageLabel("NULogoAdmins.png", 170, 50);
        titleIcon.setBounds(5, 5, 170, 50);
        panel.add(titleIcon);

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
        JPanel mainPanel = new JPanel();
        mainPanel.setPreferredSize(new Dimension(WIDTH, (int) (HEIGHT * 0.9)));
        mainPanel.setLayout(null);
        mainPanel.setBackground(Palette.GOLDEN_YELLOW.getColor());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        TextLabel greetingLabel = new TextLabel("Hello, welcome to your control panel!", 20);
        greetingLabel.setBounds(PADDING_SIZE, PADDING_SIZE, 500, 20);
        greetingLabel.setForeground(Palette.ROYAL_BLUE.getColor());
        mainPanel.add(greetingLabel);

        JSeparator separator = new JSeparator();
        separator.setBounds(PADDING_SIZE, 30, WIDTH - 25, 20);
        separator.setOrientation(SwingConstants.HORIZONTAL);
        separator.setForeground(Palette.ROYAL_BLUE.getColor());
        separator.setBackground(Palette.ROYAL_BLUE.getColor());
        mainPanel.add(separator);

        mainPanel.add(createDocumentPanel());
        mainPanel.add(createInventoryPanel());
        mainPanel.add(createLedgerPanel());

        this.add(mainPanel, BorderLayout.CENTER);
    }

    private JPanel createDocumentPanel() {
        JPanel documents = new JPanel();
        documents.setBounds(PADDING_SIZE, PANEL_Y_AXIS, PANEL_WIDTH, PANEL_HEIGHT);
        documents.setLayout(null);
        documents.setBackground(Palette.ROYAL_BLUE.getColor());
        documents.setBorder(new RoundedBorder(10));

        ImageLabel documentIcon = new ImageLabel("documentIcon.png", PANEL_ICON_SIZE, PANEL_ICON_SIZE);
        documentIcon.setBounds(5, PADDING_SIZE, PANEL_ICON_SIZE, PANEL_ICON_SIZE);
        documents.add(documentIcon);

        TextLabel documentLabel = new TextLabel("Documents", 20);
        documentLabel.setBounds(PANEL_ICON_SIZE + 15, 20, 100, 20);
        documentLabel.setForeground(Palette.WHITE.getColor());
        documents.add(documentLabel);

        TextLabel documentText = new TextLabel("View submission of requirements", 10);
        documentText.setBounds(PANEL_ICON_SIZE + 15, 40, 200, 20);
        documentText.setForeground(Palette.WHITE.getColor());
        documents.add(documentText);

        CustomButton documentButton = new CustomButton("➜", PANEL_BUTTON_HEIGHT, PANEL_BUTTON_HEIGHT, PADDING_SIZE, PADDING_SIZE);
        documentButton.setBounds(PANEL_WIDTH - 60, 80, PANEL_BUTTON_WIDTH, PANEL_BUTTON_HEIGHT);
        documentButton.setBackground(Palette.WHITE.getColor());
        documentButton.addActionListener(e -> {
            SwingApp app = SwingApp.getInstance();
            app.ui.dispose();
            app.ui = DocumentUIFrame.getInstance();
        });
        documents.add(documentButton);

        return documents;
    }

    private JPanel createInventoryPanel() {
        JPanel inventory = new JPanel();
        inventory.setBounds(PANEL_WIDTH + 20, PANEL_Y_AXIS, PANEL_WIDTH, PANEL_HEIGHT);
        inventory.setLayout(null);
        inventory.setBackground(Palette.ROYAL_BLUE.getColor());
        inventory.setBorder(new RoundedBorder(10));

        ImageLabel inventoryIcon = new ImageLabel("inventoryIcon.png", PANEL_ICON_SIZE, PANEL_ICON_SIZE);
        inventoryIcon.setBounds(10, 10, PANEL_ICON_SIZE, PANEL_ICON_SIZE);
        inventory.add(inventoryIcon);


        TextLabel inventoryLabel = new TextLabel("Inventory", 20);
        inventoryLabel.setBounds(PANEL_ICON_SIZE + 15, 20, 100, 20);
        inventoryLabel.setForeground(Palette.WHITE.getColor());
        inventory.add(inventoryLabel);

        TextLabel inventoryText = new TextLabel("Borrowable Items", 10);
        inventory.add(inventoryText);
        inventoryText.setBounds(PANEL_ICON_SIZE + 15, 40, 200, 20);
        inventoryText.setForeground(Palette.WHITE.getColor());

        CustomButton inventoryButton = new CustomButton("➜", PANEL_BUTTON_HEIGHT, PANEL_BUTTON_HEIGHT, PADDING_SIZE, PADDING_SIZE);
        inventoryButton.setBounds(PANEL_WIDTH - 60, 80, PANEL_BUTTON_WIDTH, PANEL_BUTTON_HEIGHT);
        inventoryButton.setBackground(Palette.WHITE.getColor());
        inventoryButton.addActionListener(e -> {
            SwingApp app = SwingApp.getInstance();
            app.ui.dispose();
            app.ui = InventoryUIFrame.getInstance();
        });
        inventory.add(inventoryButton);

        return inventory;
    }

    private JPanel createLedgerPanel() {
        JPanel ledger = new JPanel();
        ledger.setBounds((PANEL_WIDTH * 2) + 30, 50, PANEL_WIDTH, PANEL_HEIGHT);
        ledger.setLayout(null);
        ledger.setBackground(Palette.ROYAL_BLUE.getColor());
        ledger.setBorder(new RoundedBorder(10));

        ImageLabel ledgerIcon = new ImageLabel("ledgerIcon.png", PANEL_ICON_SIZE, PANEL_ICON_SIZE);
        ledgerIcon.setBounds(10, 10, PANEL_ICON_SIZE, PANEL_ICON_SIZE);
        ledger.add(ledgerIcon);

        TextLabel ledgerLabel = new TextLabel("Ledger", 20);
        ledgerLabel.setBounds(PANEL_ICON_SIZE + 15, 20, 100, 20);
        ledgerLabel.setForeground(Palette.WHITE.getColor());
        ledger.add(ledgerLabel);

        TextLabel ledgerText = new TextLabel("Student Tuition Fees", 10);
        ledgerText.setBounds(PANEL_ICON_SIZE + 15, 40, 200, 20);
        ledgerText.setForeground(Palette.WHITE.getColor());
        ledger.add(ledgerText);

        CustomButton ledgerButton = new CustomButton("➜", 30, 30, 10, 10);
        ledgerButton.setBounds(PANEL_WIDTH - 60, 80, 50, 30);
        ledgerButton.setBackground(Palette.WHITE.getColor());
        ledgerButton.addActionListener(e -> {
            SwingApp app = SwingApp.getInstance();
            app.ui.dispose();
            app.ui = LedgerUIFrame.getInstance();
        });
        ledger.add(ledgerButton);

        return ledger;
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
