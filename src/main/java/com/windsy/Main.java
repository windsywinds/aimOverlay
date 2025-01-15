package com.windsy;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Main {

    public static void main(String[] args) {

        // Global handler to log app errors
        Thread.setDefaultUncaughtExceptionHandler((thread, throwable) -> {
            try {
                Debug.saveLogToFileAsync("Unhandled exception in thread " + thread.getName() + ": " + throwable.getMessage()).join();
            } catch (Exception e) {
                System.err.println("Error while saving log: " + e.getMessage());
            }
        });

        try {
            // Load settings from config.ini
            Properties config = loadConfig();

            // Get user values from config.ini, default to specified values if none found
            int crossHairStyle = Integer.parseInt(config.getProperty("crossHairStyle", "1"));
            int crossHairSize = Integer.parseInt(config.getProperty("crossHairSize", "5"));

            Crosshair crosshair = new Crosshair(crossHairStyle, crossHairSize);
            Settings settings = new Settings();
            Debug debug = new Debug();

            // Check if system tray is supported
            if (!SystemTray.isSupported()) {
                Debug.saveLogToFileAsync("System tray not supported").join();
                System.exit(1);
            }

            // Handle tray icon
            TrayIcon trayIcon = new TrayIcon(Toolkit.getDefaultToolkit().getImage("overlays/logo.png"), "Overlay App");
            trayIcon.setImageAutoSize(true);
            PopupMenu popupMenu = new PopupMenu();
            MenuItem exitItem = new MenuItem("Exit");
            exitItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.exit(0); // Exit the application when clicked
                }
            });

            popupMenu.add(exitItem); // Add  exit item to the popup menu
            trayIcon.setPopupMenu(popupMenu); // Set the popup menu to the tray icon

            try {
                SystemTray.getSystemTray().add(trayIcon); // Add the tray icon to the system tray
            } catch (AWTException e) {
                Debug.saveLogToFileAsync("Error adding tray icon: " + e.getMessage()).join();
                System.exit(1);
            }

            // Initialize global shortcuts class
            new Shortcuts(crosshair, settings, debug);

        } catch (Exception e) {
            // Catch any exceptions that might occur in the main thread
            Debug.saveLogToFileAsync("Critical error: " + e.getMessage()).join();
            System.exit(1);
        }
    }

    // Method to load settings from config.ini file
    private static Properties loadConfig() {
        Properties config = new Properties();
        try {
            // Define  path to the config file (same directory as logo.png)
            String appDir = System.getProperty("user.dir");
            FileInputStream configFile = new FileInputStream(appDir + File.separator + "config.ini");

            // Load the properties file
            config.load(configFile);
        } catch (IOException e) {
            try {
                Debug.saveLogToFileAsync("Error loading config.ini file: " + e.getMessage()).join();
            } catch (Exception logException) {
                System.err.println("Error while saving log: " + logException.getMessage());
            }
        }
        return config;
    }
}
