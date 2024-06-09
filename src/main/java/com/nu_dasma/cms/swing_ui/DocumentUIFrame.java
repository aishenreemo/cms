package com.nu_dasma.cms.swing_ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;


public class DocumentUIFrame extends BaseFrame {
    private static DocumentUIFrame instance;
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    public static final int COLUMNS = 3;
    public static final int ICON_SIZE = 20;
    public static final int PADDING_SIZE = 10;

    public DocumentUIFrame() {
        super("Documents");
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
        panel.setPreferredSize(new Dimension(WIDTH, (int) (HEIGHT * 0.1)));
        panel.setBackground(Color.GRAY);

        this.add(panel, BorderLayout.NORTH);
    }

    private void initializeMainPanel() {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(WIDTH, (int) (HEIGHT * 0.9)));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(PADDING_SIZE, PADDING_SIZE, PADDING_SIZE, PADDING_SIZE));

        panel.add(createDocumentPanel());

        this.add(panel, BorderLayout.CENTER);
    }

    private JPanel createDocumentPanel() {
        JPanel documentPanel = new JPanel();
        documentPanel.setBackground(Color.GRAY);
        documentPanel.setBorder(new RoundedBorder(PADDING_SIZE));
        documentPanel.setLayout(new BorderLayout());

        JPanel titlePanel = new JPanel();
        titlePanel.setPreferredSize(new Dimension((int)(WIDTH * 0.9), 50));
        titlePanel.setBackground(Color.GRAY);
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

        JPanel tblPanel = new JPanel();
        tblPanel.setPreferredSize(new Dimension((int)(WIDTH * 0.9), 300));
        tblPanel.setBackground(Color.GRAY);
        tblPanel.setLayout(new BoxLayout(tblPanel, BoxLayout.Y_AXIS));
        tblPanel.setBackground(new Color(255, 255, 255, 0));

        tblPanel.add(createTablePanel());

        documentPanel.add(tblPanel, BorderLayout.CENTER);

        return documentPanel;

    }

    private JPanel createTablePanel() {
        String[] tableColumnHeaders = { "Student ID", "Student Name", "Document", "View", "Approve", "Reject"};

        JPanel TablePanel = new JPanel();
        TablePanel.setPreferredSize(new Dimension(WIDTH - 60, 100));
        TablePanel.setLayout(new BorderLayout());
        TablePanel.setBackground(new Color(255, 255, 255, 0));

        JPanel titlePanel = new JPanel();
        titlePanel.setPreferredSize(new Dimension(WIDTH - 60, 30));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(PADDING_SIZE, 60, 5, PADDING_SIZE));
        titlePanel.setLayout(new GridLayout(1, tableColumnHeaders.length, 5 ,5));
        titlePanel.setBackground(new Color(255, 255, 255, 100));

        for (String tableHeader:tableColumnHeaders) {
            TextLabel header = new TextLabel(tableHeader, 15);
            header.setForeground(Color.WHITE);
            header.setAlignmentX(Component.CENTER_ALIGNMENT);
            titlePanel.add(header);
        }

        TablePanel.add(titlePanel, BorderLayout.NORTH);

        JPanel rowPanel = new JPanel();
        rowPanel.setPreferredSize(new Dimension(WIDTH - 60, 70));
        rowPanel.setLayout(new BoxLayout(rowPanel, BoxLayout.Y_AXIS));
        rowPanel.setBackground(new Color(255, 255, 255, 100));
        rowPanel.setBorder(BorderFactory.createEmptyBorder(PADDING_SIZE, PADDING_SIZE, PADDING_SIZE, PADDING_SIZE));

        rowPanel.add(createRow("test", "test", "test"));
        rowPanel.add(createRow("test", "test", "test"));
        rowPanel.add(createRow("test", "test", "test"));
        rowPanel.add(createRow("test", "test", "test"));
        rowPanel.add(createRow("test", "test", "test"));
        rowPanel.add(createRow("test", "test", "test"));
        rowPanel.add(createRow("test", "test", "test"));
        rowPanel.add(createRow("test", "test", "test"));

        TablePanel.add(rowPanel, BorderLayout.CENTER);

        return TablePanel;

    }

    private JPanel createRow(String studentID, String studentName, String document) {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(WIDTH - 55, 30));
        panel.setLayout(new GridLayout(1, 6, 5 ,5));
        panel.setBackground(Color.WHITE);
        panel.setBorder(new RoundedBorder(10));

        panel.add(new TextLabel(studentID, 20));
        panel.add(new TextLabel(studentName, 20));
        panel.add(new TextLabel(document, 20));
        panel.add(new CustomButton("view", 20, 50, PADDING_SIZE, PADDING_SIZE));
        panel.add(new CustomButton("approve", 20, 50, PADDING_SIZE, PADDING_SIZE));
        panel.add(new CustomButton("reject", 20, 50, PADDING_SIZE, PADDING_SIZE));


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
