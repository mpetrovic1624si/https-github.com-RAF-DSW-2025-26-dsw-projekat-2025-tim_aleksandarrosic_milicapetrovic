package raf.graffito.dsw.controller.undo;

import app.model.SlideElement;

import java.util.Map;

public class ResizeElementCommand implements Command {
    private Map<SlideElement, int[]> oldSizes;
    private Map<SlideElement, int[]> newSizes;

    public ResizeElementCommand(Map<SlideElement, int[]> oldSizes,
                                Map<SlideElement, int[]> newSizes) {
        this.oldSizes = oldSizes;
        this.newSizes = newSizes;
    }

    @Override
    public void execute() {
        for (SlideElement el : newSizes.keySet()) {
            int[] size = newSizes.get(el);
            el.setWidth(size[0]);
            el.setHeight(size[1]);
        }
    }

    @Override
    public void undo() {
        for (SlideElement el : oldSizes.keySet()) {
            int[] size = oldSizes.get(el);
            el.setWidth(size[0]);
            el.setHeight(size[1]);
        }
    }
}
