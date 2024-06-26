package com.nu_dasma.cms.swing_ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import java.awt.Image;
import java.awt.Insets;
import java.io.File;
import java.io.InputStream;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import javax.swing.border.Border;

class TextLabel extends JLabel {
    TextLabel(String text, int size){
        super(text);
        this.setFont(new Font("Arial", Font.PLAIN, size));
        this.setForeground(Palette.ROYAL_BLUE.getColor());
    }
}

class CustomButton extends JButton {
    private int arcWidth;
    private int arcHeight;

    public CustomButton(String text, int width, int height, int arcWidth, int arcHeight) {
        super(text);
        this.arcWidth = arcWidth;
        this.arcHeight = arcHeight;
        this.setFont(new Font("Arial", Font.PLAIN, 10));
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        setContentAreaFilled(false);
        setBorder(null);
        setBackground(Color.GRAY);

        Dimension buttonSize = new Dimension(width, height);
        this.setPreferredSize(buttonSize);
        this.setMaximumSize(buttonSize);
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (getModel().isPressed()) {
            g.setColor(new Color(230, 230, 230));
        } else {
            g.setColor(getBackground());
        }
        g.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arcWidth, arcHeight);
        super.paintComponent(g);
    }
}

class RoundedBorder implements Border {

    private int radius;

    public RoundedBorder(int radius) {
        this.radius = radius;
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(this.radius + 1, this.radius + 1, this.radius + 2, this.radius + 1);
    }

    @Override
    public boolean isBorderOpaque() {
        return true;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        g.setColor(c.getForeground());
        g.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
    }
}

class AbsolutePathImageLabel extends JLabel {
    public AbsolutePathImageLabel(String filePath, int width, int height) {
        super();

        try {
            URL fileURL = new File(filePath).toURI().toURL();
            ImageIcon icon = new ImageIcon(fileURL);
            Image scaledImage = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);

            this.setIcon(new ImageIcon(scaledImage));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                null,
                "Error loading image: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
            System.out.println(e.getMessage());
        }
    }
}

class ImageLabel extends JLabel {
    public ImageLabel(String filePath, int width, int height) {
        super();

        try {
            ClassLoader classLoader = this.getClass().getClassLoader();
            InputStream stream = classLoader.getResourceAsStream(filePath);
            Image image = ImageIO.read(stream);
            Image scaledImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);

            this.setIcon(new ImageIcon(scaledImage));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                null,
                "Error loading image: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
}

enum Palette {
    BLACK("#131718"),
    WHITE("#FFFFFF"),
    ROYAL_BLUE("#36438A"),
    GOLDEN_YELLOW("#F8CF40");

    private String hexcode;
    private Palette(String hexcode) {
        this.hexcode = hexcode;
    }

    public Color getColor() {
        return Color.decode(this.hexcode);
    }
}
