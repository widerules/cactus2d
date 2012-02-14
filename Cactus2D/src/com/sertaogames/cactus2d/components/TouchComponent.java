package com.sertaogames.cactus2d.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.sertaogames.cactus2d.Cactus2DApplication;
import com.sertaogames.cactus2d.Component;

public class TouchComponent extends Component {

	// touch test
	private boolean pressed = false;
	private static boolean catched = false;
	private Vector2 size = new Vector2();

	public TouchComponent(Vector2 size) {
		super();
		this.size = size;
	}

	@Override
	public void update() {
		updatePos();
		// spriteRenderer.gui = true;

		if (pressed) {
			// touch stay
			gameObject.onTouchStay(touchPosition);
		}

		if (Gdx.input.isTouched() && !pressed && !catched && insideBox()) {
			catched = true;
			pressed = true;
			gameObject.onTouchBegin(touchPosition);
		}

		if (!Gdx.input.isTouched() && pressed) {
			pressed = false;
			catched = false;
			gameObject.onTouchEnd(touchPosition);
			if (insideBox()) {
				gameObject.onClick();
			}
		}
	}

	public Vector2 getBounds() {
		return size;
	}

	public void setBounds(Vector2 bounds) {
		this.size = bounds;
	}

	private Vector2 touchPosition = new Vector2();

	private void updatePos() {
		float x = Gdx.input.getX();
		float y = Gdx.graphics.getHeight() - Gdx.input.getY();
		touchPosition.set(x, y);
	}

	private boolean insideBox() {
		int x = (int) (touchPosition.x * Cactus2DApplication.cameraZoom);
		int y = (int) (touchPosition.y * Cactus2DApplication.cameraZoom);
		boolean isX = x >= transform.getPosition().x
				&& x <= transform.getPosition().x + size.x;
		boolean isY = y >= transform.getPosition().y
				&& y <= transform.getPosition().y + size.y;
		return isX && isY;
	}
}
