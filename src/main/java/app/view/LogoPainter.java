package app.view;

import app.model.LogoElement;

import java.awt.*;

public class LogoPainter {

    public static void paint(Graphics2D g2, LogoElement logo) {
        g2.setColor(Color.BLUE);

        int x = logo.getX();
        int y = logo.getY();
        int w = logo.getWidth();
        int h = logo.getHeight();

        Polygon shape = new Polygon();
        shape.addPoint(x + w / 2, y);
        shape.addPoint(x + w, y + h);
        shape.addPoint(x, y + h);

        g2.fill(shape);
    }
}

