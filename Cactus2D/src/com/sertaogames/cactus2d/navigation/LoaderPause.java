package com.sertaogames.cactus2d.navigation;

import com.badlogic.gdx.math.Vector2;
import com.sertaogames.cactus2d.Component;
import com.sertaogames.cactus2d.Cactus2DApplication;
import com.sertaogames.cactus2d.Cactus2DLevel;

public class LoaderPause extends Component {
	
	private Class<? extends Cactus2DLevel> levelToLoad;

	public LoaderPause(Class<? extends Cactus2DLevel> levelToLoad) {
		this.levelToLoad = levelToLoad;
	}

	@Override
	public void onClick() {
		loadLevel();
	}

	public void loadLevel() {
		try {
			Cactus2DApplication.instance.loadLevel(levelToLoad.newInstance());
			gameObject.parent.setPaused(true);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
}
