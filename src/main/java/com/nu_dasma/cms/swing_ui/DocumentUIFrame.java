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
        documentPanel.setBounds(5, 635, (int) (WIDTH * 0.9), 350);
        documentPanel.setBackground(Color.GRAY);
        documentPanel.setBorder(new RoundedBorder(PADDING_SIZE));
        documentPanel.setLayout(null);
        documentPanel.setBackground(Color.GRAY);

        documentPanel.add(Box.createRigidArea(new Dimension(0, PADDING_SIZE)));
        ImageLabel documentIcon = new ImageLabel("documentIcon.png", ICON_SIZE, ICON_SIZE);
        documentIcon.setBounds(PADDING_SIZE, PADDING_SIZE, ICON_SIZE, ICON_SIZE);
        documentPanel.add(documentIcon);

        TextLabel documentLabel = new TextLabel("Documents", ICON_SIZE);
        documentLabel.setBounds(35, PADDING_SIZE, 300, ICON_SIZE);
        documentLabel.setForeground(Color.WHITE);
        documentPanel.add(documentLabel);

        documentPanel.add(Box.createRigidArea(new Dimension(0, PADDING_SIZE)));
        JSeparator separator = new JSeparator();
        separator.setOrientation(SwingConstants.HORIZONTAL);
        separator.setForeground(Color.WHITE);
        separator.setBounds(PADDING_SIZE, 35, WIDTH - 50, ICON_SIZE);
        documentPanel.add(separator);

        return documentPanel;

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
