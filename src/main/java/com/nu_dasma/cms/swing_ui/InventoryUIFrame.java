package com.nu_dasma.cms.swing_ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.text.SimpleDateFormat;
import java.time.chrono.HijrahEra;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
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
import com.nu_dasma.cms.model.Item;

public class InventoryUIFrame extends BaseFrame {
    private static InventoryUIFrame instance;

    private Database db;
    private JPanel table;

    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    public static final int COLUMNS = 3;
    public static final int ICON_SIZE = 20;
    public static final int PADDING_SIZE = 10;

    public InventoryUIFrame() {
        super("Inventory");

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
        panel.setLayout(new FlowLayout(FlowLayout.TRAILING, PADDING_SIZE, PADDING_SIZE));
        panel.setPreferredSize(new Dimension(WIDTH, (int) (HEIGHT * 0.1)));
        panel.setBackground(Color.GRAY);

        CustomButton back = new CustomButton("Back", 50, 30, PADDING_SIZE, PADDING_SIZE);
        back.setBackground(Color.WHITE);
        back.setForeground(Color.GRAY);
        back.addActionListener(e -> {
            SwingApp app = SwingApp.getInstance();
            app.ui.dispose();
            app.ui = AdminUIFrame.getInstance();
        });
        panel.add(back);

        CustomButton logout = new CustomButton("Logout", 50, 30, PADDING_SIZE, PADDING_SIZE);
        logout.setBackground(Color.WHITE);
        logout.setForeground(Color.GRAY);
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
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(PADDING_SIZE, PADDING_SIZE, PADDING_SIZE, PADDING_SIZE));
        panel.add(createInventoryPanel());

        this.add(panel, BorderLayout.CENTER);
    }

    private JPanel createInventoryPanel() {
        JPanel inventoryPanel = new JPanel();
        inventoryPanel.setBackground(Color.GRAY);
        inventoryPanel.setBorder(new RoundedBorder(PADDING_SIZE));
        inventoryPanel.setLayout(new BorderLayout());

        JPanel titlePanel = new JPanel();
        titlePanel.setPreferredSize(new Dimension((int) (WIDTH * 0.9), 50));
        titlePanel.setBackground(Color.GRAY);
        titlePanel.setLayout(null);

        titlePanel.add(Box.createRigidArea(new Dimension(0, PADDING_SIZE)));
        ImageLabel inventoryIcon = new ImageLabel("inventoryIcon.png", ICON_SIZE, ICON_SIZE);
        inventoryIcon.setBounds(PADDING_SIZE, PADDING_SIZE, ICON_SIZE, ICON_SIZE);
        titlePanel.add(inventoryIcon);

        TextLabel studentLabel = new TextLabel("Inventory", ICON_SIZE);
        studentLabel.setBounds(35, PADDING_SIZE, 300, ICON_SIZE);
        studentLabel.setForeground(Color.WHITE);
        titlePanel.add(studentLabel);
        titlePanel.add(Box.createRigidArea(new Dimension(0, PADDING_SIZE)));

        JSeparator separator = new JSeparator();
        separator.setOrientation(SwingConstants.HORIZONTAL);
        separator.setForeground(Color.WHITE);
        separator.setBounds(PADDING_SIZE, 35, WIDTH - 50, ICON_SIZE);
        titlePanel.add(separator);

        inventoryPanel.add(titlePanel, BorderLayout.NORTH);

        this.table = new JPanel();
        this.table.setPreferredSize(new Dimension((int) (WIDTH * 0.9), 300));
        this.table.setBackground(Color.GRAY);
        this.table.setLayout(new BoxLayout(this.table, BoxLayout.Y_AXIS));
        this.table.setBackground(new Color(255, 255, 255, 0));

        this.table.add(this.createTablePanel());

        inventoryPanel.add(this.table, BorderLayout.CENTER);

        return inventoryPanel;

    }

    private JPanel createTablePanel() {
        String[] tableColumnHeaders = { "Item ID", "Item Name", "Action" };

        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(WIDTH - 60, 100));
        panel.setLayout(new BorderLayout(5, 5));
        panel.setBackground(new Color(255, 255, 255, 0));

        JPanel titlePanel = new JPanel();
        titlePanel.setPreferredSize(new Dimension((int) (WIDTH * 0.9), (int) (HEIGHT * 0.05)));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(PADDING_SIZE, 20, 5, PADDING_SIZE));
        titlePanel.setLayout(new GridLayout(1, tableColumnHeaders.length, 5, 5));
        titlePanel.setBackground(new Color(255, 255, 255, 100));

        for (String tableHeader : tableColumnHeaders) {
            TextLabel header = new TextLabel(tableHeader, 15);
            header.setForeground(Color.WHITE);
            header.setAlignmentX(Component.CENTER_ALIGNMENT);
            titlePanel.add(header);
        }

        panel.add(titlePanel, BorderLayout.NORTH);

        JPanel itemRowsPanel = new JPanel();
        itemRowsPanel.setLayout(new BoxLayout(itemRowsPanel, BoxLayout.Y_AXIS));
        itemRowsPanel.setBackground(new Color(255, 255, 255, 100));
        itemRowsPanel.setBorder(BorderFactory.createEmptyBorder(PADDING_SIZE, PADDING_SIZE, PADDING_SIZE, PADDING_SIZE));

        ArrayList<Item> items = this.db.getAllItems();
        for (Item item : items) {
            itemRowsPanel.add(this.createRow(item));
            itemRowsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        JScrollPane itemsScrollPane = new JScrollPane(itemRowsPanel);
        itemsScrollPane.setPreferredSize(new Dimension((int) (WIDTH * 0.9), (int) (HEIGHT * 0.65)));
        itemsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        itemsScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        panel.add(itemsScrollPane, BorderLayout.CENTER);

        DefaultTableModel borrowedItemsTableModel = new DefaultTableModel();
        borrowedItemsTableModel.setColumnIdentifiers(new String[] {
            "Item ID",
            "Item Name",
            "Student ID",
            "Due Date",
            "Total Penalty"
        });

        ArrayList<BorrowedItem> borrowedItems = this.db.getAllBorrowedItems();
        for (BorrowedItem item : borrowedItems) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Object[] row = {item.itemID, item.itemName, item.studentID, formatter.format(item.dueDate), item.getPenalty()};
            borrowedItemsTableModel.addRow(row);
        }

        JTable borrowedItemsTable = new JTable(borrowedItemsTableModel);
        borrowedItemsTable.getTableHeader().setReorderingAllowed(false);
        borrowedItemsTable.setEnabled(false);
        borrowedItemsTable.getColumnModel().getColumn(0).setPreferredWidth(40);
        borrowedItemsTable.getColumnModel().getColumn(1).setPreferredWidth(200);
        borrowedItemsTable.getColumnModel().getColumn(2).setPreferredWidth(40);
        borrowedItemsTable.getColumnModel().getColumn(3).setPreferredWidth(50);

        JScrollPane borrowedItemsScrollPane = new JScrollPane(borrowedItemsTable);
        borrowedItemsScrollPane.setPreferredSize(new Dimension((int) (WIDTH * 0.9), (int) (HEIGHT * 0.3)));
        borrowedItemsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        borrowedItemsScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        panel.add(borrowedItemsScrollPane, BorderLayout.SOUTH);
        
        return panel;
    }

    private JPanel createRow(Item item) {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension((int) (WIDTH * 0.9), 50));
        panel.setMaximumSize(new Dimension((int) (WIDTH * 0.9), 50));
        panel.setLayout(new GridLayout(1, 6, 5, 5));
        panel.setBackground(Color.WHITE);
        panel.setBorder(new RoundedBorder(10));

        panel.add(new TextLabel(String.valueOf(item.id), 12));
        panel.add(new TextLabel(item.name, 12));

        CustomButton button = new CustomButton("", 20, 50, PADDING_SIZE, PADDING_SIZE);
        if (item.isBorrowed) {
            button.setText("Return");
            button.setForeground(Palette.BLACK.getColor());
            button.setBackground(Palette.GOLDEN_YELLOW.getColor());
            button.addActionListener(e -> {
                InventoryUIFrame frame = InventoryUIFrame.getInstance();
                frame.db.returnItem(item.id);
                frame.table.remove(frame.table.getComponentCount() - 1);
                frame.table.add(frame.createTablePanel());
                frame.revalidate();
                frame.repaint();
                JOptionPane.showMessageDialog(null, "Item returned successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            });
        } else {
            button.setText("Borrow");
            button.setForeground(Palette.WHITE.getColor());
            button.setBackground(Palette.ROYAL_BLUE.getColor());
            button.addActionListener(e -> {
                InventoryUIFrame frame = InventoryUIFrame.getInstance();
                try {
                    String studentID = JOptionPane.showInputDialog(null, "Enter Student ID:", "CMS Student ID Input", JOptionPane.QUESTION_MESSAGE);
                    frame.db.borrowItemByStudent(item.id, Integer.parseInt(studentID));
                } catch (NumberFormatException err) {
                    JOptionPane.showMessageDialog(null, err.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }

                frame.table.remove(frame.table.getComponentCount() - 1);
                frame.table.add(frame.createTablePanel());
                frame.revalidate();
                frame.repaint();
                JOptionPane.showMessageDialog(null, "Item borrowed successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            });
        }

        panel.add(button);
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        return panel;
    }

    @Override
    public void dispose() {
        synchronized (InventoryUIFrame.class) {
            InventoryUIFrame.instance = null;
            super.dispose();
        }
    }

    public static InventoryUIFrame getInstance() {
        if (InventoryUIFrame.instance != null) {
            return InventoryUIFrame.instance;
        }

        synchronized (InventoryUIFrame.class) {
            InventoryUIFrame.instance = new InventoryUIFrame();
        }

        return InventoryUIFrame.instance;
    }
}
