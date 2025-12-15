package raf.graffito.dsw.core;
import raf.graffito.dsw.gui.swing.MainFrame;

public class ApplicationFramework {
    // Buduća polja za model celog projekta
    private static ApplicationFramework instance;
    public ApplicationFramework(){
        initialize();
    }
    public static ApplicationFramework getInstance(){
        if(instance == null){
            instance = new ApplicationFramework();
        }
        return instance;
    }

    public void initialize(){
        MainFrame mainFrame = new MainFrame();
        mainFrame.setVisible(true);
    }
}
