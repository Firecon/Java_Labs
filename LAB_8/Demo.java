package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;

public class Demo extends JFrame {
    public Demo() {
        super("Demo");
        var p = new JTabbedPane();
        p.addTab("1", new First(
                new Object[]{1, 2, "wasd", 4, 5.0, 6f, 1234567889, 121110987654321.0, true},
                new Object[]{0.1, "String", "Long String", false, false, 'c', getName()}));
        p.addTab("2", new Second(12, 12));
        p.addTab("3", new Third());
        add(p);
        pack();
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    public static class First extends JPanel {

        public First(Object[] leftObjects, Object[] rightObjects) {
            super(new BorderLayout());

            var lModel = new DefaultListModel<>();
            var rModel = new DefaultListModel<>();
            var l = new JList<>(lModel);
            var r = new JList<>(rModel);
            lModel.addAll(Arrays.asList(leftObjects));
            rModel.addAll(Arrays.asList(rightObjects));

            var c = new JPanel(new BorderLayout());
            var t = new JButton(">");
            var b = new JButton("<");
            bindListener(t, l, r, lModel, rModel);
            bindListener(b, r, l, rModel, lModel);

            add(new JScrollPane(l), BorderLayout.LINE_START);
            add(new JScrollPane(r), BorderLayout.LINE_END);
            c.add(t, BorderLayout.PAGE_START);
            c.add(b, BorderLayout.PAGE_END);
            add(c, BorderLayout.CENTER);
        }

        private void bindListener(JButton button,
                                  JList<Object> srcList,
                                  JList<Object> destList,
                                  DefaultListModel<Object> srcModel,
                                  DefaultListModel<Object> destModel) {
            button.addActionListener(a -> {
                var selected = srcList.getSelectedIndices();
                for (int i = 0; i < selected.length; ++i) {
                    destModel.addElement(srcModel.getElementAt(selected[i] - i));
                    srcModel.remove(selected[i] - i);
                }
            });
        }

    }

    public static class Second extends JPanel implements MouseListener {
        public Second(int rowCount, int columnCount) {
            super(new GridLayout(rowCount, columnCount));
            for (int i = 1, count = columnCount * rowCount; i <= count; ++i) {
                var b = new JButton(Integer.toString(i));
                b.setActionCommand(b.getText());
                b.addMouseListener(this);
                add(b);
            }
        }
        @Override
        public void mouseClicked(MouseEvent e) {}
        @Override
        public void mousePressed(MouseEvent e) {
            var b = (JButton) e.getSource();
            b.setText("Clicked!");
        }
        @Override
        public void mouseReleased(MouseEvent e) {
            var b = (JButton) e.getSource();
            b.setText(b.getActionCommand());
        }
        @Override
        public void mouseEntered(MouseEvent e) {
            var b = (JButton) e.getSource();
            var back = b.getBackground();
            b.setBackground(b.getForeground());
            b.setForeground(back);
        }
        @Override
        public void mouseExited(MouseEvent e) {
            var b = (JButton) e.getSource();
            var back = b.getBackground();
            b.setBackground(b.getForeground());
            b.setForeground(back);
        }
    }

    public static class Third extends JPanel {
        private static final Font font = new Font("Monospaced", Font.PLAIN, 12);
        private static final MyIcon[] icons = new MyIcon[]{
                new MyIcon(180, 180, new Point(10, 30), "Не выбрано.", font),
                new MyIcon(180, 180, new Point(10, 30), "Выбрано.", font),
                new MyIcon(180, 180, new Point(10, 30), "Мышь.", font),
                new MyIcon(180, 180, new Point(10, 30), "Мышь и удержание.", font),
        };
        public Third() {
            super(new BorderLayout());
            add(new JLabel(generateText(90, 30, "?")), BorderLayout.NORTH);
            var p = new JPanel(new GridLayout(15, 2));
            var bg = new ButtonGroup();
            for (int i = 0; i < 15; ++i) {
                var btn = new JRadioButton();
                btn.setIcon(icons[0]);
                btn.setSelectedIcon(icons[1]);
                btn.setRolloverIcon(icons[2]);
                btn.setPressedIcon(icons[3]);
                bg.add(btn);
                p.add(btn);
                p.add(new JLabel(generateText(35, 17,
                        Math.random() < 0.33 ? Math.random() < 0.63 ? "..." : "." : "!"), JLabel.CENTER));
            }
            add(new JScrollPane(p), BorderLayout.CENTER);
        }

        public static class MyIcon implements Icon {

            private final String text;
            private final int radius;
            private final int width;
            private final int height;
            private final Point textBox;
            private final Font font;

            public MyIcon(int width, int height, Point textBox, String text, Font font) {
                this.text = text;
                this.radius = Math.max(Math.min(width, height), 1);
                this.width = width;
                this.height = height;
                this.textBox = textBox;
                this.font = font;
            }
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                g.setColor(c.getForeground());
                for (int i = radius; i > 1; i -= 2) {
                    drawPoly(g, x + getIconWidth() / 2,
                            y + getIconHeight() / 2,
                            i,
                            (int) (Math.random() * (30 - 3)) + 3);
                }
                g.setColor(c.getBackground());
                drawCenteredString(g, x, y);
                g.dispose();
            }
            @Override
            public int getIconWidth() {
                return width;
            }
            @Override
            public int getIconHeight() {
                return height;
            }

            public void drawCenteredString(Graphics g, int x, int y) {
                FontMetrics metrics = g.getFontMetrics(font);
                int xCent = x + (width - metrics.stringWidth(text)) / 2;
                int yCent = y + ((height - metrics.getHeight()) / 2) + metrics.getAscent();
                g.setFont(font);
                var col = g.getColor();
                g.setColor(getInversed(col));
                g.fillOval(x + textBox.x, yCent - textBox.y, width - textBox.x * 2, textBox.y * 7 / 4);
                g.setColor(col);
                g.drawString(text, xCent, yCent);
            }

            public static void drawPoly(Graphics g, int xCent, int yCent, int radius, int n) {
                var x = new int[n];
                var y = new int[n];
                double angle = 2 * Math.PI / n, current = Math.PI / 2;
                for (int i = 0; i < n; ++i) {
                    x[i] = xCent - (int) (Math.cos(current) * radius);
                    y[i] = yCent - (int) (Math.sin(current) * radius);
                    current -= angle;
                }
                g.drawPolygon(x, y, n);

            }
        }

        public static String generateText(int mean, int radius, String suffix) {
            int n = (int) Math.max(mean + (Math.random() * 2 - 1) * radius, 2);
            var sb = new StringBuilder();
            sb.append((char) (Math.random() * ((int) 'Z' - (int) ('A')) + (int) 'A'));
            for (int i = 2, j = (int) (i + 4 + Math.random() * 3); i < n; ++i) {
                if (i >= j && j != n - 1) {
                    sb.append(' ');
                    j = (int) (i + 4 + Math.random() * 3);
                } else {
                    sb.append((char) (Math.random() * ((int) 'z' - (int) ('a')) + (int) 'a'));
                }
            }
            sb.append(suffix);
            return sb.toString();
        }

        public static Color getInversed(Color color) {
            return new Color(255 - color.getRed(), 255 - color.getGreen(), 255 - color.getBlue());
        }
    }
}