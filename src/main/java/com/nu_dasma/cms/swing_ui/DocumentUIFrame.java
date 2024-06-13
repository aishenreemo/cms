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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import com.nu_dasma.cms.Database;
import com.nu_dasma.cms.SwingApp;
import com.nu_dasma.cms.model.Document;
import com.nu_dasma.cms.model.User;


public class DocumentUIFrame extends BaseFrame {
    private static DocumentUIFrame instance;
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 800;
    public static final int COLUMNS = 3;
    public static final int ICON_SIZE = 20;
    public static final int PADDING_SIZE = 10;

    private Database db;
    private JPanel table;

    public DocumentUIFrame() {
        super("Documents");

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

        panel.add(createDocumentPanel());

        this.add(panel, BorderLayout.CENTER);
    }

    private JPanel createDocumentPanel() {
        JPanel documentPanel = new JPanel();
        documentPanel.setBackground(Palette.ROYAL_BLUE.getColor());
        documentPanel.setBorder(new RoundedBorder(PADDING_SIZE));
        documentPanel.setLayout(new BorderLayout());

        JPanel titlePanel = new JPanel();
        titlePanel.setPreferredSize(new Dimension((int)(WIDTH * 0.9), 50));
        titlePanel.setBackground(Palette.ROYAL_BLUE.getColor());
        titlePanel.setLayout(null);

        titlePanel.add(Box.createRigidArea(new Dimension(0, PADDING_SIZE)));
        ImageLabel documentIcon = new ImageLabel("documentIcon.png", ICON_SIZE, ICON_SIZE);
        documentIcon.setBounds(PADDING_SIZE, PADDING_SIZE, ICON_SIZE, ICON_SIZE);
        titlePanel.add(documentIcon);

        TextLabel documentLabel = new TextLabel("Documents", ICON_SIZE);
        documentLabel.setBounds(35, PADDING_SIZE, 300, ICON_SIZE);
        documentLabel.setForeground(Color.WHITE);
        titlePanel.add(documentLabel);
        titlePanel.add(Box.createRigidArea(new Dimension(0, PADDING_SIZE)));

        JSeparator separator = new JSeparator();
        separator.setOrientation(SwingConstants.HORIZONTAL);
        separator.setForeground(Color.WHITE);
        separator.setBounds(PADDING_SIZE, 35, WIDTH - 50, ICON_SIZE);
        titlePanel.add(separator);

        documentPanel.add(titlePanel, BorderLayout.NORTH);

        this.table = new JPanel();
        this.table.setPreferredSize(new Dimension((int)(WIDTH * 0.9), 300));
        this.table.setLayout(new BoxLayout(this.table, BoxLayout.Y_AXIS));
        this.table.setBackground(new Color(255, 255, 255, 0));

        this.table.add(this.createTablePanel());

        documentPanel.add(this.table, BorderLayout.CENTER);

        return documentPanel;
    }

    private JPanel createTablePanel() {
        this.table.revalidate();
        this.table.repaint();
        String[] tableColumnHeaders = { "Student ID", "Student Name", "Document", "View", "Approve", "Reject"};

        JPanel tablePanel = new JPanel();
        tablePanel.setPreferredSize(new Dimension(WIDTH - 60, 100));
        tablePanel.setLayout(new BorderLayout());
        tablePanel.setBackground(new Color(255, 255, 255, 0));

        JPanel titlePanel = new JPanel();
        titlePanel.setPreferredSize(new Dimension((int) (WIDTH * 0.9), 30));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(PADDING_SIZE, 20, 5, PADDING_SIZE));
        titlePanel.setLayout(new GridLayout(1, tableColumnHeaders.length, 5 ,5));
        titlePanel.setBackground(new Color(255, 255, 255, 100));

        for (String tableHeader:tableColumnHeaders) {
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

        ArrayList<Document> documents = this.db.getAllPendingDocuments();
        for (Document document : documents) {
            User user = this.db.getUserByStudentID(document.studentID);
            rowPanel.add(this.createRow(document.studentID, user.getFullName(), document.name, document.type));
            rowPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        JScrollPane scrollPane = new JScrollPane(rowPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        return tablePanel;
    }

    private JPanel createRow(int studentID, String studentName, String document, int documentTypeID) {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension((int) (WIDTH * 0.9), 50));
        panel.setMaximumSize(new Dimension((int) (WIDTH * 0.9), 50));
        panel.setLayout(new GridLayout(1, 6, 5 ,5));
        panel.setBackground(Palette.WHITE.getColor());
        panel.setBorder(new RoundedBorder(10));

        panel.add(new TextLabel(String.valueOf(studentID), 10));
        panel.add(new TextLabel(studentName, 10));
        panel.add(new TextLabel(document, 8));

        CustomButton viewButton = new CustomButton("view", 20, 50, PADDING_SIZE, PADDING_SIZE);
        CustomButton approveButton = new CustomButton("approve", 20, 50, PADDING_SIZE, PADDING_SIZE);
        CustomButton rejectButton = new CustomButton("reject", 20, 50, PADDING_SIZE, PADDING_SIZE);

        viewButton.setForeground(Palette.WHITE.getColor());
        viewButton.setBackground(Palette.ROYAL_BLUE.getColor());
        viewButton.addActionListener(e -> {
            DocumentUIFrame frame = DocumentUIFrame.getInstance();
            try {
                frame.db.viewStudentDocument(studentID, documentTypeID);
            } catch (SQLException err) {
                JOptionPane.showMessageDialog(null, err.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        approveButton.setForeground(Palette.WHITE.getColor());
        approveButton.setBackground(Palette.ROYAL_BLUE.getColor());
        approveButton.addActionListener(e -> {
            DocumentUIFrame frame = DocumentUIFrame.getInstance();
            frame.db.updateDocumentStatus(studentID, documentTypeID, "APPROVED");
            this.table.remove(this.table.getComponentCount() - 1);
            this.revalidate();
            this.repaint();
            this.table.add(this.createTablePanel());
        });

        rejectButton.setForeground(Palette.WHITE.getColor());
        rejectButton.setBackground(Palette.ROYAL_BLUE.getColor());
        rejectButton.addActionListener(e -> {
            DocumentUIFrame frame = DocumentUIFrame.getInstance();
            frame.db.updateDocumentStatus(studentID, documentTypeID, "REJECTED");
            this.table.remove(this.table.getComponentCount() - 1);
            this.revalidate();
            this.repaint();
            this.table.add(this.createTablePanel());
        });

        panel.add(viewButton);
        panel.add(approveButton);
        panel.add(rejectButton);
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        return panel;
    }


    @Override
    public void dispose() {
        synchronized (DocumentUIFrame.class) {
            DocumentUIFrame.instance = null;
            super.dispose();
        }
    }

    public static DocumentUIFrame getInstance() {
        if (DocumentUIFrame.instance != null) {
            return DocumentUIFrame.instance;
        }

        synchronized (DocumentUIFrame.class) {
            DocumentUIFrame.instance = new DocumentUIFrame();
        }

        return DocumentUIFrame.instance;
    }
}
