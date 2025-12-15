package raf.graffito.dsw.core.graff;

import raf.graffito.dsw.core.graff.component.GraffNode;
import raf.graffito.dsw.core.graff.component.GraffNodeComposite;
import raf.graffito.dsw.core.graff.composites.Workspace;
import raf.graffito.dsw.core.message.MessageGenerator;
import raf.graffito.dsw.core.graff.observer.RepositoryObserver;
import raf.graffito.dsw.core.graff.factory.FactoryGenerator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GraffRepository {

    private Workspace workspace;
    private MessageGenerator messageGenerator;
    private List<RepositoryObserver> observers = new ArrayList<>();

    public GraffRepository(Workspace workspace) {
        this.workspace = workspace;
        this.messageGenerator = new MessageGenerator();
    }

    public Workspace getWorkspace() {
        return workspace;
    }

    public void addObserver(RepositoryObserver o){ observers.add(o); }
    public void removeObserver(RepositoryObserver o){ observers.remove(o); }
    private void notifyObservers(){ for (RepositoryObserver o: observers) o.repositoryChanged(); }

    // Dodavanje čvora sa validacijom
    public boolean addNode(GraffNode parent, GraffNode child) {
        if (!canAddChild(parent, child)) {
            messageGenerator.generateMessage("UNAUTHORIZED_ACCESS");
            return false;
        }

        if (hasDuplicateName(parent, child.getName())) {
            messageGenerator.generateMessage("INVALID_INPUT");
            return false;
        }

        if (parent instanceof GraffNodeComposite composite) {
            composite.addChild(child);
            notifyObservers();
            return true;
        }

        messageGenerator.generateMessage("UNAUTHORIZED_ACCESS");
        return false;
    }

    // Brisanje čvora
    public boolean removeNode(GraffNode node) {
        if (node == null) return false;
        if (node instanceof Workspace) {
            messageGenerator.generateMessage("NODE_CANNOT_BE_DELETED");
            return false;
        }

        GraffNode parent = node.getParent();
        if (parent instanceof GraffNodeComposite composite) {
            composite.removeChild(node);
            notifyObservers();
            return true;
        }

        return false;
    }

    // --- PRAVILA HIJERARHIJE ---
    private boolean canAddChild(GraffNode parent, GraffNode child) {
        if (parent instanceof Workspace)
            return child instanceof raf.graffito.dsw.core.graff.composites.Project;

        if (parent instanceof raf.graffito.dsw.core.graff.composites.Project)
            return (child instanceof raf.graffito.dsw.core.graff.composites.Presentation || child instanceof raf.graffito.dsw.core.graff.leafs.Slide);

        if (parent instanceof raf.graffito.dsw.core.graff.composites.Presentation)
            return child instanceof raf.graffito.dsw.core.graff.leafs.Slide;

        if (parent instanceof raf.graffito.dsw.core.graff.leafs.Slide)
            return false;

        return true;
    }

    // --- PROVERA DUPLIKATA ---
    private boolean hasDuplicateName(GraffNode parent, String name) {
        Set<String> existingNames = new HashSet<>();
        List<GraffNode> children = parent.getChildren();
        for (GraffNode child : children) {
            existingNames.add(child.getName());
        }
        return existingNames.contains(name);
    }

    // convenience: create via factory
    public GraffNode createNodeForParent(GraffNode parent) {
        if (!(parent instanceof GraffNodeComposite)) {
            return null;
        }
        return FactoryGenerator.getFactory(parent).createNode((GraffNodeComposite) parent);
    }
}
