package com.company;
import javax.swing.*;
import java.awt.*;

public abstract class Input extends JPanel {
    public JLabel header;
    protected JComponent inputComponent;

    public Input(final String header, JComponent inputComponent) {
        this.header = new JLabel(header, JLabel.CENTER);
        this.header.setVerticalAlignment(JLabel.BOTTOM);
        this.inputComponent = inputComponent;
        setLayout(new BorderLayout());
        add(this.header, BorderLayout.PAGE_START);
        add(this.inputComponent, BorderLayout.CENTER);
    }
}