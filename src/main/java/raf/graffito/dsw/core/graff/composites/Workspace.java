package raf.graffito.dsw.core.graff.composites;

import raf.graffito.dsw.core.graff.component.GraffNode;
import raf.graffito.dsw.core.graff.component.GraffNodeComposite;

public class Workspace extends GraffNodeComposite {
    private int projectCount;

    public Workspace(String name) {
        super(name, null);
    }

    @Override
    protected void updateCounts() {
        int total = 0;
        for (GraffNode child : children) {
            total += child.getProjectCount();
        }
        this.projectCount = total;
    }

    @Override
    public int getProjectCount() {
        return projectCount;
    }
}
