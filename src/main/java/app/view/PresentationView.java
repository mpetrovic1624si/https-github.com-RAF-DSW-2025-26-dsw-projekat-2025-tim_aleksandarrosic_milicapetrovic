package app.view;

import raf.graffito.dsw.core.graff.composites.Presentation;
import app.observer.Observer;

import javax.swing.*;
import java.awt.*;

public class PresentationView extends JPanel implements Observer {
    private JLabel presentationLabel;
    private Presentation presentation;

    public PresentationView(Presentation presentation) {
        this.presentation = presentation;
        // Note: Presentation class doesn't support observers, so addObserver is removed
        this.presentationLabel = new JLabel("Prezentacija: " + presentation.getName());
        setLayout(new BorderLayout());
        add(presentationLabel, BorderLayout.NORTH);
    }

    @Override
    public void update(Object notification) {
        presentationLabel.setText("Prezentacija: " + presentation.getName());
        revalidate();
        repaint();
    }
}
