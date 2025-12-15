package raf.graffito.dsw.controller;

import raf.graffito.dsw.core.graff.GraffRepository;
import raf.graffito.dsw.core.graff.component.GraffNode;
import raf.graffito.dsw.core.graff.composites.Workspace;
import raf.graffito.dsw.gui.swing.JTree.GraffTree;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

public class ChangeColorAction extends AbstractGraffAction {

    private GraffTree tree;
    private static Map<GraffNode, Color> nodeColors = new HashMap<>();

    public ChangeColorAction(GraffTree tree, GraffRepository repository) {
        putValue(NAME, "Promeni boju");
        putValue(SHORT_DESCRIPTION, "Promeni boju selektovanog čvora");
        this.tree = tree;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object sel = tree.getTree().getLastSelectedPathComponent();
        if (!(sel instanceof DefaultMutableTreeNode)) {
            JOptionPane.showMessageDialog(null, "Selektuj čvor u stablu.", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) sel;
        Object user = node.getUserObject();
        if (!(user instanceof GraffNode)) return;
        
        GraffNode graffNode = (GraffNode) user;
        
        if (graffNode instanceof Workspace) {
            JOptionPane.showMessageDialog(null, "Workspace ne može imati boju.", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        Color currentColor = nodeColors.get(graffNode);
        Color newColor = JColorChooser.showDialog(
            null,
            "Izaberite boju za čvor",
            currentColor != null ? currentColor : Color.WHITE
        );

        if (newColor != null) {
            nodeColors.put(graffNode, newColor);
            tree.reload();
        }
    }

    public static Color getColorForNode(GraffNode node) {
        return nodeColors.get(node);
    }

    public static void removeColorForNode(GraffNode node) {
        nodeColors.remove(node);
    }
}

