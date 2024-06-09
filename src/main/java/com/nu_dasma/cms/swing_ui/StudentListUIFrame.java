
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

public class StudentListUIFrame extends BaseFrame {
    private static StudentListUIFrame instance;
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    public static final int COLUMNS = 3;
    public static final int ICON_SIZE = 20;
    public static final int PADDING_SIZE = 10;

    public StudentListUIFrame() {
        super("Student List");
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
        panel.add(createInventoryPanel());

        this.add(panel, BorderLayout.CENTER);
    }

    private JPanel createInventoryPanel() {
        JPanel studentPanel = new JPanel();
        studentPanel.setBounds(5, 635, (int) (WIDTH * 0.9), 350);
        studentPanel.setBackground(Color.GRAY);
        studentPanel.setBorder(new RoundedBorder(PADDING_SIZE));
        studentPanel.setLayout(null);
        studentPanel.setBackground(Color.GRAY);

        studentPanel.add(Box.createRigidArea(new Dimension(0, PADDING_SIZE)));
        ImageLabel studentIcon = new ImageLabel("studentIcon.png", ICON_SIZE, ICON_SIZE);
        studentIcon.setBounds(PADDING_SIZE, PADDING_SIZE, ICON_SIZE, ICON_SIZE);
        studentPanel.add(studentIcon);

        TextLabel studentLabel = new TextLabel("Student List", ICON_SIZE);
        studentLabel.setBounds(35, PADDING_SIZE, 300, ICON_SIZE);
        studentLabel.setForeground(Color.WHITE);
        studentPanel.add(studentLabel);

        studentPanel.add(Box.createRigidArea(new Dimension(0, PADDING_SIZE)));
        JSeparator separator = new JSeparator();
        separator.setOrientation(SwingConstants.HORIZONTAL);
        separator.setForeground(Color.WHITE);
        separator.setBounds(PADDING_SIZE, 35, WIDTH - 50, ICON_SIZE);
        studentPanel.add(separator);

        return studentPanel;

    }


    @Override
    public void dispose() {
        synchronized (StudentListUIFrame.class) {
            StudentListUIFrame.instance = null;
            super.dispose();
        }
    }

    public static StudentListUIFrame getInstance() {
        if (StudentListUIFrame.instance != null) {
            return StudentListUIFrame.instance;
        }

        synchronized (StudentListUIFrame.class) {
            StudentListUIFrame.instance = new StudentListUIFrame();
        }

        return StudentListUIFrame.instance;
    }
}
