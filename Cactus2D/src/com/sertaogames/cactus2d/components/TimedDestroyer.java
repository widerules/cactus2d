package com.sertaogames.cactus2d.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.audio.Sound;
import com.sertaogames.cactus2d.Component;
import com.sertaogames.cactus2d.Cactus2DApplication;

public class TimedDestroyer extends Component {

	private float time = 0.5f;

	public TimedDestroyer(float t) {
		time = t;
		enabled = false;
	}

	@Override
	public void update() {
		if (time >= 0) {
			time -= Cactus2DApplication.deltaTime;
		} else {
			gameObject.destroy();
		}
	}

}
