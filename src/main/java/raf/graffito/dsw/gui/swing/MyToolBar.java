package raf.graffito.dsw.gui.swing;

import raf.graffito.dsw.controller.AddNodeAction;
import raf.graffito.dsw.controller.RemoveNodeAction;
import raf.graffito.dsw.controller.ExitAction;
import raf.graffito.dsw.core.graff.GraffRepository;
import raf.graffito.dsw.gui.swing.JTree.GraffTreeImplementation;

import javax.swing.*;

public class MyToolBar extends JToolBar {
    public MyToolBar(GraffTreeImplementation tree, GraffRepository repo) {
        setFloatable(false);
        add(new AddNodeAction(tree, repo));
        add(new RemoveNodeAction(tree, repo));
        addSeparator();
        add(new ExitAction());
    }
}
