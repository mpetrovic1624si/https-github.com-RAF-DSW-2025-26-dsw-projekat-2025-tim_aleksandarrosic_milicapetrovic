package raf.graffito.dsw.controller.undo;

import app.model.SlideElement;

import java.util.Map;

public class RotateElementCommand implements Command {
    private Map<SlideElement, Double> oldRotations;
    private Map<SlideElement, Double> newRotations;

    public RotateElementCommand(Map<SlideElement, Double> oldRotations,
                                Map<SlideElement, Double> newRotations) {
        this.oldRotations = oldRotations;
        this.newRotations = newRotations;
    }

    @Override
    public void execute() {
        for (SlideElement el : newRotations.keySet()) {
            el.setRotation(newRotations.get(el));
        }
    }

    @Override
    public void undo() {
        for (SlideElement el : oldRotations.keySet()) {
            el.setRotation(oldRotations.get(el));
        }
    }
}
