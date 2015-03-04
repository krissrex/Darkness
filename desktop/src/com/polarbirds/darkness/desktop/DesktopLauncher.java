package com.polarbirds.darkness.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;
import com.polarbirds.darkness.DarknessGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
        System.setProperty("user.name", "Default"); // Fixes OpenAL loading but with exotic names like Jørgen...
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.useGL30 = false; // Default shaders are not compatible with 3.0
        config.backgroundFPS = 10;
        config.vSyncEnabled = true;
        config.allowSoftwareMode = true;
        config.initialBackgroundColor = Color.BLACK;
        //config.title
		new LwjglApplication(new DarknessGame(), config);
	}
}
