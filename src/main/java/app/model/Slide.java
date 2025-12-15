package app.model;

import java.util.ArrayList;
import java.util.List;

public class Slide {
    private List<SlideElement> elements = new ArrayList<>();

    public void addElement(SlideElement element) {
        elements.add(element);
    }

    public List<SlideElement> getElements() {
        return elements;
    }
}
