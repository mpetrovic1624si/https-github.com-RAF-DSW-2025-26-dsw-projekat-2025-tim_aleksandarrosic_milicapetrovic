package raf.graffito.dsw.controller.serializer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import raf.graffito.dsw.core.graff.GraffRepository;
import raf.graffito.dsw.core.graff.composites.Workspace;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class Serializer {

    private File currentFile;
    private final ObjectMapper mapper;
    private GraffRepository repository;
    private boolean hasUnsavedChanges;

    public Serializer(GraffRepository repository) {
        this.repository = repository;
        this.mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        this.hasUnsavedChanges = false;
        // Register this serializer with repository for change tracking
        repository.setSerializer(this);
    }

    // ================================
    // SAVE
    // ================================
    public void save() {
        if (!hasUnsavedChanges) {
            return; // Prevent re-serialization if project hasn't changed
        }
        
        if (currentFile == null) {
            saveAs();
            return;
        }

        try {
            Workspace workspace = repository.getWorkspace();
            mapper.writeValue(currentFile, workspace);
            hasUnsavedChanges = false;
        } catch (IOException e) {
            showError("Greška prilikom čuvanja projekta!");
            e.printStackTrace();
        }
    }

    // ================================
    // SAVE AS
    // ================================
    public void saveAs() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("JSON Files", "json"));
        if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = chooser.getSelectedFile();
            // Ensure .json extension
            if (!selectedFile.getName().toLowerCase().endsWith(".json")) {
                selectedFile = new File(selectedFile.getParent(), selectedFile.getName() + ".json");
            }
            currentFile = selectedFile;
            save();
        }
    }

    // ================================
    // OPEN PROJECT
    // ================================
    public void openProject() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("JSON Files", "json"));
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            currentFile = chooser.getSelectedFile();
            load();
        }
    }

    // ================================
    // LOAD
    // ================================
    private void load() {
        try {
            Workspace workspace = mapper.readValue(currentFile, Workspace.class);
            // Reconstruct parent relationships
            reconstructParents(workspace, null);
            
            // Replace the repository's workspace children
            Workspace currentWorkspace = repository.getWorkspace();
            currentWorkspace.getChildren().clear();
            for (var child : workspace.getChildren()) {
                currentWorkspace.addChild(child);
            }
            hasUnsavedChanges = false;
        } catch (IOException e) {
            showError("Greška prilikom učitavanja projekta!");
            e.printStackTrace();
        }
    }
    
    private void reconstructParents(raf.graffito.dsw.core.graff.component.GraffNode node, raf.graffito.dsw.core.graff.component.GraffNode parent) {
        node.setParent(parent);
        if (node instanceof raf.graffito.dsw.core.graff.component.GraffNodeComposite) {
            raf.graffito.dsw.core.graff.component.GraffNodeComposite composite = 
                (raf.graffito.dsw.core.graff.component.GraffNodeComposite) node;
            for (var child : composite.getChildren()) {
                reconstructParents(child, node);
            }
        }
    }

    // ================================
    // SAVE AS TEMPLATE
    // ================================
    public void saveAsTemplate() {
        // Create resources directory if it doesn't exist
        File resourcesDir = new File("resources");
        if (!resourcesDir.exists()) {
            resourcesDir.mkdirs();
        }

        JFileChooser chooser = new JFileChooser(resourcesDir);
        chooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("JSON Files", "json"));
        chooser.setDialogTitle("Sačuvaj šablon");
        if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = chooser.getSelectedFile();
            // Ensure .json extension
            if (!selectedFile.getName().toLowerCase().endsWith(".json")) {
                selectedFile = new File(selectedFile.getParent(), selectedFile.getName() + ".json");
            }
            try {
                Workspace workspace = repository.getWorkspace();
                mapper.writeValue(selectedFile, workspace);
                JOptionPane.showMessageDialog(null, "Šablon je uspešno sačuvan!", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                showError("Greška prilikom čuvanja šablona!");
                e.printStackTrace();
            }
        }
    }

    // ================================
    // LOAD TEMPLATE
    // ================================
    public void loadTemplate() {
        // Create resources directory if it doesn't exist
        File resourcesDir = new File("resources");
        if (!resourcesDir.exists()) {
            resourcesDir.mkdirs();
        }

        JFileChooser chooser = new JFileChooser(resourcesDir);
        chooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("JSON Files", "json"));
        chooser.setDialogTitle("Učitaj šablon");
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File templateFile = chooser.getSelectedFile();
            try {
                Workspace templateWorkspace = mapper.readValue(templateFile, Workspace.class);
                // Reconstruct parent relationships
                reconstructParents(templateWorkspace, null);
                
                // Load template into current project
                Workspace currentWorkspace = repository.getWorkspace();
                // If workspace is empty or user wants to merge, add template children
                for (var child : templateWorkspace.getChildren()) {
                    currentWorkspace.addChild(child);
                }
                hasUnsavedChanges = true;
                JOptionPane.showMessageDialog(null, "Šablon je uspešno učitan!", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                showError("Greška prilikom učitavanja šablona!");
                e.printStackTrace();
            }
        }
    }

    public boolean hasCurrentFile() {
        return currentFile != null;
    }

    public File getCurrentFile() {
        return currentFile;
    }

    public void setCurrentFile(File file) {
        this.currentFile = file;
    }

    public void markChanged() {
        this.hasUnsavedChanges = true;
    }

    public boolean hasUnsavedChanges() {
        return hasUnsavedChanges;
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(null, message, "Greška", JOptionPane.ERROR_MESSAGE);
    }
}
