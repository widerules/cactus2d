package com.sertaogames.cactus2d.components;

import com.sertaogames.cactus2d.Component;
import com.sertaogames.cactus2d.Cactus2DApplication;
import com.sertaogames.cactus2d.Cactus2DLevel;

public class LevelLoader extends Component {
	
	private Class<? extends Cactus2DLevel> levelToLoad;

	public LevelLoader(Class<? extends Cactus2DLevel> levelToLoad) {
		this.levelToLoad = levelToLoad;
	}

	@Override
	public void onClick() {
		loadLevel();
	}

	public void loadLevel() {
		try {
			Cactus2DApplication.instance.loadLevel(levelToLoad.newInstance());
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

}
