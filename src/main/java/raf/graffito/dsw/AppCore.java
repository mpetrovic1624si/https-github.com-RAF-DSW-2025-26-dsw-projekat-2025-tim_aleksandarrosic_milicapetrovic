package raf.graffito.dsw;

import raf.graffito.dsw.gui.swing.MainFrame;
import javax.swing.SwingUtilities;

public class AppCore {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame mf = MainFrame.getInstance();
            mf.setVisible(true);
        });
    }
}
