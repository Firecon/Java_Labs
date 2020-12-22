package com;

public class Linear extends Series
{
    private double num;
    private double dif;

    public Linear(double number, double diff, int len)
    {
        num = number;
        dif = diff;
        length = len;
    }

    public double Calculate_j_elem(int j)
    {
        //System.out.println(j + " elem of linear progression = " + (a +(d*j)));
        return (num+(dif*j));
    };
}
