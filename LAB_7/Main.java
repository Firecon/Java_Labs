package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        JFrame f = new JFrame("Demo");

        // add colors
        var colorPicker = new ColorPicker(Color.GRAY,
                Color.BLUE,
                Color.YELLOW
                //,Color.YELLOW
                );

        var drawArea = new DrawArea(colorPicker::getSelected);

        var ioHandler = new FilePicker(file -> drawArea.setImage(loadImage(file)),
                file -> saveImage(drawArea.getImage(), file));

        f.add(ioHandler, BorderLayout.PAGE_START);
        f.add(new JScrollPane(drawArea));
        f.add(colorPicker, BorderLayout.PAGE_END);

        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setSize(400, 400);
        f.setVisible(true);
    }

    static BufferedImage loadImage(File file) {
        try {
            return ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    static void saveImage(BufferedImage img, File file) {
        try {
            int formatStart = file.toString().lastIndexOf('.') + 1;
            String format;
            if (formatStart == 0) {
                format = "png";
                file = new File(file.toString() + ".png");
            } else {
                format = file.toString().substring(formatStart);
            }
            ImageIO.write(img, format, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
