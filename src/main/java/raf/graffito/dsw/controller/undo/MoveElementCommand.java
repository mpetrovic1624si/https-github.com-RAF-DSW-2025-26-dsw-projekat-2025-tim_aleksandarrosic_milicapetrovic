package raf.graffito.dsw.controller.undo;

import app.model.SlideElement;

import java.util.HashMap;
import java.util.Map;

public class MoveElementCommand implements Command {

    private Map<SlideElement, int[]> oldPositions;
    private Map<SlideElement, int[]> newPositions;

    public MoveElementCommand(Map<SlideElement, int[]> oldPositions,
                              Map<SlideElement, int[]> newPositions) {
        this.oldPositions = oldPositions;
        this.newPositions = newPositions;
    }

    @Override
    public void execute() {
        for (Map.Entry<SlideElement, int[]> entry : newPositions.entrySet()) {
            int[] pos = entry.getValue();
            SlideElement el = entry.getKey();
            el.setX(pos[0]);
            el.setY(pos[1]);
        }
    }

    @Override
    public void undo() {
        for (Map.Entry<SlideElement, int[]> entry : oldPositions.entrySet()) {
            int[] pos = entry.getValue();
            SlideElement el = entry.getKey();
            el.setX(pos[0]);
            el.setY(pos[1]);
        }
    }
}