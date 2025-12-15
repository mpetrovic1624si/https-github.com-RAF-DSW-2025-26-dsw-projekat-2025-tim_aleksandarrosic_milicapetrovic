package raf.graffito.dsw.core.graff.factory;

import raf.graffito.dsw.core.graff.leafs.Slide;
import raf.graffito.dsw.core.graff.component.GraffNode;
import raf.graffito.dsw.core.graff.component.GraffNodeComposite;

public class SlideFactory implements GraffNodeFactory {
    @Override
    public GraffNode createNode(GraffNodeComposite parent) {
        int slideCount = 0;

        // Proveri koliko već ima slajdova kod roditelja
        if (parent != null && parent.getChildren() != null) {
            for (GraffNode node : parent.getChildren()) {
                if (node instanceof Slide) {
                    slideCount++;
                }
            }
        }

        // Novi slajd ima broj (slideCount + 1)
        String name = "New Slide " + (slideCount + 1);
        return new Slide(name, parent);
    }
}