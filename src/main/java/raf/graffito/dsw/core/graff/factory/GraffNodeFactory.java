package raf.graffito.dsw.core.graff.factory;

import raf.graffito.dsw.core.graff.component.GraffNode;
import raf.graffito.dsw.core.graff.component.GraffNodeComposite;

public interface GraffNodeFactory {
    GraffNode createNode(GraffNodeComposite parent);
}
