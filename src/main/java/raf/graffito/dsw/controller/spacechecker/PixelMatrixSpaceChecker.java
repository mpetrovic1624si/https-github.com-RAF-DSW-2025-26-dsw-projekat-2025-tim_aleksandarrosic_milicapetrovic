package raf.graffito.dsw.controller.spacechecker;

import app.model.SlideElement;
import model.Slide;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Način 2: Slajd posmatrati kao binarnu matricu piksela (0=slobodan, 1=zauzet piksel), 
 * čime se rešava problem preklapanja iz 1. načina
 */
public class PixelMatrixSpaceChecker extends SpaceChecker {
    
    private static final int SLIDE_WIDTH = 800;
    private static final int SLIDE_HEIGHT = 600;
    
    @Override
    public boolean hasEnoughSpace(Slide slide, SlideElement newElement) {
        // Create binary matrix representing occupied pixels
        BufferedImage matrix = new BufferedImage(SLIDE_WIDTH, SLIDE_HEIGHT, BufferedImage.TYPE_BYTE_BINARY);
        Graphics2D g2d = matrix.createGraphics();
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, SLIDE_WIDTH, SLIDE_HEIGHT);
        g2d.setColor(Color.BLACK);
        
        // Mark occupied pixels from existing elements
        for (SlideElement el : slide.getElements()) {
            int x = Math.max(0, Math.min(el.getX(), SLIDE_WIDTH - 1));
            int y = Math.max(0, Math.min(el.getY(), SLIDE_HEIGHT - 1));
            int w = Math.max(1, Math.min(el.getWidth(), SLIDE_WIDTH - x));
            int h = Math.max(1, Math.min(el.getHeight(), SLIDE_HEIGHT - y));
            g2d.fillRect(x, y, w, h);
        }
        
        // Count free pixels after adding new element
        int newX = Math.max(0, Math.min(newElement.getX(), SLIDE_WIDTH - 1));
        int newY = Math.max(0, Math.min(newElement.getY(), SLIDE_HEIGHT - 1));
        int newW = Math.max(1, Math.min(newElement.getWidth(), SLIDE_WIDTH - newX));
        int newH = Math.max(1, Math.min(newElement.getHeight(), SLIDE_HEIGHT - newY));
        
        // Mark new element area
        g2d.fillRect(newX, newY, newW, newH);
        
        // Count free (white) pixels
        int freePixels = 0;
        for (int x = 0; x < SLIDE_WIDTH; x++) {
            for (int y = 0; y < SLIDE_HEIGHT; y++) {
                if (matrix.getRGB(x, y) == Color.WHITE.getRGB()) {
                    freePixels++;
                }
            }
        }
        
        double totalPixels = SLIDE_WIDTH * SLIDE_HEIGHT;
        double freeRatio = (double) freePixels / totalPixels;
        
        g2d.dispose();
        
        return freeRatio >= MIN_FREE_SPACE_RATIO;
    }
}

