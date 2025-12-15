package raf.graffito.dsw.core.graff.composites;

import raf.graffito.dsw.core.graff.component.GraffNode;
import raf.graffito.dsw.core.graff.component.GraffNodeComposite;
import app.observer.Observer;

import java.util.ArrayList;
import java.util.List;

public class Project extends GraffNodeComposite {
    private String title;
    private String author;
    private int slideCount = 0;
    private int projectCount = 1; // itself counts as 1 project
    private final List<Observer> observers = new ArrayList<>();

    public Project(String title, GraffNode parent) {
        super(title, parent);
        this.title = title;
    }

    public String getTitle() { 
        return title; 
    }
    
    public String getAuthor() { 
        return author; 
    }
    
    public void setAuthor(String author) { 
        this.author = author; 
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
        if (parent instanceof Workspace) {
            ((Workspace) parent).updateCounts();
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
        return projectCount;
    }

    @Override
    public int getSlideCount() {
        return slideCount;
    }
}
