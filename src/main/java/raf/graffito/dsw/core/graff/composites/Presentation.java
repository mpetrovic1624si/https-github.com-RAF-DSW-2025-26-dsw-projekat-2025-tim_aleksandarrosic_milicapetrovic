package raf.graffito.dsw.core.graff.composites;

import raf.graffito.dsw.core.graff.component.GraffNode;
import raf.graffito.dsw.core.graff.component.GraffNodeComposite;
import app.observer.Observer;

import java.util.ArrayList;
import java.util.List;

public class Presentation extends GraffNodeComposite {
    private String author;
    private int slideCount = 0;
    private final List<Observer> observers = new ArrayList<>();

    public Presentation(String name, GraffNode parent) {
        super(name, parent);
    }

    public void addObserver(Observer o) { 
        observers.add(o); 
    }
    
    public void removeObserver(Observer o) { 
        observers.remove(o); 
    }
    
    public void notifyObservers(Object arg) {
        for (Observer o : observers) {
            o.update(arg);
        }
    }

    @Override
    protected void updateCounts() {
        int total = 0;
        for (GraffNode child : children) {
            total += child.getSlideCount();
        }
        this.slideCount = total;
        if (parent instanceof Project) {
            ((Project) parent).updateCounts();
        }
        notifyObservers("UPDATED");
    }

    @Override
    public void removeChild(GraffNode child) {
        super.removeChild(child);
        notifyObservers("CHILD_REMOVED");
    }

    @Override
    public int getProjectCount() { 
        return 0; 
    }

    @Override
    public int getSlideCount() { 
        return slideCount; 
    }
}
