package com.sertaogames.cactus2d.misc;

import java.io.IOException;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.tools.imagepacker.TexturePacker;
import com.badlogic.gdx.tools.imagepacker.TexturePacker.Settings;

public class PackTextures {

	 public static void main (String[] args) throws IOException {
         Settings settings = new Settings();
         settings.padding = 0;
         settings.maxWidth = 512;
         settings.maxHeight = 512;
         settings.incremental = true;
         TexturePacker.process(settings, "assets/textures", "data/textures");
 }
}
