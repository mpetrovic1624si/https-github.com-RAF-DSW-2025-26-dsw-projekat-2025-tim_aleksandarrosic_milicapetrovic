package raf.graffito.dsw.controller;

public class ActionManager {
    private final ExitAction exitAction;
    private final AboutUsAction aboutUsAction;

    public ActionManager() {
        exitAction = new ExitAction();
        aboutUsAction = new AboutUsAction();
    }

    public ExitAction getExitAction() {
        return exitAction;
    }

    public AboutUsAction getAboutUsAction() {
        return aboutUsAction;
    }
}
