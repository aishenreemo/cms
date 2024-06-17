package com.nu_dasma.cms.swing_ui;

import java.awt.BorderLayout;
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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import com.nu_dasma.cms.Database;
import com.nu_dasma.cms.SwingApp;
import com.nu_dasma.cms.model.BorrowedItem;
import com.nu_dasma.cms.model.Document;
import com.nu_dasma.cms.model.Student;

public class StudentUIFrame extends BaseFrame {
    private static StudentUIFrame instance;

    public static final int WIDTH = 500;
    public static final int HEIGHT = 600;
    public static final int IMAGE_SIZE = (int) (HEIGHT * 0.17);

    private Database db;
    private Student user;
    private JLabel image;

    public StudentUIFrame() {
        super("CMS Student Home");

        this.setLayout(new BorderLayout());
        this.setSize(WIDTH, HEIGHT);
        this.setBackground(Palette.GOLDEN_YELLOW.getColor());

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

            int userID = 3;
            if (this.db.loggedInUser != null) {
                userID = this.db.loggedInUser.id;
            }
            this.user = new Student(this.db.connection, userID);

            Document idPicture = new Document(this.db.connection, "ID_PICTURE", this.db.getDocumentType("ID_PICTURE"), this.user.studentID);
            String defaultImagePath = "studentIcon.png";

            if (!idPicture.path.isEmpty()) {
                this.image = new AbsolutePathImageLabel(idPicture.path, IMAGE_SIZE, IMAGE_SIZE);
            } else {
                this.image = new ImageLabel(defaultImagePath, IMAGE_SIZE, IMAGE_SIZE);
            }

        } catch (SQLException e) {
            System.err.println("Read error: " + e.getMessage());
        }
    }

    private void initializeTitlePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setPreferredSize(new Dimension(WIDTH, (int) (HEIGHT * 0.1)));
        panel.setBackground(Palette.ROYAL_BLUE.getColor());

        ImageLabel titleIcon = new ImageLabel("NULogoStudents.png", 170, 50);
        titleIcon.setBounds(5, 5, 170, 50);
        panel.add(titleIcon);

        CustomButton logout = new CustomButton("logout", 50, 30, 10, 10);
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
        panel.setPreferredSize(new Dimension(WIDTH, (int) (HEIGHT * 1.7)));
        panel.setMinimumSize(new Dimension(WIDTH, (int) (HEIGHT * 1.7)));

        panel.setLayout(null);
        panel.setBackground(Palette.GOLDEN_YELLOW.getColor());

        panel.add(this.createStudentInfoPanel());
        panel.add(this.createMatriculationPanel());
        panel.add(this.createFinancialPanel());
        panel.add(this.createMiscellanousPanel());
        panel.add(this.createClearanceButton(user));

        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        this.add(scrollPane, BorderLayout.CENTER);
    }

    private JPanel createStudentInfoPanel() {
        JPanel panel = new JPanel();

        int headerX = (int) (IMAGE_SIZE + (WIDTH * 0.025));
        int headerY = (int) (HEIGHT * 0.017);
        int labelHeight = 20;
        int fontSize = 10;
        int padding = 5;

        panel.setLayout(null);
        panel.setBounds(padding, padding, WIDTH - (padding * 2), IMAGE_SIZE);
        panel.setBackground(Palette.GOLDEN_YELLOW.getColor());
        panel.setBorder(BorderFactory.createEmptyBorder(padding, padding, padding, padding));

        this.image.setBounds(0, 0, IMAGE_SIZE, IMAGE_SIZE);
        panel.add(this.image);

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
        panel.setBackground(Palette.ROYAL_BLUE.getColor());
        panel.setBorder(new RoundedBorder(10));

        // add title
        JPanel title = new JPanel();
        title.setLayout(new BoxLayout (title, BoxLayout.Y_AXIS));
        title.setBackground(Palette.ROYAL_BLUE.getColor());
        title.setPreferredSize(new Dimension((int) (WIDTH * 0.9), (int)(250 * 0.2)));

        TextLabel label = new TextLabel("Requirements", 20);
        label.setForeground(Palette.WHITE.getColor());
        title.add(label);

        JSeparator separator = new JSeparator();
        separator.setOrientation(SwingConstants.HORIZONTAL);
        separator.setForeground(Palette.WHITE.getColor());
        title.add(separator);

        panel.add(title, BorderLayout.NORTH);

        // add grid
        JPanel main = new JPanel();
        ArrayList<Document> documents = this.db.getStudentDocuments(this.user.studentID);
        main.setPreferredSize(new Dimension((int) (WIDTH * 0.9), (int)(250 * 0.8)));
        main.setLayout(null);
        main.setBackground(Palette.ROYAL_BLUE.getColor());

        int componentHeight = (int) (main.getPreferredSize().getHeight() / (documents.size() + 1));
        for (int i = 0; i < documents.size(); i++) {
            int x = 5;
            int y = i * componentHeight;

            Document document = documents.get(i);
            TextLabel requirement = new TextLabel(document.name, 12);
            requirement.setBounds(x, y, 200, componentHeight);
            requirement.setForeground(Palette.WHITE.getColor());
            main.add(requirement);

            int buttonWidth = 50;
            int buttonHeight = 20;

            x += 195;
            TextLabel status = new TextLabel(document.status.isEmpty() ? "MISSING" : document.status, 10);
            status.setBounds(x, y, 200, componentHeight);
            status.setForeground(Palette.WHITE.getColor());
            main.add(status);

            x += 60;
            CustomButton submit = new CustomButton("submit", buttonWidth, buttonHeight, 5, 5);
            submit.setBounds(x, y + 5, buttonWidth, buttonHeight);
            submit.setBackground(Palette.WHITE.getColor());
            submit.setForeground(Palette.ROYAL_BLUE.getColor());
            submit.addActionListener(e -> {
                StudentUIFrame frame = StudentUIFrame.getInstance();
                String srcAbsolutePath = frame.chooseFile();

                if (srcAbsolutePath == null) {
                    return;
                }

                frame.db.uploadStudentDocument(frame.user.studentID, document.type, srcAbsolutePath);
                status.setText("PENDING");
            });
            main.add(submit);

            x += (int) submit.getPreferredSize().getWidth() + 5;
            CustomButton view = new CustomButton("view", buttonWidth, buttonHeight, 5, 5);
            view.setBounds(x, y + 5, buttonWidth, buttonHeight);
            view.setBackground(Palette.WHITE.getColor());
            view.setForeground(Palette.ROYAL_BLUE.getColor());
            view.addActionListener(e -> {
                StudentUIFrame frame = StudentUIFrame.getInstance();
                try {
                    frame.db.viewStudentDocument(frame.user.studentID, document.type);
                } catch (SQLException err) {
                    JOptionPane.showMessageDialog(null, err.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
            main.add(view);

            x += (int) view.getPreferredSize().getWidth() + 5;
            CustomButton reset = new CustomButton("reset", buttonWidth, buttonHeight, 5, 5);
            reset.setBounds(x, y + 5, buttonWidth, buttonHeight);
            reset.setBackground(Palette.WHITE.getColor());
            reset.setForeground(Palette.ROYAL_BLUE.getColor());
            reset.addActionListener(e -> {
                StudentUIFrame frame = StudentUIFrame.getInstance();
                try {
                    frame.db.deleteStudentDocument(frame.user.studentID, document.type);
                    status.setText("MISSING");
                } catch (SQLException err) {
                    JOptionPane.showMessageDialog(null, err.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
            main.add(reset);
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
        panel.setBackground(Palette.ROYAL_BLUE.getColor());
        panel.setBorder(new RoundedBorder(10));

        TextLabel label = new TextLabel("Student Ledger", 20);
        label.setForeground(Palette.WHITE.getColor());
        panel.add(label);

        JSeparator separator = new JSeparator();
        separator.setOrientation(SwingConstants.HORIZONTAL);
        separator.setForeground(Palette.WHITE.getColor());
        panel.add(separator);

        TextLabel totalTuitionFee = new TextLabel(String.valueOf(this.user.tuitionFee), 20);
        totalTuitionFee.setForeground(Palette.WHITE.getColor());

        TextLabel tuitionLabel = new TextLabel("Total Tuition Fee: ", 10);
        tuitionLabel.setForeground(Palette.WHITE.getColor());

        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(tuitionLabel);
        panel.add(totalTuitionFee);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        TextLabel paidAmount = new TextLabel(String.valueOf(this.user.paidAmount), 20);
        paidAmount.setForeground(Palette.WHITE.getColor());

        TextLabel paidLabel = new TextLabel("Amount Paid: ", 10);
        paidLabel.setForeground(Palette.WHITE.getColor());

        panel.add(paidLabel);
        panel.add(paidAmount);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        String balance = "CLEARED";
        if (this.user.paidAmount < this.user.tuitionFee) {
            balance = String.valueOf(this.user.tuitionFee - this.user.paidAmount);
        }
        TextLabel balanceAmount = new TextLabel(balance, 40);
        balanceAmount.setForeground(Palette.WHITE.getColor());

        TextLabel balanceLabel = new TextLabel("Remaining Balance:", 10);
        balanceLabel.setForeground(Palette.WHITE.getColor());

        panel.add(balanceLabel);
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
        panel.setBackground(Palette.ROYAL_BLUE.getColor());
        panel.setBorder(new RoundedBorder(10));

        // add title
        JPanel title = new JPanel();
        title.setLayout(new BoxLayout (title, BoxLayout.Y_AXIS));
        title.setBackground(Palette.ROYAL_BLUE.getColor());
        title.setPreferredSize(new Dimension((int) (WIDTH * 0.9), (int) (height * 0.1)));

        TextLabel label = new TextLabel("Student Inventory", 20);
        label.setForeground(Palette.WHITE.getColor());
        title.add(label);

        JSeparator separator = new JSeparator();
        separator.setOrientation(SwingConstants.HORIZONTAL);
        separator.setForeground(Palette.ROYAL_BLUE.getColor());
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
        table.getColumnModel().getColumn(0).setPreferredWidth(200);
        table.getTableHeader().setReorderingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setEnabled(false);

        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }
    private CustomButton createClearanceButton(Student student) {
        double width = WIDTH * 0.9;
        double height = HEIGHT * 0.1 ;

        CustomButton button = new CustomButton("View Clearance",(int)(width), (int)(height), 10, 10);
        button.setVisible(false);
        button.setBounds(5, 930,(int) (width), (int) (height));
        button.setBackground(Palette.ROYAL_BLUE.getColor());
        button.setForeground(Palette.WHITE.getColor());

        button.addActionListener(e -> {
            SwingApp app = SwingApp.getInstance();
            app.ui.dispose();
            app.ui = ClearanceFrame.getInstance(this.user);
        });
        
        int progressAmount = (int) (student.getProgressPercentage() * 100);
        if (progressAmount > 99) {
            button.setVisible(true);
        }

        return button;
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
