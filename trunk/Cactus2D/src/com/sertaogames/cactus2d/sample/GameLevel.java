package com.sertaogames.cactus2d.sample;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.sertaogames.cactus2d.Cactus2DApplication;
import com.sertaogames.cactus2d.Cactus2DLevel;
import com.sertaogames.cactus2d.GameObject;
import com.sertaogames.cactus2d.components.SpriteRendererComponent;
import com.sertaogames.cactus2d.components.TouchComponent;

/**
 * 
 * @author Diogo Vinícius
 *
 */
public class GameLevel extends Cactus2DLevel {

	@Override
	protected void init() {
		GameObject sertao = new GameObject("sertao");
		Texture texture = Cactus2DApplication.loadTexture("assets/data/sertao.png");
		SpriteRendererComponent sr = new SpriteRendererComponent(texture);
		
		sertao.AddComponent(sr);
		sertao.AddComponent(new TouchComponent(new Vector2(100, 100)));
		sertao.AddComponent(new DragNDropComponent());

		addGameObject(sertao);
	}
	
}
