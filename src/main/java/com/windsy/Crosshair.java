package com.windsy;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Crosshair {

    private JWindow frame;         // Reference to the overlay frame
    private JLabel label;          // Reference to the image label
    private boolean isImageShown;  // Track if the image is currently displayed
    private ImageIcon originalImageIcon; // Store the original image
    private double sizeFactor;     // Scale factor to adjust image size
    private int imagePath;         // Crosshair style number

    public Crosshair(Integer imagePath, double sizeFactor) {
        this.sizeFactor = sizeFactor;
        this.imagePath = imagePath;

        // Path to the overlay image (using File.separator for cross-platform compatibility)
        String appDir = System.getProperty("user.dir");
        File imageFile = new File(appDir + File.separator + "overlays" + File.separator + "crosshair" + imagePath + ".png");


        // Check if the file exists
        if (!imageFile.exists()) {
            try {
                Debug.saveLogToFileAsync("Error: File not found at " + imageFile.getAbsolutePath()).join();
            } catch (Exception e) {
                System.err.println("Error while saving log: " + e.getMessage());
            }
            System.exit(1);
        }

        // Load the original image
        originalImageIcon = new ImageIcon(imageFile.getAbsolutePath());

        // Create overlay frame
        frame = new JWindow();
        frame.setAlwaysOnTop(true); // Stay on top
        frame.setBackground(new Color(0, 0, 0, 0)); // Transparent background

        // Scale the image and set up the frame
        updateImageAndFrame();

        // Initially display the image
        isImageShown = true;
        frame.setVisible(true);

        // Call method to load user's previous settings from config file
        loadSettingsFromConfig();
    }

    private Properties loadConfig() {
        Properties config = new Properties();
        try {
            String appDir = System.getProperty("user.dir");
            File configFile = new File(appDir + File.separator + "config.ini");

            // Check if the config file exists
            // if none, generate new config??
            if (!configFile.exists()) {
                try {
                    Debug.saveLogToFileAsync("Error: Config file not found at " + configFile.getAbsolutePath()).join();
                } catch (Exception e) {
                    System.err.println("Error while saving log: " + e.getMessage());
                }
                return config; // Return empty config if the file is not found
            }

            // Load the properties from the config file
            config.load(new java.io.FileInputStream(configFile));
        } catch (IOException e) {
            System.err.println("Error reading config file: " + e.getMessage());
        }
        return config;
    }


    // Load settings from the config.ini file
    private void loadSettingsFromConfig() {
        Properties config = loadConfig();
        String crosshairStyle = config.getProperty("crossHairStyle", "2"); // Default to 2
        String crosshairSize = config.getProperty("crossHairSize", "3");  // Default to 3

        try {
            // Apply these settings to the crosshair
            this.imagePath = Integer.parseInt(crosshairStyle);
            this.sizeFactor = Double.parseDouble(crosshairSize);
        } catch (NumberFormatException e) {
            try {
                Debug.saveLogToFileAsync("Error: Invalid config values, using default settings.").join();
            } catch (Exception err) {
                System.err.println("Error while saving log: " + err.getMessage());
            }
            this.imagePath = 2;  // Default style
            this.sizeFactor = 3;  // Default size
        }

        // Update the crosshair appearance
        updateImageAndFrame();
    }

    // Save the settings to config.ini file
    private void saveSettingsToConfig() {
        Properties config = new Properties();
        try {
            String appDir = System.getProperty("user.dir");
            FileOutputStream configFile = new FileOutputStream(appDir + File.separator + "config.ini");

            //set size factor as single not double
            String sizeFactorString = (sizeFactor == (int) sizeFactor)
                    ? String.valueOf((int) sizeFactor)
                    : String.valueOf(sizeFactor);
            // Save the current crosshair style and size to the config
            config.setProperty("crossHairStyle", String.valueOf(imagePath));
            config.setProperty("crossHairSize", sizeFactorString);

            // Store the properties to the file
            config.store(configFile, "Crosshair settings");
            System.out.println("Updating settings: Style=" + imagePath + ", Size=" + sizeFactor);

            configFile.close();
        } catch (IOException e) {
            System.err.println("Error saving config.ini file: " + e.getMessage());
        }
    }

    // Toggle crosshair on or off
    public void toggleCrosshair() {
        if (isImageShown) {
            frame.getContentPane().remove(label);
        } else {
            frame.getContentPane().add(label);
        }
        isImageShown = !isImageShown; // Toggle the state
        frame.revalidate();          // Revalidate to apply changes
        frame.repaint();             // Repaint to update the display
    }

    // Updates the size of the crosshair image based on the sizeFactor selected
    public void changeSize(double newSizeFactor) {
        this.sizeFactor = newSizeFactor;
        updateImageAndFrame();
    }

    // Updates the scaled image and repositions the frame.
    private void updateImageAndFrame() {
        // Scale the image based on the sizeFactor
        Image scaledImage = originalImageIcon.getImage().getScaledInstance(
                (int) (originalImageIcon.getIconWidth() * sizeFactor / 5),
                (int) (originalImageIcon.getIconHeight() * sizeFactor / 5),
                Image.SCALE_SMOOTH);

        ImageIcon scaledImageIcon = new ImageIcon(scaledImage);

        // Update the label
        if (label == null) {
            label = new JLabel(scaledImageIcon);
            frame.getContentPane().add(label);
        } else {
            label.setIcon(scaledImageIcon);
        }

        // Center the frame on the screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - scaledImageIcon.getIconWidth()) / 2;
        int y = (screenSize.height - scaledImageIcon.getIconHeight()) / 2;
        frame.setBounds(x, y, scaledImageIcon.getIconWidth(), scaledImageIcon.getIconHeight());

        // Revalidate and repaint
        frame.revalidate();
        frame.repaint();
        saveSettingsToConfig();
    }

    public void cycleSize() {
        // Increment the sizeFactor and wrap around if it exceeds 10
        sizeFactor = (sizeFactor % 10) + 1;

        // Scale the image based on the new sizeFactor
        Image scaledImage = originalImageIcon.getImage().getScaledInstance(
                (int) (originalImageIcon.getIconWidth() * sizeFactor / 5),
                (int) (originalImageIcon.getIconHeight() * sizeFactor / 5),
                Image.SCALE_SMOOTH);

        // Update the JLabel with the new image
        ImageIcon scaledImageIcon = new ImageIcon(scaledImage);
        label.setIcon(scaledImageIcon);

        // Update the frame size and position
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - scaledImageIcon.getIconWidth()) / 2;
        int y = (screenSize.height - scaledImageIcon.getIconHeight()) / 2;
        frame.setBounds(x, y, scaledImageIcon.getIconWidth(), scaledImageIcon.getIconHeight());

        // Revalidate and repaint to apply changes
        frame.revalidate();
        frame.repaint();
        saveSettingsToConfig();
    }

    public void cycleStyle() {
        // Increment the imagePath and loop if it exceeds 10
        imagePath = (imagePath % 10) + 1; // Loop through styles 1 to 10

        // Load the new image
        String appDir = System.getProperty("user.dir");
        File imageFile = new File(appDir + File.separator + "overlays" + File.separator + "crosshair" + imagePath + ".png");

        // Check if the file exists
        if (!imageFile.exists()) {
            try {
                Debug.saveLogToFileAsync("Error: Crosshair style " + imagePath + " not found at " + imageFile.getAbsolutePath()).join();
            } catch (Exception e) {
                System.err.println("Error while saving log: " + e.getMessage());
            }
            imagePath = 1; // Reset to the default style
            imageFile = new File(appDir + File.separator + "overlays" + File.separator + "crosshair" + imagePath + ".png");
        }

        // Ensure the fallback file exists
        if (!imageFile.exists()) {
            try {
                Debug.saveLogToFileAsync("Critical Error: Default crosshair style file not found at " + imageFile.getAbsolutePath()).join();
            } catch (Exception e) {
                System.err.println("Error while saving log: " + e.getMessage());
            }
            return;
        }

        // Update the originalImageIcon with the new style
        originalImageIcon = new ImageIcon(imageFile.getAbsolutePath());

        // Update the image and frame
        updateImageAndFrame();
    }

    public JWindow getFrame() {
        return frame;
    }
}
