package raf.graffito.dsw.core.graff.component;

import java.util.ArrayList;
import java.util.List;

public abstract class GraffNodeComposite extends GraffNode {
    protected List<GraffNode> children = new ArrayList<>();

    public GraffNodeComposite(String name, GraffNode parent) {
        super(name, parent);
    }

    @Override
    public void addChild(GraffNode child) {
        if (child == null) return;
        child.setParent(this);
        children.add(child);
        updateCounts();
    }

    @Override
    public void removeChild(GraffNode child) {
        if (child == null) return;
        children.remove(child);
        updateCounts();
    }

    @Override
    public List<GraffNode> getChildren() {
        return children;
    }

    protected abstract void updateCounts();
}
