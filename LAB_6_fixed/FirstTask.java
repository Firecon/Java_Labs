package com.company;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.function.Consumer;



public class FirstTask extends Task {

    private final JLabel statusBar;
    private final Canvas canvas;

    public FirstTask(Dimension buttonDimension) {
        super("First Task", new BorderLayout(), 560, 280);
        // setup status bar

        statusBar = new JLabel();
        statusBar.setText("0 / 0");
        add(statusBar, BorderLayout.SOUTH);
        // setup canvas

        canvas = new Canvas(buttonDimension);
        canvas.setOnMouseMoved(p -> statusBar.setText(String.format("(%s, %s)", p.getX(), p.getY())));
        //canvas.mouseExited(statusBar.setText("0"));
        add(canvas, BorderLayout.CENTER);
        setVisible(true);
    }

    private static class Canvas extends JPanel implements MouseListener, MouseMotionListener {

        private Consumer<Point> onMouseMoved = p -> {};
        private final Button button;
        private boolean flag = false;

        public Canvas(Dimension buttonDimension) {
            super(null);
            button = new Button(buttonDimension);
            add(button);
            addMouseListener(this);
            addMouseMotionListener(this);
        }
        public void setOnMouseMoved(Consumer<Point> onMouseMoved) {
            this.onMouseMoved = onMouseMoved == null ? p -> {} : onMouseMoved;
            button.setOnMouseMoved(p -> this.onMouseMoved.accept(
                    new Point(p.x + button.getX(), p.y + button.getY())));
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            onMouseMoved.accept(e.getPoint());
        }
        @Override
        public void mouseDragged(MouseEvent e) {
            onMouseMoved.accept(e.getPoint());
        }
        @Override
        public void mouseReleased(MouseEvent e) { }
        @Override
        public void mouseClicked(MouseEvent e) {
            button.setLocation(new Point(e.getX() - button.getWidth() / 2, e.getY() - button.getHeight() / 2));
        }
        @Override
        public void mousePressed(MouseEvent e) {}
        @Override
        public void mouseEntered(MouseEvent e) {}
        @Override
        public void mouseExited(MouseEvent e)
        {
            Point zero = new Point();
            zero.y = 0;
            zero.x = 0;
            onMouseMoved.accept(zero);
        }

        private static class Button extends JButton implements MouseMotionListener, KeyListener {
            private Consumer<Point> onMouseMoved = p -> {};
            public Button(Dimension size) {
                setSize(size);
                addMouseMotionListener(this);
                addKeyListener(this);
            }
            public void setOnMouseMoved(Consumer<Point> onMouseMoved) {
                this.onMouseMoved = onMouseMoved == null ? p -> {} : onMouseMoved;
            }

            @Override
            public void mouseMoved(MouseEvent e)
            {
                onMouseMoved.accept(e.getPoint());
            }
            @Override
            public void mouseDragged(MouseEvent e) {
                onMouseMoved.accept(e.getPoint());
                if (e.isControlDown()) {
                    setLocation(new Point(getX() + e.getX() - getWidth() / 2,
                            getY() + e.getY() - getHeight() / 2));
                }
            }
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() != KeyEvent.VK_BACK_SPACE) {
                    setText(getText() + e.getKeyChar());

                }
            }
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar()  == KeyEvent.VK_BACK_SPACE) {
                    String text = getText();
                    if (!text.isEmpty())
                    setText(getText().substring(0,getText().length()-1));
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        }
    }
}
