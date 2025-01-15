package com.windsy;

import javax.swing.*;
import java.awt.*;

public class Settings {

    private JWindow frame;         // Ref to the overlay frame
    private boolean isOverlayVisible;

    public Settings() {

        frame = new JWindow();
        frame.setAlwaysOnTop(true); // Keep overlay on top

        // Create container panel with a background color
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(0, 0, 0, 150)); // Semi-transparent background color
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panel.setLayout(new BorderLayout());

        // Add text instructions to the overlay
        JLabel textLabel = new JLabel(
                "<html>" +
                        "aimOverlay v1.0" +
                        "<br>" + "<br>" + "<br>" +

                        "X: Change crosshair type" +
                        "<br>" +
                        "C: Change crosshair size" +
                        "<br>" + "<br>" +

                        "F2: Toggle Settings Overlay" +
                        "<br>" +
                        "F3: Toggle Crosshair Overlay" +
                        "<br>" + "<br>" +

                        "F4: Exit Application" +
                        "</html>",
                SwingConstants.CENTER);
        // Define text styling
        textLabel.setForeground(Color.WHITE);
        textLabel.setFont(new Font("Arial", Font.BOLD, 16));
        textLabel.setHorizontalAlignment(SwingConstants.CENTER);
        textLabel.setVerticalAlignment(SwingConstants.CENTER);

        // Add the text label to the panel
        panel.add(textLabel, BorderLayout.CENTER);




        // Add the panel to the frame
        frame.getContentPane().add(panel);

        // Sets the size and position of the frame
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int frameWidth = 300;  // Customize the width of the overlay
        int frameHeight = 250; // Customize the height of the overlay
        int x = (screenSize.width - frameWidth) / 1;
        int y = (screenSize.height - frameHeight) / 8;
        frame.setBounds(x, y, frameWidth, frameHeight);

        // Display the overlay on load - add config setting to only show on first load
        // I should put this on a timer to fade out if not turned off?
        isOverlayVisible = true;
        frame.setVisible(true);
    }


    public void toggleSettings() {
        frame.setVisible(!isOverlayVisible);
        isOverlayVisible = !isOverlayVisible; // Toggle the state
    }

    public JWindow getFrame() {
        return frame;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Settings());
    }
}
