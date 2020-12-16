package com;

public class Exponential extends Series
{
    private double num;
    private double dif;

    public Exponential(double number, double differ, int len)
    {
        num = number;
        dif = differ;
        length = len;
    }


    public double Calculate_j_elem(int j)
    {
        //System.out.println(j +" elem of exponential progression = " + a*(Math.pow(d,j)));
        return (num*(Math.pow(dif,j)));
    };

}
