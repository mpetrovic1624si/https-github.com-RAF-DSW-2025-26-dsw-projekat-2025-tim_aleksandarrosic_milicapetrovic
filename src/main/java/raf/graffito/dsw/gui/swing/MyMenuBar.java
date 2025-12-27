package raf.graffito.dsw.gui.swing;

import raf.graffito.dsw.controller.*;
import raf.graffito.dsw.core.graff.GraffRepository;
import raf.graffito.dsw.gui.swing.JTree.GraffTreeImplementation;
import raf.graffito.dsw.controller.serializer.Serializer;

import javax.swing.*;

public class MyMenuBar extends JMenuBar {
    public MyMenuBar(GraffTreeImplementation tree, GraffRepository repository) {
        // Serializer za Save/Open/Template akcije
        Serializer serializer = new Serializer(repository);
        
        // File button - dropdown menu
        JMenu fileMenu = new JMenu("File");
        fileMenu.add(new SaveAction(serializer));
        fileMenu.add(new SaveAsAction(serializer));
        fileMenu.add(new OpenAction(serializer));
        fileMenu.addSeparator();
        fileMenu.add(new SaveTemplateAction(serializer));
        fileMenu.add(new LoadTemplateAction(serializer));
        fileMenu.addSeparator();
        
        // Undo/Redo - koriste se iz trenutnog SlideController-a
        // Ne dodajemo ih ovde jer zavise od aktivnog slide-a
        // Implementirani su u SlideController i dostupni preko keyboard shortcuts
        
        fileMenu.addSeparator();
        fileMenu.add(new ExitAction());
        add(fileMenu);
        
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
