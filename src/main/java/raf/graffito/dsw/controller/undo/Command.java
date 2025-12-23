package raf.graffito.dsw.controller.undo;

public interface Command {
    void execute();
    void undo();
}
