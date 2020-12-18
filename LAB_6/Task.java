package com.company;

import javax.swing.*;
import java.awt.*;

public class Task extends JFrame {
    public Task(String title, LayoutManager layout, int width, int height) {
        super(title);
        setLayout(layout);
        setSize(width, height);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }
}
