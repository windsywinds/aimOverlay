# aimOverlay  
  
A crosshair overlay executable for windows made in Java

This application will create an always-on-top overlay of a predefined image that centers in the middle of the screen. The user can cycle through different crosshair styles, adjust the size, and toggle the overlay on/off.

# In-app global shortcuts

F2: Toggle the settings overlay on/off  
F3: Exit application  
F8: Toggle the debug overlay (not currently operative)  
X: Cycle through crosshair styles  
C: Increase crosshair size (resets at size 10)  

# Custom overlays

Users can create their own custom overlays by following the naming scheme of "crosshair" + number + ".png" e.g. "crosshair4.png"  
These can be placed in the /overlay directory alongside, 
or replacing the existing corsshair images. 
There must always be a "crosshair1.png" as this is what the application will default to.
