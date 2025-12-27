package raf.graffito.dsw.controller.spacechecker;

import app.model.SlideElement;
import model.Slide;

public abstract class SpaceChecker {
    
    protected static final double MIN_FREE_SPACE_RATIO = 0.20; // 20%
    
    public abstract boolean hasEnoughSpace(Slide slide, SlideElement newElement);
    
    protected double getSlideArea(Slide slide) {
        // Slide dimensions - should be configurable
        return 800 * 600; // SLIDE_WIDTH * SLIDE_HEIGHT
    }
    
    protected double getOccupiedArea(Slide slide) {
        double total = 0;
        for (SlideElement el : slide.getElements()) {
            total += el.getWidth() * el.getHeight();
        }
        return total;
    }
}

