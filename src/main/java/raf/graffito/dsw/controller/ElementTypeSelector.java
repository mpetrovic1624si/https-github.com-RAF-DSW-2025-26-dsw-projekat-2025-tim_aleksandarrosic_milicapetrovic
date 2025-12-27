package raf.graffito.dsw.controller;

import javax.swing.*;

public class ElementTypeSelector {
    
    public enum ElementType {
        IMAGE, TEXT, LOGO
    }
    
    private static ElementType selectedType = ElementType.IMAGE;
    
    public static ElementType getSelectedType() {
        return selectedType;
    }
    
    public static void setSelectedType(ElementType type) {
        selectedType = type;
    }
    
    public static ElementType showTypeSelector() {
        String[] options = {"Image", "Text", "Logo"};
        int choice = JOptionPane.showOptionDialog(
            null,
            "Select element type:",
            "Add Element",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]
        );
        
        switch (choice) {
            case 0: return ElementType.IMAGE;
            case 1: return ElementType.TEXT;
            case 2: return ElementType.LOGO;
            default: return ElementType.IMAGE;
        }
    }
}

