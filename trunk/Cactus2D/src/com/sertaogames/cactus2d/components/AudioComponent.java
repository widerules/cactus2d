package com.sertaogames.cactus2d.components;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.sertaogames.cactus2d.Cactus2DApplication;
import com.sertaogames.cactus2d.Component;

public class AudioComponent extends Component {
	
	public Sound sound;
	public float volume = 1f;
	
	public AudioComponent(String path) {
		sound = Cactus2DApplication.loadSound(path);
	}
	
	@Override
	public void init() {
		super.init();
	}
	
	@Override
	public void onClick() {
		sound.play();
	}
	
	@Override
	public void onTouchBegin(Vector2 touchPosition) {
		sound.play();
	}
	
}
