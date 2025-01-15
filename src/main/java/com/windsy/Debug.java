package com.windsy;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;
import java.time.LocalDateTime;


public class Debug {
    private JWindow frame;         // Ref to the overlay frame
    private boolean isOverlayVisible;
    public String message;

    // Save to debug.log file
    public static CompletableFuture<Void> saveLogToFileAsync(String message) {
        System.err.println(message);
        return CompletableFuture.runAsync(() -> {
            LocalDateTime dateTimeLog = LocalDateTime.now();
            String logEntry = dateTimeLog + " - " + message + System.lineSeparator();
            try {
                String appDir = System.getProperty("user.dir");
                File logFile = new File(appDir + File.separator + "debug.log");
                try (FileOutputStream fileOutputStream = new FileOutputStream(logFile, true)) {
                    fileOutputStream.write(logEntry.getBytes());
                }
                System.out.println("Debug log entry added");
            } catch (IOException e) {
                System.err.println("Error saving log file: " + e.getMessage());
            }
        });
    }


    // Overlay for on screen debugging - not in use
    public Debug() {

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

        // Add text information
        JLabel textLabel = new JLabel(
                "<html>" +
                        "DEBUGGER" +
                        "<br>" +
                        "F8: Hide debugger" +
                        "</html>",
                SwingConstants.CENTER);
        // Define text styling
        textLabel.setForeground(Color.WHITE);
        textLabel.setFont(new Font("Arial", Font.BOLD, 16));
        textLabel.setHorizontalAlignment(SwingConstants.CENTER);
        textLabel.setVerticalAlignment(SwingConstants.TOP);

        // Add the text label to the panel
        panel.add(textLabel, BorderLayout.CENTER);

        // Add the panel to the frame
        frame.getContentPane().add(panel);

        // Sets the size and position of the frame
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int frameWidth = 500;  // Customize the width of the overlay
        int frameHeight = 250; // Customize the height of the overlay
        int x = (screenSize.width - frameWidth) / 1;
        int y = (screenSize.height - frameHeight) / 2;
        frame.setBounds(x, y, frameWidth, frameHeight);

        // Do not show debug overlay on load
        isOverlayVisible = false;
        frame.setVisible(false);
    }


    public void toggleDebug() {
        frame.setVisible(!isOverlayVisible);
        isOverlayVisible = !isOverlayVisible; // Toggle the state
    }

    public JWindow getFrame() {
        return frame;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Debug());
    }
}
