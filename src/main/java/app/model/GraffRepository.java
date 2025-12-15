package app.model;

import app.observer.Observable;

public class GraffRepository extends Observable {
    // Primer metode koja obaveštava view-ove o promeni
    public void notifyChange(String message) {
        notifyObservers(message);
    }
}
