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
        // File button
        JButton fileButton = new JButton(new ExitAction());
        fileButton.setText("File");
        add(fileButton);
        
        addSeparator();
        
        // AboutUs button
        JButton aboutUsButton = new JButton(new AboutUsAction());
        aboutUsButton.setText("AboutUs");
        add(aboutUsButton);
        
        addSeparator();
        
        // DeleteNode button
        JButton deleteNodeButton = new JButton(new RemoveNodeAction(tree, repository));
        deleteNodeButton.setText("DeleteNode");
        add(deleteNodeButton);
        
        addSeparator();
        
        // AddNode button
        JButton addNodeButton = new JButton(new AddNodeAction(tree, repository));
        addNodeButton.setText("AddNode");
        add(addNodeButton);
    }
}
