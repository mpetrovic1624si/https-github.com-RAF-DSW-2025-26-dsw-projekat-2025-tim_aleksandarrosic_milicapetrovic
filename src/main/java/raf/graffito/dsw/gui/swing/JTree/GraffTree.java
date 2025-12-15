package raf.graffito.dsw.gui.swing.JTree;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import raf.graffito.dsw.core.graff.component.GraffNode;

public interface GraffTree {
    JTree getTree();
    DefaultTreeModel getModel();
    void addNode(GraffNode parent, GraffNode child);
    void removeNode(GraffNode node);
    void reload();
}
