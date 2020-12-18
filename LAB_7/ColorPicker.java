package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ColorPicker extends JPanel implements ActionListener {
    private Color selected;

    public Color getSelected() {
        return selected;
    }

    public void setColors(Color... colors) {
        removeAll();
        var options = new String[colors.length];
        for (int i = 0; i < colors.length; ++i)
            options[i] = Integer.toString(colors[i].getRGB());
        var optionInput = new OptionInput("Выбор Цвета", this, false, options);
        add(optionInput, new GridBagConstraints());
    }

    public ColorPicker(Color... colors) {
        super(new GridBagLayout());
        setColors(colors);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        selected = new Color(Integer.parseInt(e.getActionCommand()));
    }
}
