package raf.graffito.dsw.gui.swing.JTree;

import raf.graffito.dsw.core.graff.GraffRepository;
import raf.graffito.dsw.core.graff.component.GraffNode;
import raf.graffito.dsw.core.graff.component.GraffNodeComposite;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetListener;

public class GraffTreeImplementation implements GraffTree {

    private JTree tree;
    private DefaultTreeModel treeModel;
    private GraffRepository repository;
    private DefaultMutableTreeNode rootNode;

    public GraffTreeImplementation(GraffRepository repository) {
        this.repository = repository;
        this.rootNode = new DefaultMutableTreeNode(repository.getWorkspace());
        this.treeModel = new DefaultTreeModel(rootNode);
        this.tree = new JTree(treeModel);
        populateTree(rootNode, repository.getWorkspace());
        tree.setRootVisible(true);
        tree.setDragEnabled(true);
        tree.setDropMode(javax.swing.DropMode.ON_OR_INSERT);
        // simple: attach an empty drop target so DnD works in basic cases
        tree.setDropTarget(new DropTarget());
    }

    private void populateTree(DefaultMutableTreeNode parentNode, GraffNodeComposite parent) {
        parentNode.removeAllChildren();
        for (GraffNode child : parent.getChildren()) {
            DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(child);
            parentNode.add(childNode);
            if (child instanceof GraffNodeComposite) {
                populateTree(childNode, (GraffNodeComposite) child);
            }
        }
    }

    @Override
    public JTree getTree() { return tree; }

    @Override
    public DefaultTreeModel getModel() { return treeModel; }

    @Override
    public void addNode(GraffNode parent, GraffNode child) {
        populateTree(rootNode, repository.getWorkspace());
        treeModel.reload();
    }

    @Override
    public void removeNode(GraffNode node) {
        populateTree(rootNode, repository.getWorkspace());
        treeModel.reload();
    }

    @Override
    public void reload() {
        populateTree(rootNode, repository.getWorkspace());
        treeModel.reload();
    }

    public GraffRepository getRepository() {
        return repository;
    }
}
