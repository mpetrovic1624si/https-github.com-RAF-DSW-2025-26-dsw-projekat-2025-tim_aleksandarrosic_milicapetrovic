package raf.graffito.dsw.core.graff.factory;

import raf.graffito.dsw.core.graff.composites.Presentation;
import raf.graffito.dsw.core.graff.component.GraffNode;
import raf.graffito.dsw.core.graff.component.GraffNodeComposite;

public class PresentationFactory implements GraffNodeFactory {

    @Override
    public GraffNode createNode(GraffNodeComposite parent) {
        // Broji koliko prezentacija već postoji
        int count = 0;
        for (GraffNode child : parent.getChildren()) {
            if (child instanceof Presentation) {
                count++;
            }
        }
        // Generiše novo ime Presentation n
        String name = "Presentation " + (count + 1);
        return new Presentation(name, parent);
    }
}
