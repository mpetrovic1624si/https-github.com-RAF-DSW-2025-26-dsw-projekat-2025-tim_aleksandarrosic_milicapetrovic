package raf.graffito.dsw.controller;

import app.model.SlideElement;
import model.Slide;
import raf.graffito.dsw.controller.spacechecker.SpaceChecker;
import raf.graffito.dsw.controller.spacechecker.SumAreaSpaceChecker;
import raf.graffito.dsw.controller.spacechecker.PixelMatrixSpaceChecker;

public class SpaceCheckerManager {
    
    private static SpaceCheckerManager instance;
    private SpaceChecker currentChecker;
    
    private SpaceCheckerManager() {
        // Default: use SumAreaSpaceChecker
        this.currentChecker = new SumAreaSpaceChecker();
    }
    
    public static SpaceCheckerManager getInstance() {
        if (instance == null) {
            instance = new SpaceCheckerManager();
        }
        return instance;
    }
    
    public void setChecker(SpaceChecker checker) {
        this.currentChecker = checker;
    }
    
    public void setCheckerType(String type) {
        switch (type.toLowerCase()) {
            case "sum":
                this.currentChecker = new SumAreaSpaceChecker();
                break;
            case "pixel":
                this.currentChecker = new PixelMatrixSpaceChecker();
                break;
            default:
                this.currentChecker = new SumAreaSpaceChecker();
        }
    }
    
    public boolean hasEnoughSpace(Slide slide, SlideElement newElement) {
        return currentChecker.hasEnoughSpace(slide, newElement);
    }
}

