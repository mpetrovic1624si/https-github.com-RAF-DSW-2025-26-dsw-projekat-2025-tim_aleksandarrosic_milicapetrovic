package raf.graffito.dsw.controller;

import raf.graffito.dsw.gui.swing.JTree.GraffTreeImplementation;
import raf.graffito.dsw.core.graff.component.GraffNode;
import raf.graffito.dsw.core.graff.composites.Presentation;
import raf.graffito.dsw.core.graff.leafs.Slide;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

public class TreeController {

    private GraffTreeImplementation graffTree;
    private JTabbedPane tabbedPane;

    public TreeController(GraffTreeImplementation graffTree, JTabbedPane tabbedPane) {
        this.graffTree = graffTree;
        this.tabbedPane = tabbedPane;
        attachListeners();
    }

    private void attachListeners() {
        graffTree.getTree().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    TreePath path = graffTree.getTree().getPathForLocation(e.getX(), e.getY());
                    if (path == null) return;
                    DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
                    Object userObj = node.getUserObject();
                    if (userObj instanceof GraffNode) {
                        openTabs((GraffNode) userObj);
                    }
                }
            }
        });
    }

    private Map<Presentation, RightPanelObserver> observersMap = new HashMap<>();

    private void openTabs(GraffNode node) {
        if (node instanceof Presentation presentation) {
            // Registruj observer za ovu prezentaciju
            if (!observersMap.containsKey(presentation)) {
                observersMap.put(presentation, new RightPanelObserver(tabbedPane, presentation));
            }

        // Otvori tabove za slajdove
        for (GraffNode s : presentation.getChildren()) {
            if (s instanceof Slide slide) {
                boolean exists = false;
                for (int i = 0; i < tabbedPane.getTabCount(); i++) {
                    if (tabbedPane.getTitleAt(i).equals(slide.getName())) {
                        exists = true;
                        break;
                    }
                }
                if (!exists) {
                    tabbedPane.addTab(slide.getName(), new JPanel());
                }
            }
        }

        } else if (node instanceof Slide slide) {
        // Otvori tab za pojedinačni slajd
        boolean exists = false;
        for (int i = 0; i < tabbedPane.getTabCount(); i++) {
            if (tabbedPane.getTitleAt(i).equals(slide.getName())) {
                exists = true;
                break;
                }
            }
            if (!exists) {
                tabbedPane.addTab(slide.getName(), new JPanel());
                }
            }
    }
}
