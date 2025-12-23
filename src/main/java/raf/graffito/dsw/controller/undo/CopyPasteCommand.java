package raf.graffito.dsw.controller.undo;
import model.Slide;
import app.model.SlideElement;

import app.model.SlideElement;

public class CopyPasteCommand implements Command{
    private model.Slide slide;
    private SlideElement pastedElement;

    public CopyPasteCommand(model.Slide slide, SlideElement original) {
        this.slide = slide;
        this.pastedElement = original.cloneElement();
    }

    @Override
    public void execute() {
        slide.addElement(pastedElement);
    }

    @Override
    public void undo() {
        slide.removeElement(pastedElement);
    }
}
