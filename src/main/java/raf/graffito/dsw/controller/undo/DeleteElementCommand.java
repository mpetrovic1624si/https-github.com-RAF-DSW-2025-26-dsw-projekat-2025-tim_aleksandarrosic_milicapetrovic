package raf.graffito.dsw.controller.undo;


import app.model.SlideElement;

import java.util.List;

public class DeleteElementCommand implements Command {

    private model.Slide slide;
    private List<SlideElement> elements;

    public DeleteElementCommand(model.Slide slide, List<SlideElement> elements) {
        this.slide = slide;
        this.elements = elements;
    }

    @Override
    public void execute() {
        slide.removeElements(elements);
    }

    @Override
    public void undo() {
        slide.addElements(elements);
    }
}
