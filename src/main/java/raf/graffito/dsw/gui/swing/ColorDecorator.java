package raf.graffito.dsw.gui.swing;

import raf.graffito.dsw.core.graff.component.GraffNode;
import java.awt.*;
import java.util.List;

public class ColorDecorator extends GraffNode {
    private GraffNode node;
    private Color color;

    public ColorDecorator(GraffNode node, Color color) {
        super(node.getName(), node.getParent());
        this.node = node;
        this.color = color;
    }

    @Override
    public void addChild(GraffNode child) { node.addChild(child); }

    @Override
    public void removeChild(GraffNode child) { node.removeChild(child); }

    @Override
    public List<GraffNode> getChildren() { return node.getChildren(); }

    public Color getColor() { return color; }
    public void setColor(Color color) { this.color = color; }

    @Override
    public String toString() { return node.getName(); }
}
