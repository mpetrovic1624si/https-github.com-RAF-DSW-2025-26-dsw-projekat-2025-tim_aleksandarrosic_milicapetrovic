package raf.graffito.dsw.controller;

import javax.swing.*;
import javax.swing.tree.*;
import java.awt.datatransfer.*;
import java.io.IOException;

public class TreeTransferHandler extends TransferHandler {
    private final DataFlavor nodesFlavor;

    public TreeTransferHandler() {
        nodesFlavor = new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType, "application/x-java-jvm-local-objectref");
    }

    public int getSourceActions(JComponent c) {
        return MOVE;
    }

    protected Transferable createTransferable(JComponent c) {
        JTree tree = (JTree) c;
        return new NodesTransferable(tree.getSelectionPath());
    }

    
 //   protected void importData(JComponent c, Transferable t) {
 //       // možeš dodati logiku da ubaciš node na drugo mesto
 //   }

    static class NodesTransferable implements Transferable {
        private final TreePath path;
        public NodesTransferable(TreePath path) { this.path = path; }
        public DataFlavor[] getTransferDataFlavors() { return new DataFlavor[]{ DataFlavor.javaFileListFlavor }; }
        public boolean isDataFlavorSupported(DataFlavor flavor) { return true; }
        public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException { return path; }
    }
}
