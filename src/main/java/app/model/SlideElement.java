package app.model;

import raf.graffito.dsw.controller.serializer.Serializer;

public abstract class SlideElement {

    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected double rotation;
    protected boolean selected;

    public SlideElement(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.rotation = 0;
        this.selected = false;
    }

    // OBAVEZNO za Copy/Paste
    public abstract SlideElement cloneElement();

    // Getteri
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

    public boolean isSelected() {
        return selected;
    }

    // Setteri
    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    // Rotacija elementa
    public void rotate(double deltaDegrees) {
        this.rotation += deltaDegrees;
    }
}