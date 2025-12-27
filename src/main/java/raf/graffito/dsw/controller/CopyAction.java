package raf.graffito.dsw.controller;

import app.model.SlideElement;
import model.Slide;

import java.util.ArrayList;
import java.util.List;

public class CopyAction {
    private static List<SlideElement> clipboard = new ArrayList<>();
    
    public static void copySelectedElements(Slide slide) {
        clipboard.clear();
        for (SlideElement el : slide.getElements()) {
            if (el.isSelected()) {
                clipboard.add(el);
            }
        }
    }
    
    public static List<SlideElement> getClipboard() {
        return new ArrayList<>(clipboard);
    }
    
    public static boolean hasClipboardContent() {
        return !clipboard.isEmpty();
    }
}

