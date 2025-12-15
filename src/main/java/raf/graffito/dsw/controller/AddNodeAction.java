package raf.graffito.dsw.controller;

import raf.graffito.dsw.core.graff.GraffRepository;
import raf.graffito.dsw.core.graff.component.GraffNode;
import raf.graffito.dsw.core.graff.component.GraffNodeComposite;
import raf.graffito.dsw.core.graff.factory.FactoryGenerator;
import raf.graffito.dsw.gui.swing.JTree.GraffTree;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class AddNodeAction extends AbstractGraffAction {

    private GraffTree tree;
    private GraffRepository repository;

    public AddNodeAction(GraffTree tree, GraffRepository repository) {
        putValue(NAME, "Dodaj čvor");
        this.tree = tree;
        this.repository = repository;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object sel = tree.getTree().getLastSelectedPathComponent();
        if (!(sel instanceof javax.swing.tree.DefaultMutableTreeNode)) {
            JOptionPane.showMessageDialog(null, "Selektuj čvor u stablu.", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        javax.swing.tree.DefaultMutableTreeNode node = (javax.swing.tree.DefaultMutableTreeNode) sel;
        Object user = node.getUserObject();
        if (!(user instanceof GraffNode)) return;
        GraffNode parent = (GraffNode) user;
        var factory = FactoryGenerator.getFactory(parent);
        if (factory == null) {
            repository.getWorkspace(); // no-op
            repository.getWorkspace();
            JOptionPane.showMessageDialog(null, "Izabrani čvor ne može imati dece.", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        GraffNode newNode = factory.createNode((GraffNodeComposite) parent);
        if (repository.addNode(parent, newNode)) {
            tree.reload();
        }
    }
}
