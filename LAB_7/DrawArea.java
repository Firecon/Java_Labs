package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.function.Supplier;

public class DrawArea extends JPanel implements MouseMotionListener, MouseListener {
    private BufferedImage img;
    private Point prev;
    private Supplier<Color> colorSupplier;

    public void setColorSupplier(Supplier<Color> colorSupplier) {
        this.colorSupplier = colorSupplier;
    }

    public void setImage(BufferedImage img) {
        this.img = img;
        repaint();
    }

    public BufferedImage getImage() {
        return img;
    }

    public DrawArea(Supplier<Color> colorSupplier) {
        img = new BufferedImage(1920, 1920, BufferedImage.TYPE_INT_BGR);
        setColorSupplier(colorSupplier);
        setDoubleBuffered(true);
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(img.getWidth(), img.getHeight());
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponents(g);
        g.drawImage(img, 0, 0, this);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        prev = e.getPoint();
        var graphics = img.getGraphics();
        graphics.setColor(colorSupplier.get());
        graphics.fillOval(prev.x, prev.y, 1, 1);
        graphics.dispose();
        repaint(prev.x, prev.y, 1, 1);
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        prev = null;
    }
    @Override
    public void mouseDragged(MouseEvent e) {
        var cur = e.getPoint();
        var graphics = img.getGraphics();
        graphics.setColor(colorSupplier.get());
        graphics.drawLine(prev.x, prev.y, cur.x, cur.y);
        graphics.dispose();
        repaint(new Rect(prev, cur));
        prev = cur;
    }

    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
    @Override
    public void mouseMoved(MouseEvent e) {}

    private static class Rect extends Rectangle {
        public Rect(Point a, Point b) {
            super(MakeRectangle(a, b));
        }

        private static Rectangle MakeRectangle(Point a, Point b) {
            int width = b.x - a.x;
            int height = b.y - a.y;
            if (width > 0) {
                if (height > 0) {
                    return new Rectangle(a.x, a.y, 1 + width, 1 + height);
                } else {
                    return new Rectangle(a.x, b.y, 1 + width, 1 - height);
                }
            } else if (height > 0) {
                return new Rectangle(b.x, a.y, 1 - width, 1 + height);
            } else {
                return new Rectangle(b.x, b.y, 1 - width, 1 - height);
            }
        }
    }
}
