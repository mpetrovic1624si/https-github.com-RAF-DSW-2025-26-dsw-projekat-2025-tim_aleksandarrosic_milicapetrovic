package raf.graffito.dsw.core.graff.factory;

import raf.graffito.dsw.core.graff.component.GraffNode;
import raf.graffito.dsw.core.graff.component.GraffNodeComposite;
import raf.graffito.dsw.core.graff.composites.Project;

public class ProjectFactory implements GraffNodeFactory {

    private static int projectCounter = 1; // brojač za projekte

    @Override
    public GraffNode createNode(GraffNodeComposite parent) {
        String projectName = "Project " + projectCounter;
        projectCounter++; // povećaj za sledeći projekat
        return new Project(projectName, parent);
    }

    // Opcionalno: reset brojača (ako se startuje nova Workspace)
    public static void resetCounter() {
        projectCounter = 1;
    }
}
