package app.view;

import raf.graffito.dsw.core.graff.composites.Project;
import app.observer.Observer;

import javax.swing.*;
import java.awt.*;

public class ProjectView extends JPanel implements Observer {
    private JLabel projectLabel;
    private Project project;

    public ProjectView(Project project) {
        this.project = project;
        // Note: Project class doesn't support observers, so addObserver is removed
        this.projectLabel = new JLabel("Projekat: " + project.getName());
        setLayout(new BorderLayout());
        add(projectLabel, BorderLayout.NORTH);
    }

    @Override
    public void update(Object notification) {
        projectLabel.setText("Projekat: " + project.getName());
        revalidate();
        repaint();
    }
}
