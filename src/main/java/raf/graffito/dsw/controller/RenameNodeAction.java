package raf.graffito.dsw.controller;

import raf.graffito.dsw.core.graff.GraffRepository;
import raf.graffito.dsw.core.graff.component.GraffNode;
import raf.graffito.dsw.core.graff.composites.Workspace;
import raf.graffito.dsw.gui.swing.JTree.GraffTree;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.event.ActionEvent;

public class RenameNodeAction extends AbstractGraffAction {

    private GraffTree tree;
    private GraffRepository repository;

    public RenameNodeAction(GraffTree tree, GraffRepository repository) {
        putValue(NAME, "Promeni ime");
        putValue(SHORT_DESCRIPTION, "Promeni ime selektovanog čvora");
        this.tree = tree;
        this.repository = repository;
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
            JOptionPane.showMessageDialog(null, "Workspace ne može biti preimenovan.", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String currentName = graffNode.getName();
        Object result = JOptionPane.showInputDialog(
            null,
            "Unesite novo ime:",
            "Promena imena",
            JOptionPane.QUESTION_MESSAGE,
            null,
            null,
            currentName
        );

        if (result != null) {
            String newName = result.toString();
            if (!newName.trim().isEmpty() && !newName.equals(currentName)) {
                if (repository.removeNode(graffNode, newName)) {
                    tree.reload();
                } else {
                    JOptionPane.showMessageDialog(null, "Ime već postoji ili je neispravno.", "Greška", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
}

