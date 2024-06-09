package com.nu_dasma.cms.swing_ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.File;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import com.nu_dasma.cms.model.BorrowedItem;
import com.nu_dasma.cms.model.Document;
import com.nu_dasma.cms.model.Student;
import com.nu_dasma.cms.Database;

public class StudentUIFrame extends BaseFrame {
    private static StudentUIFrame instance;

    public static final int WIDTH = 400;
    public static final int HEIGHT = 600;

    private Database db;
    private Student user;

    public StudentUIFrame() {
        super("CMS Student Home");

        this.setLayout(new BorderLayout());
        this.setSize(WIDTH, HEIGHT);
        this.setBackground(Color.WHITE);

        this.initializeUser();
        this.initializeTitlePanel();
        this.initializeMainPanel();


        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
    }

    private void initializeUser() {
        try {
            this.db = Database.getInstance();
            this.user = new Student(this.db.connection, 3);
        } catch (SQLException e) {
            System.err.println("Read error: " + e.getMessage());
        }
    }

    private void initializeTitlePanel() {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(WIDTH, (int) (HEIGHT * 0.1)));
        panel.setBackground(Color.GRAY);
        this.add(panel, BorderLayout.NORTH);
    }

    private void initializeMainPanel() {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(WIDTH, (int) (HEIGHT * 1.6)));
        panel.setMinimumSize(new Dimension(WIDTH, (int) (HEIGHT * 1.6)));

        panel.setLayout(null);
        panel.setBackground(Color.WHITE);

        panel.add(this.createStudentInfoPanel());
        panel.add(this.createMatriculationPanel());
        panel.add(this.createFinancialPanel());
        panel.add(this.createMiscellanousPanel());

        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        this.add(scrollPane, BorderLayout.CENTER);
    }

    private JPanel createStudentInfoPanel() {
        JPanel panel = new JPanel();

        int imageSize = (int) (HEIGHT * 0.17);

        int headerX = (int) (imageSize + (WIDTH * 0.025));
        int headerY = (int) (HEIGHT * 0.017);
        int labelHeight = 20;
        int fontSize = 10;
        int padding = 5;

        panel.setLayout(null);
        panel.setBounds(padding, padding, WIDTH - (padding * 2), imageSize);
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(padding, padding, padding, padding));

        ImageLabel image = new ImageLabel("dummyImage.png", imageSize, imageSize);
        image.setBounds(0, 0, imageSize, imageSize);
        panel.add(image);

        TextLabel fullNameLabel = new TextLabel(this.user.getFullName(), 20);
        fullNameLabel.setBounds(headerX, headerY, WIDTH - headerX, 35);
        panel.add(fullNameLabel);

        Map<String, TextLabel> map = new Hashtable<>();
        map.put("ID Number:", new TextLabel(String.valueOf(this.user.studentID), fontSize));
        map.put("Email:", new TextLabel(this.user.email, fontSize));

        headerY += 10;
        for (Map.Entry<String, TextLabel> entry : map.entrySet()) {
            String keyString = entry.getKey();
            TextLabel key = new TextLabel(keyString, fontSize + 2);
            TextLabel value = entry.getValue();

            headerY += labelHeight;
            int valueX = headerX + (int) key.getPreferredSize().getWidth() + 10;
            value.setBounds(valueX, headerY, WIDTH - valueX, labelHeight);
            key.setBounds(headerX, headerY, WIDTH - headerX, labelHeight);

            panel.add(key);
            panel.add(value);
        }

        return panel;
    }

    private JPanel createMatriculationPanel() {
        JPanel panel = new JPanel();

        int padding = 5;
        int width = (int) (WIDTH * 0.9);
        int previousPanelHeight = (int) (HEIGHT * 0.19);
        int height = (int) (HEIGHT * 0.40);

        panel.setLayout(new BorderLayout());
        panel.setBounds(padding, padding + previousPanelHeight, width, height);
        panel.setBackground(Color.WHITE);
        panel.setBorder(new RoundedBorder(10));

        // add title
        JPanel title = new JPanel();
        title.setLayout(new BoxLayout (title, BoxLayout.Y_AXIS));
        title.setBackground(Color.WHITE);
        title.setPreferredSize(new Dimension((int) (WIDTH * 0.9), (int)(250 * 0.2)));

        TextLabel label = new TextLabel("Requirements", 20);
        title.add(label);

        JSeparator separator = new JSeparator();
        separator.setOrientation(SwingConstants.HORIZONTAL);
        separator.setForeground(Color.BLACK);
        title.add(separator);

        panel.add(title, BorderLayout.NORTH);

        // add grid
        JPanel main = new JPanel();
        ArrayList<Document> documents = this.db.getStudentDocuments(this.user.studentID);
        main.setPreferredSize(new Dimension((int) (WIDTH * 0.9), (int)(250 * 0.8)));
        main.setLayout(null);
        main.setBackground(Color.WHITE);

        int componentHeight = (int) (main.getPreferredSize().getHeight() / (documents.size() + 1));
        for (int i = 0; i < documents.size(); i++) {
            int x = 5;
            int y = i * componentHeight;

            Document document = documents.get(i);
            TextLabel requirement = new TextLabel(document.name, 12);
            requirement.setBounds(x, y, 200, componentHeight);
            main.add(requirement);

            int buttonWidth = 50;
            int buttonHeight = 20;
            CustomButton submit = new CustomButton("submit", buttonWidth, buttonHeight, 5, 5);
            x += (int) requirement.getPreferredSize().getWidth() + 15;
            submit.setBounds(x, y + 5, buttonWidth, buttonHeight);
            submit.setBackground(Color.GRAY);
            submit.setForeground(Color.WHITE);
            submit.addActionListener(e -> {
                StudentUIFrame frame = StudentUIFrame.getInstance();
                String srcAbsolutePath = frame.chooseFile();

                if (srcAbsolutePath == null) {
                    return;
                }

                frame.db.uploadStudentDocument(frame.user.studentID, document.type, srcAbsolutePath);
            });

            main.add(submit);

            CustomButton view = new CustomButton("view", buttonWidth, buttonHeight, 5, 5);
            view.setBackground(Color.GRAY);
            view.setForeground(Color.WHITE);
            view.addActionListener(e -> {
                StudentUIFrame frame = StudentUIFrame.getInstance();
                try {
                    frame.db.viewStudentDocument(frame.user.studentID, document.type);
                } catch (SQLException err) {
                    JOptionPane.showMessageDialog(null, err.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            x += (int) submit.getPreferredSize().getWidth() + 5;
            view.setBounds(x, y + 5, buttonWidth, buttonHeight);
            main.add(view);
        }

        panel.add(main, BorderLayout.CENTER);

        return panel;
    }

    private String chooseFile() {
        JFileChooser fileChooser = new JFileChooser();

        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            return selectedFile.getAbsolutePath();
        }

        return null;
    }

    private JPanel createFinancialPanel() {
        JPanel panel = new JPanel();
        panel.setBounds(5, (int) (HEIGHT * 0.62), (int) (WIDTH * 0.9), 200);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(new RoundedBorder(10));

        panel.add(new TextLabel("Student Ledger", 20));
        JSeparator separator = new JSeparator();
        separator.setOrientation(SwingConstants.HORIZONTAL);
        separator.setForeground(Color.BLACK);
        panel.add(separator);

        TextLabel totalTuitionFee = new TextLabel(String.valueOf(this.user.tuitionFee), 20);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(new TextLabel("Total Tuition Fee: ", 10));
        panel.add(totalTuitionFee);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        TextLabel paidAmount = new TextLabel(String.valueOf(this.user.paidAmount), 20);
        panel.add(new TextLabel("Amount Paid: ", 10));
        panel.add(paidAmount);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        String balance = "CLEARED";
        if (this.user.paidAmount < this.user.tuitionFee) {
            balance = String.valueOf(this.user.tuitionFee - this.user.paidAmount);
        }
        TextLabel balanceAmount = new TextLabel(balance, 40);
        panel.add(new TextLabel("Remaining Balance:", 10));
        panel.add(balanceAmount);

        return panel;
    }

    private JPanel createMiscellanousPanel() {
        JPanel panel = new JPanel();

        int padding = 5;
        int width = (int) (WIDTH * 0.9);
        int previousPanelHeight = (int) (HEIGHT * 0.98);
        int height = (int) (HEIGHT * 0.55);

        panel.setLayout(new BorderLayout());
        panel.setBounds(padding, padding + previousPanelHeight, width, height);
        panel.setBackground(Color.WHITE);
        panel.setBorder(new RoundedBorder(10));

        // add title
        JPanel title = new JPanel();
        title.setLayout(new BoxLayout (title, BoxLayout.Y_AXIS));
        title.setBackground(Color.WHITE);
        title.setPreferredSize(new Dimension((int) (WIDTH * 0.9), (int) (height * 0.1)));

        TextLabel label = new TextLabel("Student Inventory", 20);
        title.add(label);

        JSeparator separator = new JSeparator();
        separator.setOrientation(SwingConstants.HORIZONTAL);
        separator.setForeground(Color.BLACK);
        title.add(separator);

        panel.add(title, BorderLayout.NORTH);

        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[] {"Item Name", "Due Date", "Total Penalty"});

        ArrayList<BorrowedItem> items = this.db.getStudentBorrowedItems(this.user.studentID);
        for (BorrowedItem item : items) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Object[] row = {item.itemName, formatter.format(item.dueDate), item.getPenalty()};
            model.addRow(row);
        }

        JTable table = new JTable();
        table.setModel(model);
        table.getTableHeader().setReorderingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setEnabled(false);

        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
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
