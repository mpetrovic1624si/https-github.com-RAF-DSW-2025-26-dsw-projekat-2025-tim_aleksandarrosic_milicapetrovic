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
import raf.graffito.dsw.controller.SlideControllerManager;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private static MainFrame instance;
    private GraffTreeImplementation graffTree;
    private GraffRepository repository;
    private JTabbedPane tabbedPane;
    private RightToolBar rightToolBar;
    private ImageLoaderPanel imageLoaderPanel;

    public MainFrame() {
        repository = new GraffRepository(new Workspace("Workspace"));
        graffTree = new GraffTreeImplementation(repository);
        tabbedPane = new JTabbedPane();
        repository.addObserver(() -> graffTree.reload());
        initialize();
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
        
        // Window mode selector
        WindowModePanel modePanel = new WindowModePanel();
        WindowModeController.getInstance().setMainFrame(this);
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(toolBar, BorderLayout.CENTER);
        topPanel.add(modePanel, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        // Initialize SlideControllerManager with tabbedPane
        SlideControllerManager.getInstance().setTabbedPane(tabbedPane);
        
        // Update right toolbar when tab changes
        tabbedPane.addChangeListener(e -> {
            if (rightToolBar != null) {
                view.SlideView currentView = SlideControllerManager.getInstance().getCurrentSlideView();
                rightToolBar.setCurrentSlideView(currentView);
            }
        });

        TreeController treeController = new TreeController(graffTree, tabbedPane);

        JSplitPane mainSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        
        // Left side: Tree
        JSplitPane leftSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        JScrollPane leftScroll = new JScrollPane(graffTree.getTree());
        leftSplit.setTopComponent(leftScroll);
        
        // Image loader panel on left bottom
        imageLoaderPanel = new ImageLoaderPanel();
        leftSplit.setBottomComponent(imageLoaderPanel);
        leftSplit.setDividerLocation(400);
        
        mainSplit.setLeftComponent(leftSplit);
        mainSplit.setRightComponent(tabbedPane);
        mainSplit.setDividerLocation(300);

        // Create right toolbar and add to EAST
        rightToolBar = new RightToolBar();
        add(rightToolBar, BorderLayout.EAST);

        add(mainSplit, BorderLayout.CENTER);
    }

    public JTabbedPane getTabbedPane(){ return tabbedPane; }
    public RightToolBar getRightToolBar(){ return rightToolBar; }
    public ImageLoaderPanel getImageLoaderPanel(){ return imageLoaderPanel; }

