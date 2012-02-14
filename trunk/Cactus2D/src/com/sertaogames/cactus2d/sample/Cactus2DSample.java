package com.sertaogames.cactus2d.sample;

import com.badlogic.gdx.backends.jogl.JoglApplication;
import com.sertaogames.cactus2d.Cactus2DApplication;

/**
 * 
 * @author Diogo Vinícius
 *
 */
public class Cactus2DSample {

	public static void main(String[] args) {
		Cactus2DApplication game = new Cactus2DApplication();
		game.loadLevel(new GameLevel());
		new JoglApplication(game, "Cactus2D Sample", 320, 600, false);
	}
	
}
