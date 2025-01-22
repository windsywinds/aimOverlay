[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]

<div align="center">

<h1 align="center">aimOverlay  </h1>

  <p align="center">
    A crosshair overlay executable for windows made in Java

This application will create an always-on-top overlay of a predefined image that centers in the middle of the screen. The user can cycle through different crosshair styles, adjust the size, and toggle the overlay on/off.
    <br />
    <a href="https://github.com/windsywinds/aimOverlay/"><strong>Explore the docs »</strong></a>
    <br />
    <br />
    ·
    <a href="#">No Demo Available</a>
    ·
    <a href="https://github.com/windsywinds/aimOverlay/issues">Report Bug</a>
    ·

</div>

  

![Product Name Screen Shot][product-screenshot]

# In-app global shortcuts

F2: Toggle the settings overlay on/off  
F3: Exit application  
F8: Toggle the debug overlay (not currently operative)  
X: Cycle through crosshair styles  
C: Increase crosshair size (resets at size 10)  

# Custom overlays

Users can create their own custom overlays by following the naming scheme of "crosshair" + number + ".png" e.g. "crosshair4.png"  
These can be placed in the /overlay directory alongside, 
or replacing the existing crosshair images. 
There must always be a "crosshair1.png" as this is what the application will default to.

# Troubleshooting

Program outputs a debug.log file in the applications install directory which logs all errors
and actions to allow problem identification more easily.     
  
Application must be run with Administrator privileges in order to allow global keyboard 
shortcuts and write changes to file. If the user does not wish to allow Administrator privileges 
they can edit the config.ini file located in the install directory to manually update the overlay 
style and size selected when application loads.  

  

<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[product-screenshot]: https://windsywinds.github.io/thumbnails/overlaythumb.jpg
[contributors-shield]: https://img.shields.io/github/contributors/windsywinds/aimOverlay.svg?style=for-the-badge
[contributors-url]: https://github.com/windsywinds/aimOverlay/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/windsywinds/aimOverlay.svg?style=for-the-badge
[forks-url]: https://github.com/windsywinds/aimOverlay/network/members
[stars-shield]: https://img.shields.io/github/stars/windsywinds/aimOverlay.svg?style=for-the-badge
[stars-url]: https://github.com/windsywinds/aimOverlay/stargazers
[issues-shield]: https://img.shields.io/github/issues/windsywinds/aimOverlay.svg?style=for-the-badge
[issues-url]: https://github.com/windsywinds/aimOverlay/issues
[license-shield]: https://img.shields.io/github/license/windsywinds/aimOverlay.svg?style=for-the-badge
[license-url]: https://github.com/windsywinds/aimOverlay/blob/master/LICENSE.txt