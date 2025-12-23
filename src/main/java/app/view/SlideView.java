package view;


import app.model.SlideObserver;

import javax.swing.*;
import java.awt.*;

public class SlideView extends JPanel implements SlideObserver {

    private model.Slide slide;

    public SlideView(model.Slide slide) {
        this.slide = slide;
        slide.addObserver(this);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // ovde iscrtavanje svih elemenata iz slide.getElements()
    }

    @Override
    public void slideChanged() {
        repaint();
    }
}