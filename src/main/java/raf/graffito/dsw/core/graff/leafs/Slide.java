package raf.graffito.dsw.core.graff.leafs;

import raf.graffito.dsw.core.graff.component.GraffLeaf;
import raf.graffito.dsw.core.graff.component.GraffNode;

public class Slide extends GraffLeaf {
    public Slide(String name, GraffNode parent) {
        super(name, parent);
    }

    @Override
    public int getSlideCount() { return 1; }
}
