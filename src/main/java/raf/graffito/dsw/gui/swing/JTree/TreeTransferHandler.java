package raf.graffito.dsw.gui.swing.JTree;

import raf.graffito.dsw.core.graff.component.GraffNode;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class TreeTransferHandler extends TransferHandler {
    
    private static final DataFlavor NODE_FLAVOR;
    static {
        try {
            NODE_FLAVOR = new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType + ";class=" + GraffNode.class.getName());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    private GraffTreeImplementation treeImplementation;

    public TreeTransferHandler(GraffTreeImplementation treeImplementation) {
        this.treeImplementation = treeImplementation;
    }

    @Override
    public int getSourceActions(JComponent c) {
        return MOVE;
    }

    @Override
    protected Transferable createTransferable(JComponent c) {
        JTree tree = (JTree) c;
        TreePath path = tree.getSelectionPath();
        if (path != null) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
            return new NodeTransferable((GraffNode) node.getUserObject());
        }
        return null;
    }

    @Override
    protected void exportDone(JComponent source, Transferable data, int action) {
        // Cleanup if needed
    }

    @Override
    public boolean canImport(TransferHandler.TransferSupport support) {
        if (!support.isDrop()) {
            return false;
        }
        support.setShowDropLocation(true);
        if (!support.isDataFlavorSupported(NODE_FLAVOR)) {
            return false;
        }
        
        JTree.DropLocation dl = (JTree.DropLocation) support.getDropLocation();
        TreePath dest = dl.getPath();
        if (dest == null) {
            return false;
        }
        
        DefaultMutableTreeNode targetNode = (DefaultMutableTreeNode) dest.getLastPathComponent();
        GraffNode targetGraffNode = (GraffNode) targetNode.getUserObject();
        
        // Don't allow dropping on itself or its descendants
        try {
            GraffNode sourceNode = (GraffNode) support.getTransferable().getTransferData(NODE_FLAVOR);
            if (sourceNode == targetGraffNode) {
                return false;
            }
            // Check if target is a descendant of source
            GraffNode parent = targetGraffNode.getParent();
            while (parent != null) {
                if (parent == sourceNode) {
                    return false;
                }
                parent = parent.getParent();
            }
        } catch (Exception e) {
            return false;
        }
        
        return true;
    }

    @Override
    public boolean importData(TransferHandler.TransferSupport support) {
        if (!canImport(support)) {
            return false;
        }

        JTree.DropLocation dl = (JTree.DropLocation) support.getDropLocation();
        TreePath dest = dl.getPath();
        DefaultMutableTreeNode targetNode = (DefaultMutableTreeNode) dest.getLastPathComponent();
        GraffNode targetGraffNode = (GraffNode) targetNode.getUserObject();

        try {
            GraffNode sourceNode = (GraffNode) support.getTransferable().getTransferData(NODE_FLAVOR);
            
            // Remove from old parent
            treeImplementation.removeNode(sourceNode);
            
            // Add to new parent
            treeImplementation.addNode(targetGraffNode, sourceNode);
            
            return true;
        } catch (UnsupportedFlavorException | IOException e) {
            return false;
        }
    }

    private static class NodeTransferable implements Transferable {
        private GraffNode node;

        public NodeTransferable(GraffNode node) {
            this.node = node;
        }

        @Override
        public DataFlavor[] getTransferDataFlavors() {
            return new DataFlavor[]{NODE_FLAVOR};
        }

        @Override
        public boolean isDataFlavorSupported(DataFlavor flavor) {
            return NODE_FLAVOR.equals(flavor);
        }

        @Override
        public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
            if (!isDataFlavorSupported(flavor)) {
                throw new UnsupportedFlavorException(flavor);
            }
            return node;
        }
    }
}

