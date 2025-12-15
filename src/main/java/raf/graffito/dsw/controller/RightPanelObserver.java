package raf.graffito.dsw.controller;

import raf.graffito.dsw.core.graff.composites.Presentation;
import raf.graffito.dsw.core.graff.component.GraffNode;
import app.observer.Observer;

import javax.swing.*;
import java.util.*;

public class RightPanelObserver implements Observer {
    private final JTabbedPane tabbedPane;
    private final Presentation presentation;

    public RightPanelObserver(JTabbedPane tabbedPane, Presentation presentation) {
        this.tabbedPane = tabbedPane;
        this.presentation = presentation;
        presentation.addObserver(this);
    }

    @Override
    public void update(Object notification) {
        // Prvo ukloni sve tabove koji više ne postoje
        Set<String> validSlides = new HashSet<>();
        for (GraffNode slide : presentation.getChildren()) {
            validSlides.add(slide.getName());
        }

        for (int i = tabbedPane.getTabCount() - 1; i >= 0; i--) {
            String tabTitle = tabbedPane.getTitleAt(i);
            if (!validSlides.contains(tabTitle)) {
                tabbedPane.removeTabAt(i);
            }
        }

        // Dodaj nove tabove za nove slajdove
        for (GraffNode slide : presentation.getChildren()) {
            boolean exists = false;
            for (int i = 0; i < tabbedPane.getTabCount(); i++) {
                if (tabbedPane.getTitleAt(i).equals(slide.getName())) {
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                JPanel panel = new JPanel();
                tabbedPane.addTab(slide.getName(), panel);
            }
        }
    }
}
