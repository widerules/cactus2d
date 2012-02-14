package com.sertaogames.cactus2d.components;

import com.badlogic.gdx.Gdx;
import com.sertaogames.cactus2d.Component;
import com.sertaogames.cactus2d.Cactus2DApplication;
import com.sertaogames.cactus2d.Cactus2DLevel;

public class SplashTouch extends Component {
	
	private Class<? extends Cactus2DLevel> level;

	private boolean wasTouched = false;
	private float timeout = 5f;
	@Override
	public void update() {
		if ((timeout <= 0 || (wasTouched && !Gdx.input.isTouched()))) {
			gameObject.onClick();
		}
		else
			timeout -= Cactus2DApplication.deltaTime;
		
		wasTouched = Gdx.input.isTouched();
	}
}
