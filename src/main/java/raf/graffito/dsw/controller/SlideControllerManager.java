package raf.graffito.dsw.controller;

import java.util.HashMap;
import java.util.Map;
import javax.swing.JTabbedPane;
import view.SlideView;

public class SlideControllerManager {
    private static SlideControllerManager instance;
    private Map<String, SlideController> controllerMap = new HashMap<>();
    private Map<String, SlideView> slideViewMap = new HashMap<>();
    private SlideController currentController;
    private SlideView currentSlideView;
    private JTabbedPane tabbedPane;

    private SlideControllerManager() {}

    public static SlideControllerManager getInstance() {
        if (instance == null) {
            instance = new SlideControllerManager();
        }
        return instance;
    }

    public void setTabbedPane(JTabbedPane tabbedPane) {
        this.tabbedPane = tabbedPane;
        if (tabbedPane != null) {
            tabbedPane.addChangeListener(e -> updateCurrentController());
            updateCurrentController();
        }
    }

    private void updateCurrentController() {
        if (tabbedPane == null || tabbedPane.getSelectedIndex() < 0) {
            currentController = null;
            currentSlideView = null;
            return;
        }
        String tabTitle = tabbedPane.getTitleAt(tabbedPane.getSelectedIndex());
        currentController = controllerMap.get(tabTitle);
        currentSlideView = slideViewMap.get(tabTitle);
    }

    public void registerController(String slideName, SlideController controller) {
        controllerMap.put(slideName, controller);
        updateCurrentController();
    }
    
    public void registerSlideView(String slideName, SlideView slideView) {
        slideViewMap.put(slideName, slideView);
        updateCurrentController();
    }
    
    public SlideView getCurrentSlideView() {
        return currentSlideView;
    }

    public void unregisterController(String slideName) {
        controllerMap.remove(slideName);
        updateCurrentController();
    }

    public SlideController getCurrentController() {
        return currentController;
    }
}

