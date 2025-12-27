package raf.graffito.dsw.gui.swing;

import raf.graffito.dsw.controller.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import view.SlideView;

public class RightToolBar extends JToolBar {
    
    private ButtonGroup toolModeGroup;
    private JButton addModeButton;
    private JButton selectModeButton;
    private JButton moveModeButton;
    private JButton resizeModeButton;
    private JButton rotateModeButton;
    private JButton zoomInButton;
    private JButton zoomOutButton;
    private SlideView currentSlideView;
    
    public RightToolBar() {
        setOrientation(VERTICAL);
        setFloatable(false);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        toolModeGroup = new ButtonGroup();
        
        // Add Mode Button (Tree icon representation)
        addModeButton = createToolButton("Add", new AddToolModeAction(), "Add element mode");
        addModeButton.putClientProperty("buttonGroup", toolModeGroup);
        toolModeGroup.add(addModeButton);
        add(addModeButton);
        add(Box.createVerticalStrut(5));
        
        // Select Mode Button (Leaf/starburst representation - using select icon)
        selectModeButton = createToolButton("Select", new SelectToolModeAction(), "Select elements mode");
        selectModeButton.putClientProperty("buttonGroup", toolModeGroup);
        toolModeGroup.add(selectModeButton);
        add(selectModeButton);
        add(Box.createVerticalStrut(5));
        
        // Move Mode Button
        moveModeButton = createToolButton("Move", new MoveToolModeAction(), "Move element mode");
        moveModeButton.putClientProperty("buttonGroup", toolModeGroup);
        toolModeGroup.add(moveModeButton);
        add(moveModeButton);
        add(Box.createVerticalStrut(5));
        
        // Resize Mode Button
        resizeModeButton = createToolButton("Resize", new ResizeToolModeAction(), "Resize element mode");
        resizeModeButton.putClientProperty("buttonGroup", toolModeGroup);
        toolModeGroup.add(resizeModeButton);
        add(resizeModeButton);
        add(Box.createVerticalStrut(5));
        
        // Rotate Mode Button (Infinity symbol representation)
        rotateModeButton = createToolButton("∞", new RotateToolModeAction(), "Rotate element mode");
        rotateModeButton.putClientProperty("buttonGroup", toolModeGroup);
        toolModeGroup.add(rotateModeButton);
        add(rotateModeButton);
        add(Box.createVerticalStrut(5));
        
        // Rotate 90° Left Button
        JButton rotateLeftButton = createToolButton("↺", new Rotate90LeftAction(), "Rotate 90° left");
        add(rotateLeftButton);
        add(Box.createVerticalStrut(5));
        
        // Rotate 90° Right Button
        JButton rotateRightButton = createToolButton("↻", new Rotate90RightAction(), "Rotate 90° right");
        add(rotateRightButton);
        add(Box.createVerticalStrut(10));
        
        // Separator
        addSeparator();
        add(Box.createVerticalStrut(5));
        
        // Zoom Out Button (-)
        zoomOutButton = new JButton("-");
        zoomOutButton.setToolTipText("Zoom out");
        zoomOutButton.setPreferredSize(new Dimension(40, 40));
        zoomOutButton.setMaximumSize(new Dimension(40, 40));
        zoomOutButton.setMinimumSize(new Dimension(40, 40));
        zoomOutButton.addActionListener(e -> {
            if (currentSlideView != null) {
                currentSlideView.zoomOut();
            }
        });
        add(zoomOutButton);
        add(Box.createVerticalStrut(5));
        
        // Zoom In Button (+) with "select" label below
        JPanel zoomPanel = new JPanel();
        zoomPanel.setLayout(new BoxLayout(zoomPanel, BoxLayout.Y_AXIS));
        zoomPanel.setPreferredSize(new Dimension(60, 60));
        zoomPanel.setMaximumSize(new Dimension(60, 60));
        zoomPanel.setBackground(null);
        zoomPanel.setOpaque(false);
        
        zoomInButton = new JButton("+");
        zoomInButton.setPreferredSize(new Dimension(40, 40));
        zoomInButton.setMaximumSize(new Dimension(40, 40));
        zoomInButton.setMinimumSize(new Dimension(40, 40));
        zoomInButton.setToolTipText("Zoom in");
        zoomInButton.addActionListener(e -> {
            if (currentSlideView != null) {
                currentSlideView.zoomIn();
            }
        });
        
        JLabel selectLabel = new JLabel("select");
        selectLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        selectLabel.setFont(new Font(selectLabel.getFont().getName(), Font.PLAIN, 9));
        
        zoomPanel.add(zoomInButton);
        zoomPanel.add(selectLabel);
        add(zoomPanel);
        add(Box.createVerticalStrut(5));
        
        // Zoom (magnifying glass) Button
        JButton zoomButton = new JButton("🔍");
        zoomButton.setToolTipText("Reset zoom");
        zoomButton.setPreferredSize(new Dimension(40, 40));
        zoomButton.setMaximumSize(new Dimension(40, 40));
        zoomButton.setMinimumSize(new Dimension(40, 40));
        zoomButton.addActionListener(e -> {
            if (currentSlideView != null) {
                currentSlideView.setZoomLevel(1.0);
            }
        });
        add(zoomButton);
        
        add(Box.createVerticalGlue());
    }
    
    private JButton createToolButton(String text, AbstractAction action, String tooltip) {
        JButton button = new JButton(action);
        button.setText(text);
        button.setToolTipText(tooltip);
        button.setPreferredSize(new Dimension(60, 60));
        button.setMaximumSize(new Dimension(60, 60));
        button.setMinimumSize(new Dimension(60, 60));
        button.setFocusPainted(false);
        return button;
    }
    
    public void updateToolModeSelection(String modeName) {
        // Reset all selections
        toolModeGroup.clearSelection();
        
        // Select appropriate button based on mode
        switch (modeName) {
            case "Add":
                addModeButton.setSelected(true);
                break;
            case "Select":
                selectModeButton.setSelected(true);
                break;
            case "Move":
                moveModeButton.setSelected(true);
                break;
            case "Resize":
                resizeModeButton.setSelected(true);
                break;
            case "Rotate":
                rotateModeButton.setSelected(true);
                break;
        }
    }
    
    public void setCurrentSlideView(SlideView slideView) {
        this.currentSlideView = slideView;
    }
}

