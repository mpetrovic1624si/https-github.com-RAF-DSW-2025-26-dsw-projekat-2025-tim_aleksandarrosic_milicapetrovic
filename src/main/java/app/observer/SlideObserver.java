package app.observer;

public interface SlideObserver {

    /**
     * Poziva se kada se promeni stanje slajda:
     * - dodavanje / brisanje elemenata
     * - pomeranje
     * - rotacija
     * - resize
     * - undo / redo
     */
    void slideChanged();
}