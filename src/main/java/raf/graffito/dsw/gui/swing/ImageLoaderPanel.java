package raf.graffito.dsw.gui.swing;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

public class ImageLoaderPanel extends JPanel {
    private JPanel thumbnailContainer;
    private List<File> loadedImages;
    private static final int THUMBNAIL_SIZE = 120;
    private static final int PANEL_WIDTH = 150;
    private static final int PANEL_HEIGHT = 400;

    public ImageLoaderPanel() {
        loadedImages = new ArrayList<>();
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setBorder(BorderFactory.createTitledBorder("Učitane slike"));

        // Button to load images
        JButton loadButton = new JButton("Učitaj Sliku");
        loadButton.addActionListener(e -> loadImages());

        // Container for thumbnails with scroll
        thumbnailContainer = new JPanel();
        thumbnailContainer.setLayout(new BoxLayout(thumbnailContainer, BoxLayout.Y_AXIS));
        thumbnailContainer.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(thumbnailContainer);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT - 50));
        scrollPane.setBorder(null);

        add(loadButton, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void loadImages() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setMultiSelectionEnabled(true);
        fileChooser.setFileFilter(new FileNameExtensionFilter(
            "Image Files", "jpg", "jpeg", "png", "gif", "bmp"));
        fileChooser.setDialogTitle("Odaberi slike");

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File[] selectedFiles = fileChooser.getSelectedFiles();
            for (File file : selectedFiles) {
                if (!loadedImages.contains(file)) {
                    loadedImages.add(file);
                    addThumbnail(file);
                }
            }
            revalidate();
            repaint();
        }
    }

    private void addThumbnail(File imageFile) {
        try {
            BufferedImage originalImage = ImageIO.read(imageFile);
            if (originalImage == null) {
                return;
            }

            // Create thumbnail
            ImageIcon thumbnailIcon = createThumbnail(originalImage);
            JLabel thumbnailLabel = new JLabel(thumbnailIcon);
            thumbnailLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            thumbnailLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            thumbnailLabel.setToolTipText(imageFile.getName());

            // Make thumbnail clickable to select image
            thumbnailLabel.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    fireImageSelected(imageFile);
                }
            });

            thumbnailContainer.add(thumbnailLabel);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, 
                "Greška pri učitavanju slike: " + imageFile.getName(), 
                "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }

    private ImageIcon createThumbnail(BufferedImage originalImage) {
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();
        
        // Calculate scaling to fit thumbnail size while maintaining aspect ratio
        double scale = Math.min((double) THUMBNAIL_SIZE / width, 
                               (double) THUMBNAIL_SIZE / height);
        int thumbWidth = (int) (width * scale);
        int thumbHeight = (int) (height * scale);

        Image scaledImage = originalImage.getScaledInstance(
            thumbWidth, thumbHeight, Image.SCALE_SMOOTH);
        
        BufferedImage thumbnail = new BufferedImage(
            THUMBNAIL_SIZE, THUMBNAIL_SIZE, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = thumbnail.createGraphics();
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, THUMBNAIL_SIZE, THUMBNAIL_SIZE);
        
        // Center the scaled image
        int x = (THUMBNAIL_SIZE - thumbWidth) / 2;
        int y = (THUMBNAIL_SIZE - thumbHeight) / 2;
        g2d.drawImage(scaledImage, x, y, null);
        g2d.dispose();

        return new ImageIcon(thumbnail);
    }

    private void fireImageSelected(File imageFile) {
        // Update ImageLoaderManager
        raf.graffito.dsw.controller.ImageLoaderManager.getInstance().setSelectedImage(imageFile);
        
        // Highlight the selected thumbnail
        for (Component comp : thumbnailContainer.getComponents()) {
            if (comp instanceof JLabel) {
                JLabel label = (JLabel) comp;
                if (label.getToolTipText().equals(imageFile.getName())) {
                    label.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
                } else {
                    label.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                }
            }
        }
        repaint();
    }

    public List<File> getLoadedImages() {
        return new ArrayList<>(loadedImages);
    }

    public File getSelectedImage() {
        // Return the first selected image, or null
        for (Component comp : thumbnailContainer.getComponents()) {
            if (comp instanceof JLabel) {
                JLabel label = (JLabel) comp;
                if (label.getBorder() instanceof javax.swing.border.LineBorder) {
                    String fileName = label.getToolTipText();
                    return loadedImages.stream()
                        .filter(f -> f.getName().equals(fileName))
                        .findFirst()
                        .orElse(null);
                }
            }
        }
        return null;
    }
}
