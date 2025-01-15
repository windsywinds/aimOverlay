package com.windsy;


import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Shortcuts {

    private final Crosshair crosshair;
    private final Settings settings;
    private final Debug debug;
    private boolean isSettingsOverlayVisible = true;
    private boolean isDebugOverlayVisible = false;

    public Shortcuts(Crosshair crosshair, Settings settings, Debug debug) {
        this.crosshair = crosshair;
        this.settings = settings;
        this.debug = debug;


        // Disable JNativeHook logging
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);
        logger.setUseParentHandlers(false);

        try {
            // Register the global key listener
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException e) {
            e.printStackTrace();
            System.exit(1);
        }

        // Add a global key listener and define hotkeys

        //need to bind x and c to only register if F2 settings is displayed. See: F3 unregister hook?
        GlobalScreen.addNativeKeyListener(new NativeKeyListener() {
            @Override
            public void nativeKeyPressed(NativeKeyEvent e) {
                int keyCode = e.getKeyCode();
                // Handle F2 to toggle the settings overlay
                if (keyCode == NativeKeyEvent.VC_F2) {
                    settings.toggleSettings();
                    isSettingsOverlayVisible = !isSettingsOverlayVisible;
                }
                // Handle C to toggle the crosshair size
                else if (keyCode == NativeKeyEvent.VC_C && isSettingsOverlayVisible) {
                    crosshair.cycleSize(); // Cycle through crosshair sizes
                }
                // Handle X to toggle the crosshair style
                else if (keyCode == NativeKeyEvent.VC_X && isSettingsOverlayVisible) {
                    crosshair.cycleStyle();
                }
                // Handle F4 to toggle the crosshair overlay
                else if (keyCode == NativeKeyEvent.VC_F4) {
                    crosshair.toggleCrosshair();
                }
                // Handle F3 to exit the application
                else if (keyCode == NativeKeyEvent.VC_F3) {
                    try {
                        GlobalScreen.unregisterNativeHook(); // Unregister the hook before exit
                    } catch (NativeHookException ex) {
                        ex.printStackTrace();
                    }
                    System.exit(0);
                }
                //F8 for debug overlay
                else if (keyCode == NativeKeyEvent.VC_F8) {
                    debug.toggleDebug();
                    isDebugOverlayVisible = !isDebugOverlayVisible;
                }
            }

            @Override
            public void nativeKeyReleased(NativeKeyEvent e) {
                // No actions needed for key release
            }

            @Override
            public void nativeKeyTyped(NativeKeyEvent e) {
                // No actions needed for key typed
            }
        });
    }
}
