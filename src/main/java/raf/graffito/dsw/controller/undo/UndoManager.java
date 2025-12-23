package raf.graffito.dsw.controller.undo;
import java.util.Scanner;
import java.util.Stack;

public class UndoManager {
    private Stack<Command> undoStack;
    private Stack<Command> redoStack;

    public UndoManager() {
        undoStack = new Stack<>();
        redoStack = new Stack<>();
    }

    public void executeCommand(Command cmd) {
        cmd.execute();
        undoStack.push(cmd);
        redoStack.clear();
    }

    public void undo() {
        if (!undoStack.isEmpty()) {
            Command cmd = undoStack.pop();
            cmd.undo();
            redoStack.push(cmd);
        }
    }

    public void redo() {
        if (!redoStack.isEmpty()) {
            Command cmd = redoStack.pop();
            cmd.execute();
            undoStack.push(cmd);
        }
    }

    public boolean canUndo() {
        return !undoStack.isEmpty();
    }

    public boolean canRedo() {
        return !redoStack.isEmpty();
    }

    public void clear() {
        undoStack.clear();
        redoStack.clear();
    }
}
