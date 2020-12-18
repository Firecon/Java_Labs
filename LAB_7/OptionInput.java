package com.company;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class OptionInput extends Input {
    public OptionInput(final String header,
                       final ActionListener listener,
                       boolean isVerticalLayout,
                       final String[] options) {
        super(header, MakeInputComponent(listener, isVerticalLayout, options));
    }

    public static JPanel MakeInputComponent(final ActionListener listener,
                                            boolean isVerticalLayout,
                                            final String[] options) {
        var panel = new JPanel(isVerticalLayout
                                       ? new GridLayout(options.length, 1, 1, 1)
                                       : new GridLayout(1, options.length, 1, 1));
        var group = new ButtonGroup();
        for (var option : options) {
            var radioButton = new JRadioButton(option);
            radioButton.setForeground(new Color(Integer.parseInt(option)));
            radioButton.setActionCommand(option);
            panel.add(radioButton);
            group.add(radioButton);
            radioButton.addActionListener(listener);
        }
        group.setSelected(group.getElements().nextElement().getModel(), true);
        return panel;
    }
}