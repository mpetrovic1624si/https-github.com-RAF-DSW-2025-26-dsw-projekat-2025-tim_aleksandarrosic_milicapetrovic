package raf.graffito.dsw.controller;

import raf.graffito.dsw.core.graff.GraffRepository;
import raf.graffito.dsw.core.graff.component.GraffNode;
import raf.graffito.dsw.core.graff.composites.Presentation;
import raf.graffito.dsw.core.graff.composites.Project;
import raf.graffito.dsw.core.graff.leafs.Slide;
import raf.graffito.dsw.gui.swing.JTree.GraffTree;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Map;

public class RemoveNodeAction extends AbstractGraffAction {

    private GraffTree tree;
    private GraffRepository repository;
    private JTabbedPane tabbedPane;
    private Map<Presentation, RightPanelObserver> observersMap;

    public RemoveNodeAction(GraffTree tree, GraffRepository repository) {
        this(tree, repository, null, null);
    }

    public RemoveNodeAction(GraffTree tree, GraffRepository repository,
                            JTabbedPane tabbedPane,
                            Map<Presentation, RightPanelObserver> observersMap) {
        putValue(NAME, "Obriši čvor");
        this.tree = tree;
        this.repository = repository;
        this.tabbedPane = tabbedPane;
        this.observersMap = observersMap;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object sel = tree.getTree().getLastSelectedPathComponent();
        if (!(sel instanceof javax.swing.tree.DefaultMutableTreeNode)) return;
        javax.swing.tree.DefaultMutableTreeNode node = (javax.swing.tree.DefaultMutableTreeNode) sel;
        Object user = node.getUserObject();
        if (!(user instanceof GraffNode)) return;
        GraffNode target = (GraffNode) user;

        if (repository.removeNode(target)) {
            tree.reload();
            if (observersMap != null && tabbedPane != null) {
                if (target instanceof Slide slide) {
                    // Ukloni tab samo za ovaj slajd
                    GraffNode parent = slide.getParent();
                    if (parent instanceof Presentation parentPresentation) {
                        RightPanelObserver observer = observersMap.get(parentPresentation);
                        if (observer != null) {
                            observer.update(null); // osveži tabove - ukloniće tab za obrisani slajd
                        }
                    }
                } else if (target instanceof Presentation presentation) {
                    // Ukloni sve tabove ove prezentacije
                    RightPanelObserver observer = observersMap.get(presentation);
                    if (observer != null) {
                        // Ukloni sve tabove koji pripadaju ovoj prezentaciji
                        // Pošto prezentacija je već obrisana, uklanjamo sve tabove
                        tabbedPane.removeAll();
                    }
                    observersMap.remove(presentation);
                } else if (target instanceof Project project) {
                    // Ukloni sve tabove za sve prezentacije u projektu
                    for (GraffNode child : project.getChildren()) {
                        if (child instanceof Presentation p) {
                            RightPanelObserver observer = observersMap.get(p);
                            if (observer != null) {
                                // Tabovi će biti uklonjeni kada se ukloni projekat
                            }
                            observersMap.remove(p);
                        }
                    }
                    // Ukloni sve tabove jer su sve prezentacije iz projekta obrisane
                    tabbedPane.removeAll();
                }
            }

        }   
    }
}
