package raf.graffito.dsw.gui.swing;

import app.model.ImageElement;
import app.model.LogoElement;
import app.model.Slide;
import app.model.TextElement;
import app.view.SlideView;
import raf.graffito.dsw.core.graff.GraffRepository;
import raf.graffito.dsw.core.graff.composites.Workspace;
import raf.graffito.dsw.gui.swing.JTree.GraffTreeImplementation;
import raf.graffito.dsw.controller.TreeController;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private static MainFrame instance;
    private GraffTreeImplementation graffTree;
    private GraffRepository repository;
    private JTabbedPane tabbedPane;

    public MainFrame() {
        repository = new GraffRepository(new Workspace("Workspace"));
        graffTree = new GraffTreeImplementation(repository);
        tabbedPane = new JTabbedPane();
        initialize();
        repository.addObserver(() -> graffTree.reload());
        Slide slide = new Slide();

        slide.addElement(new ImageElement(50, 50, 200, 150, "test.jpg"));
        SlideView slideView = new SlideView(slide);


        JPanel thumbnails = new JPanel();
        thumbnails.setPreferredSize(new Dimension(150, 600));
        thumbnails.setLayout(new BoxLayout(thumbnails, BoxLayout.Y_AXIS));

        thumbnails.add(new JLabel(new ImageIcon("test.jpg")));

        setLayout(new BorderLayout());
        add(slideView, BorderLayout.CENTER);
        add(thumbnails, BorderLayout.EAST);

        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

    }

    public static MainFrame getInstance(){
        if (instance == null) instance = new MainFrame();
        return instance;
    }

    private void initialize() {
        setTitle("Graffito - fixed demo");
        setSize(800,600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        MyMenuBar menu = new MyMenuBar(graffTree, repository);
        setJMenuBar(menu);

        MyToolBar toolBar = new MyToolBar(graffTree, repository);
        add(toolBar, BorderLayout.NORTH);

        TreeController treeController = new TreeController(graffTree, tabbedPane);

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        JScrollPane leftScroll = new JScrollPane(graffTree.getTree());
        split.setLeftComponent(leftScroll);
        split.setRightComponent(tabbedPane);
        split.setDividerLocation(250);

        add(split, BorderLayout.CENTER);
    }

    public JTabbedPane getTabbedPane(){ return tabbedPane; }

