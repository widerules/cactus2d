package com.sertaogames.cactus2d.sample;

import com.badlogic.gdx.math.Vector2;
import com.sertaogames.cactus2d.Cactus2DApplication;
import com.sertaogames.cactus2d.Component;

/**
 * 
 * @author Diogo Vinícius
 *
 */
public class DragNDropComponent extends Component {

	@Override
	public void onTouchStay(Vector2 touchPosition) {
		screenToWorld(touchPosition);
		transform.setLocalPosition(touchPosition);
	}

	public static Vector2 screenToWorld(Vector2 screenPosition) {
		return screenPosition.mul(Cactus2DApplication.cameraZoom);
	}
	
}
