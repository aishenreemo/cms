
package com.nu_dasma.cms.swing_ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import com.nu_dasma.cms.Database;
import com.nu_dasma.cms.SwingApp;
import com.nu_dasma.cms.model.Student;
import com.nu_dasma.cms.model.User;

public class AdminUIFrame extends BaseFrame {
    private static AdminUIFrame instance;
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 800;
    public static final int PADDING_SIZE = 10;
    public static final int PANEL_WIDTH = ((WIDTH - 50) / 3);
    public static final int PANEL_HEIGHT = 120;
    public static final int PANEL_Y_AXIS = 50;
    public static final int PANEL_BUTTON_WIDTH = 50;
    public static final int PANEL_BUTTON_HEIGHT = 30;

    public static final int PANEL_ICON_SIZE = 100;
    public static final int TABLE_WIDTH = WIDTH - 30;
    public static final int TABLE_HEIGHT = (int) (HEIGHT * 0.65);

    private User user;

    public AdminUIFrame() {
        super("CMS Admin Home");

        this.initializeUser();
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
        panel.setPreferredSize(new Dimension(WIDTH, (int) (HEIGHT * 0.075)));
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

        TextLabel greetingLabel = new TextLabel(String.format("Hello %s, welcome to your control panel!", this.user.firstName), 20);
        greetingLabel.setBounds(PADDING_SIZE, PADDING_SIZE, WIDTH, 20);
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
        mainPanel.add(createMainTablePanel());

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
        documentLabel.setBounds(PANEL_ICON_SIZE + 15, 20, 200, 20);
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

    private JPanel createMainTablePanel() {
        int ICON_SIZE = 20;
        //title + blank panel for table
        JPanel mainTblPanel = new JPanel();
        mainTblPanel.setBounds(10, 180, TABLE_WIDTH, TABLE_HEIGHT);
        mainTblPanel.setLayout(new BorderLayout());
        mainTblPanel.setBorder(new RoundedBorder(10));
        mainTblPanel.setBackground(Palette.ROYAL_BLUE.getColor());

        //titlePanel
        JPanel titlePanel = new JPanel();
        titlePanel.setPreferredSize(new Dimension(TABLE_WIDTH, 50));
        titlePanel.setBackground(Palette.ROYAL_BLUE.getColor());
        titlePanel.setLayout(null);

        ImageLabel studentIcon = new ImageLabel("studentIcon.png", ICON_SIZE, ICON_SIZE);
        studentIcon.setBounds(PADDING_SIZE, PADDING_SIZE, ICON_SIZE, ICON_SIZE);
        titlePanel.add(studentIcon);

        TextLabel studentLabel = new TextLabel("Students List", ICON_SIZE);
        studentLabel.setBounds(35, PADDING_SIZE, 300, ICON_SIZE);
        studentLabel.setForeground(Palette.WHITE.getColor());
        titlePanel.add(studentLabel);

        JSeparator separator = new JSeparator();
        separator.setOrientation(SwingConstants.HORIZONTAL);
        separator.setForeground(Palette.WHITE.getColor());
        separator.setBounds(PADDING_SIZE, 35, WIDTH - 50, ICON_SIZE);
        titlePanel.add(separator);

        //tablePanel
        JPanel tablePanel = new JPanel();
        tablePanel.setPreferredSize(new Dimension(TABLE_WIDTH, TABLE_HEIGHT));
        tablePanel.setLayout(new BoxLayout(tablePanel, BoxLayout.Y_AXIS));
        tablePanel.setBackground(Palette.WHITE.getColor());
        tablePanel.add(createTablePanel(), BorderLayout.CENTER);

        mainTblPanel.add(titlePanel, BorderLayout.NORTH);
        mainTblPanel.add(tablePanel, BorderLayout.CENTER);

        return mainTblPanel;
    }

    private JPanel createTablePanel() {
        //borderlayout = table headers (north), rowPanel (center)
        String[] tableColumnHeaders = { "Student ID", "Student Name", "Clearance Progress", "Clearance Status"};
        int headerY = 15;
        int headerX = 30;
        JPanel tblPanel = new JPanel();
        tblPanel.setPreferredSize(new Dimension(TABLE_WIDTH, TABLE_HEIGHT));
        tblPanel.setLayout(new BorderLayout());
        tblPanel.setBackground(new Color(255, 255, 255, 0));

        JPanel columnPanel = new JPanel();
        columnPanel.setPreferredSize(new Dimension(TABLE_WIDTH, (int) (TABLE_HEIGHT * 0.1)));
        columnPanel.setLayout(null);
        columnPanel.setBackground(new Color(255, 255, 255, 50));

        for (String header : tableColumnHeaders) {
            TextLabel columnHeader = new TextLabel(header, 15);
            columnHeader.setBounds(headerX, headerY, 150, 15);
            columnHeader.setForeground(Palette.WHITE.getColor());

            headerX += 250;

            columnPanel.add(columnHeader);
        }

        JPanel rowPanel = new JPanel();
        rowPanel.setPreferredSize(new Dimension(TABLE_WIDTH, (int) (TABLE_HEIGHT * 0.77)));
        rowPanel.setLayout(new BoxLayout(rowPanel, BoxLayout.Y_AXIS));
        rowPanel.setBackground(Palette.WHITE.getColor());
        rowPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        ArrayList<Student> students = Database.getInstance().getAllStudents();
        for (Student student : students) {
            rowPanel.add(this.createRow(student));
            rowPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        JScrollPane rowScrollPanel = new JScrollPane(rowPanel);
        rowScrollPanel.setPreferredSize(new Dimension(TABLE_WIDTH, (int) (TABLE_HEIGHT * 0.77)));
        rowScrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        rowScrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        rowScrollPanel.setBackground(new Color(255, 255, 255, 50));
        rowScrollPanel.setBackground(Palette.WHITE.getColor());

        tblPanel.add(columnPanel, BorderLayout.NORTH);
        tblPanel.add(rowScrollPanel, BorderLayout.SOUTH);

        return tblPanel;
    }

    private JPanel createRow(Student student) {
        int headerX = 30;
        int paddingX = 250;
        int paddingY = 15;

        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension((int) (WIDTH * 1.85), 50));
        panel.setMaximumSize(new Dimension((int) (WIDTH * 1.85), 50));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.setLayout(null);
        panel.setBackground(Palette.WHITE.getColor());
        panel.setBorder(new RoundedBorder(10));

        TextLabel studentID = new TextLabel(String.valueOf(student.studentID), 12);
        studentID.setBounds(headerX, paddingY, 150, 15);
        panel.add(studentID);

        headerX += paddingX;

        TextLabel studentName = new TextLabel(student.getFullName(), 12);
        studentName.setBounds(headerX, paddingY, 150, 15);
        panel.add(studentName);

        headerX += paddingX;

        int progressAmount = (int) (student.getProgressPercentage() * 100);
        JProgressBar progressBar = new JProgressBar(0, 100);
        progressBar.setValue(progressAmount);
        progressBar.setString(String.format("%d%%", progressAmount));
        progressBar.setBounds(headerX, 10, 150, 30);
        panel.add(progressBar);

        headerX += paddingX;
        TextLabel status = new TextLabel("ONGOING", 12);
        status.setBounds(headerX, paddingY, 150, 15);
        if (progressAmount > 99) {
            status.setText("CLEARED");
        }

        panel.add(status);

        return panel;
    }

    private void initializeUser() {
        try {
            Database db = Database.getInstance();

            int userID = 1;
            if (db.loggedInUser != null) {
                userID = db.loggedInUser.id;
            }

            this.user = new User(db.connection, userID);
        } catch (SQLException e) {
            System.err.println("Read error: " + e.getMessage());
        }
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
