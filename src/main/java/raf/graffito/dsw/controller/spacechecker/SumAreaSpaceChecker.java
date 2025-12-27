package raf.graffito.dsw.controller.spacechecker;

import app.model.SlideElement;
import model.Slide;

/**
 * Način 1: Sabiranje površine svih elemenata na slajdu, bez obzira na njihovo potencijalno preklapanje
 */
public class SumAreaSpaceChecker extends SpaceChecker {
    
    @Override
    public boolean hasEnoughSpace(Slide slide, SlideElement newElement) {
        double slideArea = getSlideArea(slide);
        double occupiedArea = getOccupiedArea(slide);
        double newElementArea = newElement.getWidth() * newElement.getHeight();
        
        double totalAfterAdd = occupiedArea + newElementArea;
        double freeArea = slideArea - totalAfterAdd;
        double freeRatio = freeArea / slideArea;
        
        return freeRatio >= MIN_FREE_SPACE_RATIO;
    }
}

