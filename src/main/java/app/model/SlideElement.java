package app.model;

import lombok.Setter;

public class SlideElement implements Cloneable {
    protected int x, y;
    protected int width, height;
    protected double rotation;

    public SlideElement(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.rotation = 0;
    }

    public abstract SlideElement copy();

    // Getteri i setteri
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public double getRotation() {
        return rotation;
    }
}
}
