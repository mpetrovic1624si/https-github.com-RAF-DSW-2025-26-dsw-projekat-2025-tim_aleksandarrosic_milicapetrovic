package raf.graffito.dsw.core.graff.component;

import java.util.Collections;
import java.util.List;

public abstract class GraffLeaf extends GraffNode {
    public GraffLeaf(String name, GraffNode parent) {
        super(name, parent);
    }

    @Override
    public void addChild(GraffNode child) { /* leaves can't have children */ }

    @Override
    public void removeChild(GraffNode child) { /* leaves can't have children */ }

    @Override
    public List<GraffNode> getChildren() { return Collections.emptyList(); }

    @Override
    public GraffNode findByName(String name) {
        return (this.name != null && this.name.equals(name)) ? this : null;
    }
}
