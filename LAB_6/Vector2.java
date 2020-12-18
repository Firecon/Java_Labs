package com.company;

import java.awt.*;

public class Vector2 {
    private double x;
    private double y;

    public double getY() { return y; }
    public void setY(double y) { this.y = y; }
    public double getX() { return x; }
    public void setX(double x) { this.x = x; }

    public int getSnappedX() { return (int) Math.round(x); }
    public int getSnappedY() { return (int) Math.round(y); }

    public boolean snapsToZero() {
        return getSnappedX() == 0 && getSnappedY() == 0;
    }

    public double getMagnitude() {
        return Math.sqrt(x * x + y * y);
    }

    public Vector2(double x, double y) {
        setX(x);
        setY(y);
    }
    public Vector2(Point point) {
        setX(point.x);
        setY(point.y);
    }
    public Vector2(Dimension dimension) {
        setX(dimension.width);
        setY(dimension.height);
    }

    public Vector2 scaled(double scalar) {
        return new Vector2(x * scalar, y * scalar);
    }

    public static Vector2 dif(Vector2 a, Vector2 b) {
        return new Vector2(a.x - b.x, a.y - b.y);
    }
}
