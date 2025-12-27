package raf.graffito.dsw.gui.swing;

import javax.swing.*;
import java.awt.*;

public class WindowModeController {
    
    private static WindowModeController instance;
    private JFrame mainFrame;
    private WindowMode currentMode = WindowMode.NORMAL;
    private Dimension normalSize;
    private Dimension smallSize;
    
    private WindowModeController() {}
    
    public static WindowModeController getInstance() {
        if (instance == null) {
            instance = new WindowModeController();
        }
        return instance;
    }
    
    public void setMainFrame(JFrame frame) {
        this.mainFrame = frame;
        this.normalSize = frame.getSize();
        this.smallSize = new Dimension(normalSize.width / 2, normalSize.height / 2);
    }
    
    public void setMode(WindowMode mode) {
        if (mainFrame == null) return;
        
        this.currentMode = mode;
        GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        
        switch (mode) {
            case NORMAL:
                if (device.getFullScreenWindow() != null) {
                    device.setFullScreenWindow(null);
                }
                mainFrame.setSize(normalSize);
                mainFrame.setExtendedState(JFrame.NORMAL);
                break;
                
            case FULLSCREEN:
                if (device.getFullScreenWindow() == null) {
                    device.setFullScreenWindow(mainFrame);
                }
                break;
                
            case SMALL:
                if (device.getFullScreenWindow() != null) {
                    device.setFullScreenWindow(null);
                }
                mainFrame.setSize(smallSize);
                mainFrame.setExtendedState(JFrame.NORMAL);
                break;
        }
        
        // Update slide view scaling
        updateSlideScaling();
    }
    
    private void updateSlideScaling() {
        // This will be called when mode changes
        // Scaling is handled in SlideView based on available space
    }
    
    public WindowMode getCurrentMode() {
        return currentMode;
    }
    
    public double getScaleFactor() {
        switch (currentMode) {
            case NORMAL:
                return 1.0;
            case FULLSCREEN:
                GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
                DisplayMode dm = device.getDisplayMode();
                return Math.min((double) dm.getWidth() / normalSize.width, (double) dm.getHeight() / normalSize.height);
            case SMALL:
                return 0.5;
            default:
                return 1.0;
        }
    }
}

