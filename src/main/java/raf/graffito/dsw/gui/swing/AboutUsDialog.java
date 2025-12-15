package raf.graffito.dsw.gui.swing;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class AboutUsDialog extends JDialog {

    public AboutUsDialog() {
        setTitle("About Us");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setModal(true);

        // Panel sa vertikalnim rasporedom
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Dodavanje članova tima
        panel.add(createMemberPanel("/images/aleksandar.jpg", "Aleksandar Rosić", "RA 162/2024"));
        panel.add(Box.createRigidArea(new Dimension(0, 10))); // razmak između članova
        panel.add(createMemberPanel("/images/milica.jpg", "Milica Petrović", "RA 163/2024"));

        add(panel);
        setVisible(true);
    }

    private JPanel createMemberPanel(String imagePath, String name, String index) {
        JPanel memberPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));

        // Učitavanje slike
        ImageIcon icon;
        java.net.URL imageURL = getClass().getResource(imagePath);
        if (imageURL != null) {
            Image img = new ImageIcon(imageURL).getImage();
            Image scaled = img.getScaledInstance(60, 60, Image.SCALE_SMOOTH);
            icon = new ImageIcon(scaled);
        } else {
            // Prazan placeholder ako slika ne postoji
            icon = new ImageIcon(new BufferedImage(60, 60, BufferedImage.TYPE_INT_ARGB));
        }

        JLabel picLabel = new JLabel(icon);
        JLabel textLabel = new JLabel("<html><b>" + name + "</b><br>" + index + "</html>");

        memberPanel.add(picLabel);
        memberPanel.add(textLabel);

        return memberPanel;
    }
}
