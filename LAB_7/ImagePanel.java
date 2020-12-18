package com.company;
import javax.swing.*;
import java.awt.*;

class ImagePanel extends JPanel {

    private final int width;
    private final int height;
    private final Image bg;

    public ImagePanel(int width, int height, Image bg) {
        this.width = width;
        this.height = height;
        this.bg = bg;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        graphics.drawImage(bg, 0, 0, null);
    }
}