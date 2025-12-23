package raf.graffito.dsw.controller.undo;
import app.model.SlideElement;
import model.Slide;


public class AddElementCommand implements Command {

    private Slide slide;
    private SlideElement element;

    public AddElementCommand(Slide slide, SlideElement element) {
        this.slide = slide;
        this.element = element;
    }

    @Override
    public void execute() {
        slide.addElement(element);
    }

    @Override
    public void undo() {
        slide.removeElement(element);
    }
}