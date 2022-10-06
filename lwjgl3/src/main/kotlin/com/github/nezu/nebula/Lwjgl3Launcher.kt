package com.github.nezu.nebula

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import nebula.Nebula

fun main() {
    Lwjgl3Application(Nebula(), Lwjgl3ApplicationConfiguration().apply {
        setTitle("Nebula")
        useVsync(true)
        //// Limits FPS to the refresh rate of the currently active monitor.
        setForegroundFPS(Lwjgl3ApplicationConfiguration.getDisplayMode().refreshRate)
        //// If you remove the above line and set Vsync to false, you can get unlimited FPS, which can be
        //// useful for testing performance, but can also be very stressful to some hardware.
        //// You may also need to configure GPU drivers to fully disable Vsync; this can cause screen tearing.
        setWindowedMode(16 * 32, 9 * 32)
        setWindowIcon("libgdx128.png", "libgdx64.png", "libgdx32.png", "libgdx16.png")
    })
}