package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SecondTask extends Task {

    private final JButton normalBtn;
    private final JButton unfairBtn;

    public SecondTask() {
        super("Second Task", new BorderLayout(), 788, 328);
        setMinimumSize(new Dimension(788, 328));
        add(new JLabel("Устраивает ли вас размер стипендии?", JLabel.CENTER), BorderLayout.PAGE_START);
        var mainPanel = new JPanel(new GridLayout(1, 2));
        normalBtn = createBtn("Да", mainPanel);
        unfairBtn = createBtn("Нет", mainPanel);
        setUpNormalButton();
        setUpUnfairBtn();
        add(mainPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    private void setUpNormalButton() {
        normalBtn.addActionListener(e -> {
            if (e.getActionCommand().equals(normalBtn.getActionCommand())) {
                JOptionPane.showMessageDialog(this,
                        "Утешающее сообщение",
                        "Утешающее сообщение",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    private void setUpUnfairBtn() {
        unfairBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                var v = new Vector2(0, 1);
                moveBtnTo(unfairBtn, v.scaled(calcRadiusMaxMagnitude(unfairBtn, v)));
            }
        });
        unfairBtn.getParent().addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                updateBtnPos(unfairBtn, e);
            }
        });
    }

    private static void updateBtnPos(JButton btn, MouseEvent e) {
        var v = Vector2.dif(new Vector2(e.getPoint()),
                new Vector2(btn.getParent().getSize()).scaled(0.5));
        var radius = calcRadiusMaxMagnitude(btn, v);
        moveBtnTo(btn, v.snapsToZero()
                ? new Vector2(0, 1).scaled(radius)
                : v.scaled(-1 / v.getMagnitude()).scaled((radius - v.getMagnitude())));
    }

    private static int calcRadiusMaxMagnitude(JButton btn, Vector2 vec) {
        var direction = vec.scaled(1 / vec.getMagnitude());
        var halfSize = new Vector2(btn.getParent().getSize());
        var rawRadius =
                Math.sqrt(1 / (direction.getX() * direction.getX() / halfSize.getX() / halfSize.getX()
                        + direction.getY() * direction.getY() / halfSize.getY() / halfSize.getY()));
        rawRadius -= new Vector2(btn.getSize()).getMagnitude();
        return (int) Math.round(rawRadius);
    }

    private static void moveBtnTo(JButton btn, Vector2 pos) {
        var gbc = new GridBagConstraints();
        gbc.insets = new Insets(pos.getSnappedY(), pos.getSnappedX(), 0, 0);
        ((GridBagLayout) (btn.getParent().getLayout())).setConstraints(btn, gbc);
        btn.getParent().revalidate();
        btn.getParent().repaint();
    }

    private JButton createBtn(String name, Container container) {
        var panel = new JPanel(new GridBagLayout());
        var button = new JButton(name);
        panel.add(button, new GridBagConstraints());
        container.add(panel);
        return button;
    }
}
