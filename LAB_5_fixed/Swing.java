package com;
import org.w3c.dom.css.CSSPrimitiveValue;
import org.w3c.dom.css.Rect;

import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.*;


public class Swing extends JFrame
{
    //Exponential ex = new Exponential(2, 0.5, 4);
   //Linear lin = new Linear(1, 1, 100);
            //System.out.println(lin.Sum());
    //System.out.println(lin.ToString());
    //System.out.println(ex.ToString());

            //lin.SaveToFile("FileName.txt");
            //ex.SaveToFile("FileName2.txt");

    private JButton but = new JButton("Save to file");
    private JButton but2 = new JButton("Run the programm");
    private JRadioButton r_but = new JRadioButton("Linear");
    private JRadioButton r_but2 = new JRadioButton("Exponential");
    private JLabel Filename = new JLabel("FileName");
    private JLabel Amount_of_elem = new JLabel("Amount of elements");
    private JLabel First_element = new JLabel("First element");
    private JLabel Difference = new JLabel("Difference");
    private JLabel lab = new JLabel();
    private JLabel error_lab = new JLabel();


    private JTextField text = new JTextField();
    private JTextField text2 = new JTextField();
    private JTextField text3 = new JTextField();
    private JTextField text4 = new JTextField();

    private JCheckBox check = new JCheckBox("Check", false);

    public Swing() {
        super("Progression");
        this.setBounds(500, 200, 500, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container container = this.getContentPane();

        container.setLayout(null);
        lab.setBounds(50, 20, 400, 80);
        container.add(lab);
        but.setBounds(250, 470, 100, 40);
        but2.setBounds(50, 470, 170, 40);

        error_lab.setBounds(50,20,100,20);


        container.add(error_lab);

        ButtonGroup group = new ButtonGroup();
        group.add(r_but);
        group.add(r_but2);
        r_but.setBounds(50, 90, 100, 40);
        r_but2.setBounds(50, 130, 100, 40);
        container.add(r_but);

        r_but.setSelected(true);
        container.add(r_but2);
        container.add(check);

        ActionListener actionListener = new ButtonEventListener();
        ActionListener actionListener2 = new ButtonEventListener2();

        but2.addActionListener(actionListener);
        but.addActionListener(actionListener2);

        container.add(but2);
        container.add(but);


        text.setBounds(250, 400, 150, 60);
        text2.setBounds(50, 400, 150, 60);
        text3.setBounds(50, 200, 150, 60);
        text4.setBounds(50, 300, 150, 60);

        container.add(text);
        container.add(text2);
        container.add(text3);
        container.add(text4);

        First_element.setBounds(50,180,150,20);
        Difference.setBounds(50,280,150,20);
        Filename.setBounds(250, 380, 150, 20);
        Amount_of_elem.setBounds(50, 380, 150, 20);

        container.add(Filename);
        container.add(Amount_of_elem);
        container.add(First_element);
        container.add(Difference);



    }

    public class ButtonEventListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {

            try {
                error_lab.setText("");
                String str = text2.getText();
                int len = Integer.parseInt(str);
                str = text3.getText();
                int first_elem = Integer.parseInt(str);
                str = text4.getText();
                int difference = Integer.parseInt(str);

                if (r_but.isSelected())// Linear
                {
                    Linear lin = new Linear(first_elem, difference, len);
                    lab.setText(lin.toString());
                } else //Exponentional
                {
                    Exponential ex = new Exponential(first_elem, difference, len);
                    lab.setText(ex.toString());
                }
            }
            catch (Exception exception)
            {
                error_lab.setText("Wrong input");
                text2.setText("");
                text3.setText("");
                text4.setText("");
            }
        }
    }

    public class ButtonEventListener2 implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            try
            {
                error_lab.setText("");
                String str = text2.getText();
                int len = Integer.parseInt(str);
                str = text3.getText();
                int first_elem = Integer.parseInt(str);
                str = text4.getText();
                int difference = Integer.parseInt(str);

            if (r_but.isSelected())// Linear
            {
                Linear lin = new Linear(first_elem, difference, len);
                str = text.getText();
                lin.SaveToFile(str);
            }
            else //Exponentional
            {
                Exponential ex = new Exponential(first_elem,difference,len);
                str = text.getText();
                ex.SaveToFile(str);
            }

            }
            catch(Exception exception)
            {
                error_lab.setText("Wrong input");
                text2.setText("");
                text3.setText("");
                text4.setText("");
                text.setText("");
                //System.exit(0);
            }
        }
    }
}
