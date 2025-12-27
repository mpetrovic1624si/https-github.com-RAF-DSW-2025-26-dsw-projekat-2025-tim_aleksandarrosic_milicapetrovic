package view;


import app.model.SlideObserver;
import app.model.SlideElement;
import app.model.ImageElement;
import app.model.LogoElement;
import model.TextElement;
import app.view.LogoPainter;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;

public class SlideView extends JPanel implements SlideObserver {

    private model.Slide slide;
    private double zoomLevel = 1.0;
    private static final double MIN_ZOOM = 0.25;
    private static final double MAX_ZOOM = 4.0;
    private static final int SLIDE_WIDTH = 800;
    private static final int SLIDE_HEIGHT = 600;

    public SlideView(model.Slide slide) {
        this.slide = slide;
        slide.addObserver(this);
        setPreferredSize(new Dimension(SLIDE_WIDTH, SLIDE_HEIGHT));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        // Enable anti-aliasing
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Apply zoom transformation
        AffineTransform originalTransform = g2d.getTransform();
        AffineTransform zoomTransform = new AffineTransform();
        zoomTransform.scale(zoomLevel, zoomLevel);
        g2d.transform(zoomTransform);
        
        // Draw slide background
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, SLIDE_WIDTH, SLIDE_HEIGHT);
        
        // Draw all elements
        for (SlideElement element : slide.getElements()) {
            drawElement(g2d, element);
        }
        
        // Restore original transform
        g2d.setTransform(originalTransform);
    }
    
    private void drawElement(Graphics2D g2d, SlideElement element) {
        AffineTransform originalTransform = g2d.getTransform();
        
        // Apply rotation transformation
        if (element.getRotation() != 0) {
            double rotationRad = Math.toRadians(element.getRotation());
            AffineTransform rotationTransform = new AffineTransform();
            rotationTransform.rotate(rotationRad, 
                element.getX() + element.getWidth() / 2.0, 
                element.getY() + element.getHeight() / 2.0);
            g2d.transform(rotationTransform);
        }
        
        // Draw element based on type
        if (element instanceof ImageElement) {
            drawImageElement(g2d, (ImageElement) element);
        } else if (element instanceof TextElement) {
            drawTextElement(g2d, (TextElement) element);
        } else if (element instanceof LogoElement) {
            drawLogoElement(g2d, (LogoElement) element);
        }
        
        // Draw selection rectangle if selected
        if (element.isSelected()) {
            g2d.setColor(Color.BLUE);
            g2d.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 
                0, new float[]{5}, 0));
            g2d.drawRect(element.getX(), element.getY(), element.getWidth(), element.getHeight());
        }
        
        // Restore transform
        g2d.setTransform(originalTransform);
    }
    
    private void drawImageElement(Graphics2D g2d, ImageElement imgElement) {
        if (imgElement.getImagePath() != null) {
            try {
                File imgFile = new File(imgElement.getImagePath());
                if (imgFile.exists()) {
                    BufferedImage image = ImageIO.read(imgFile);
                    if (image != null) {
                        g2d.drawImage(image, imgElement.getX(), imgElement.getY(), 
                            imgElement.getWidth(), imgElement.getHeight(), null);
                        return;
                    }
                }
            } catch (Exception e) {
                // Fall through to default rectangle
            }
        }
        
        // Default: draw rectangle placeholder
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.fillRect(imgElement.getX(), imgElement.getY(), 
            imgElement.getWidth(), imgElement.getHeight());
        g2d.setColor(Color.BLACK);
        g2d.drawRect(imgElement.getX(), imgElement.getY(), 
            imgElement.getWidth(), imgElement.getHeight());
    }
    
    private void drawTextElement(Graphics2D g2d, TextElement textElement) {
        g2d.setColor(Color.BLACK);
        Font font = new Font(textElement.getFontName(), Font.PLAIN, textElement.getFontSize());
        g2d.setFont(font);
        
        // Draw text with word wrapping
        String text = textElement.getText();
        if (text != null && !text.isEmpty()) {
            FontMetrics fm = g2d.getFontMetrics();
            int x = textElement.getX();
            int y = textElement.getY() + fm.getAscent();
            int maxWidth = textElement.getWidth();
            
            String[] words = text.split(" ");
            StringBuilder currentLine = new StringBuilder();
            
            for (String word : words) {
                String testLine = currentLine.toString() + (currentLine.length() > 0 ? " " : "") + word;
                int width = fm.stringWidth(testLine);
                
                if (width > maxWidth && currentLine.length() > 0) {
                    g2d.drawString(currentLine.toString(), x, y);
                    y += fm.getHeight();
                    currentLine = new StringBuilder(word);
                } else {
                    currentLine = new StringBuilder(testLine);
                }
            }
            if (currentLine.length() > 0) {
                g2d.drawString(currentLine.toString(), x, y);
            }
        }
    }
    
    private void drawLogoElement(Graphics2D g2d, LogoElement logoElement) {
        LogoPainter.paint(g2d, logoElement);
    }
    
    public void setZoomLevel(double zoom) {
        if (zoom >= MIN_ZOOM && zoom <= MAX_ZOOM) {
            this.zoomLevel = zoom;
            repaint();
        }
    }
    
    public double getZoomLevel() {
        return zoomLevel;
    }
    
    public void zoomIn() {
        setZoomLevel(zoomLevel * 1.1);
    }
    
    public void zoomOut() {
        setZoomLevel(zoomLevel / 1.1);
    }

    @Override
    public void slideChanged() {
        repaint();
    }
}