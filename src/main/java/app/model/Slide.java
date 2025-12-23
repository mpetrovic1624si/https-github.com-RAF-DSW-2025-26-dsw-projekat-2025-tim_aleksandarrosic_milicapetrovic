package model;

import observer.SlideObserver;
import java.util.ArrayList;
import java.util.List;

public class Slide {

    // ======================
    // PODACI
    // ======================
    private List<SlideElement> elements;
    private List<SlideObserver> observers;

    private boolean changed; // flag za promene

    // ======================
    // KONSTRUKTOR
    // ======================
    public Slide() {
        elements = new ArrayList<>();
        observers = new ArrayList<>();
        changed = false;
    }

    // ======================
    // RAD SA ELEMENTIMA
    // ======================
    public void addElement(SlideElement element) {
        elements.add(element);
        changed = true;
        notifyObservers();
    }

    public void removeElement(SlideElement element) {
        elements.remove(element);
        changed = true;
        notifyObservers();
    }

    public List<SlideElement> getElements() {
        return elements;
    }

    // Brisanje više elemenata
    public void removeElements(List<SlideElement> toRemove) {
        elements.removeAll(toRemove);
        changed = true;
        notifyObservers();
    }

    // Dodavanje više elemenata
    public void addElements(List<SlideElement> toAdd) {
        elements.addAll(toAdd);
        changed = true;
        notifyObservers();
    }

    // ======================
    // OBSERVER PATTERN
    // ======================
    public void addObserver(SlideObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(SlideObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers() {
        for (SlideObserver o : observers) {
            o.slideChanged();
        }
    }

    // ======================
    // SAVE / CHANGED
    // ======================
    public boolean isChanged() {
        return changed;
    }

    public void setChanged(boolean changed) {
        this.changed = changed;
    }
}