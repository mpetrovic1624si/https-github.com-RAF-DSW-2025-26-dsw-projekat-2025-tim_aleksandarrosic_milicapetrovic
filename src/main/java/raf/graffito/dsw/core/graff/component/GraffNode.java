package raf.graffito.dsw.core.graff.component;

import java.util.List;

public abstract class GraffNode {
    protected String name;
    protected GraffNode parent;

    public GraffNode(String name, GraffNode parent) {
        this.name = name;
        this.parent = parent;
    }

    public String getName(){ return name; }
    public void setName(String name){ this.name = name; }

    public GraffNode getParent(){ return parent; }
    public void setParent(GraffNode parent){ this.parent = parent; }

    public abstract void addChild(GraffNode child);
    public abstract void removeChild(GraffNode child);
    public abstract List<GraffNode> getChildren();

    public GraffNode findByName(String name) {
        if (this.name != null && this.name.equals(name)) return this;
        for (GraffNode child : getChildren()) {
            GraffNode found = child.findByName(name);
            if (found != null) return found;
        }
        return null;
    }

    @Override
    public String toString() {
        return getName();
    }

    public int getSlideCount() { return 0; }
    public int getProjectCount() { return 0; }
}
