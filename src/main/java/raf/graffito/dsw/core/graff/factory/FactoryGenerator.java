package raf.graffito.dsw.core.graff.factory;

import raf.graffito.dsw.core.graff.component.GraffNode;
import raf.graffito.dsw.core.graff.composites.Workspace;
import raf.graffito.dsw.core.graff.composites.Project;
import raf.graffito.dsw.core.graff.composites.Presentation;

public class FactoryGenerator {
    public static GraffNodeFactory getFactory(GraffNode parent) {
        if (parent instanceof Workspace) return new ProjectFactory();
        if (parent instanceof Project) return new PresentationFactory();
        if (parent instanceof Presentation) return new SlideFactory();
        return null;
    }
}
