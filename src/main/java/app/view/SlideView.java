package app.view;
import app.model.*;
import com.sun.java.swing.plaf.motif.MotifButtonUI;

import javax.swing.*;
        import java.awt.*;
        import java.awt.geom.AffineTransform;

public class SlideView extends JPanel {

    private Slide slide;
    private MotifButtonUI LogoPainter;

    public SlideView(Slide slide) {
        this.slide = slide;
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        for (SlideElement el : slide.getElements()) {
            AffineTransform old = g2.getTransform();

            g2.rotate(
                    Math.toRadians(el.getRotation()),
                    el.getX() + el.getWidth() / 2,
                    el.getY() + el.getHeight() / 2
            );

            if (el instanceof ImageElement img) {
                Image image = new ImageIcon(img.getImagePath()).getImage();
                g2.drawImage(image, el.getX(), el.getY(),
                        el.getWidth(), el.getHeight(), null);
            }

            if (el instanceof TextElement txt) {
                g2.setColor(Color.BLACK);
                g2.drawString(txt.getText(), el.getX(), el.getY() + 20);
            }

            if (el instanceof LogoElement logo) {
                LogoPainter.paint(g2, logo);
            }

            g2.setTransform(old);
        }
    }

    public void setLogoPainter(MotifButtonUI logoPainter) {
        LogoPainter = logoPainter;
    }
}
