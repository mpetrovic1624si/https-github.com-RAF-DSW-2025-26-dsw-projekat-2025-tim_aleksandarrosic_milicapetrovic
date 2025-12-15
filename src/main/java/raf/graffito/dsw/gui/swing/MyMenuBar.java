package raf.graffito.dsw.gui.swing;

import raf.graffito.dsw.controller.AddNodeAction;
import raf.graffito.dsw.controller.RemoveNodeAction;
import raf.graffito.dsw.controller.ExitAction;
import raf.graffito.dsw.controller.AboutUsAction;
import raf.graffito.dsw.core.graff.GraffRepository;
import raf.graffito.dsw.gui.swing.JTree.GraffTreeImplementation;

import javax.swing.*;

public class MyMenuBar extends JMenuBar {
    public MyMenuBar(GraffTreeImplementation tree, GraffRepository repository) {
        JMenu file = new JMenu("File");
        file.add(new ExitAction());
        add(file);

        JMenu edit = new JMenu("Edit");
        edit.add(new AddNodeAction(tree, repository));
        edit.add(new RemoveNodeAction(tree, repository));
        add(edit);

        JMenu help = new JMenu("Help");
        help.add(new AboutUsAction());
        add(help);
    }
}
