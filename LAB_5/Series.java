package com;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.io.FileWriter;



public abstract class Series
{
    protected int length;

    public abstract double Calculate_j_elem(int j);


    public double Sum()
    {
        double sum = 0;
        for (int i = 0; i < length; i++)
        {
            sum += Calculate_j_elem(i);
        }
        return sum;
    };


    public String toString()
    {
        String str = "";
        for (int i = 0; i < length; i++)
        {
            str += Calculate_j_elem(i) + " ";
        }
        return str;
    };

    public void SaveToFile(String str) throws FileNotFoundException
    {
        try
        {
            FileWriter writer = new FileWriter(str);
            writer.write(this.toString() + "\nSUM = " + this.Sum());
            writer.close();
        }
        catch(Exception e)
        {
            System.out.println("Error");
            System.exit(0);
        }
    };
}
