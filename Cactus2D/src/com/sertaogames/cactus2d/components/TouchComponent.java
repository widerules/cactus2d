/**

 This file is part of Cactus2D.

    Cactus2D is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Cactus2D is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Cactus2D.  If not, see <http://www.gnu.org/licenses/>.
*/

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
