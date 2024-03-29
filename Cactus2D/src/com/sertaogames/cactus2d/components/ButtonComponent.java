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
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.sertaogames.cactus2d.Component;
import com.sertaogames.cactus2d.Cactus2DApplication;

public class ButtonComponent extends Component {

	public TextureRegion normalImage;
	public TextureRegion pressedImage;
	private TextureRegion currentImage;
	
	public ButtonComponent(String path) {
		//Texture tx = new Texture(Gdx.files.internal(path));
		Texture tx = Cactus2DApplication.loadTexture(path);
		TextureRegion[][] tmp = TextureRegion.split(tx, tx.getWidth(), tx.getHeight()/2);
		normalImage = tmp[0][0];
		pressedImage = tmp[1][0];
		currentImage = normalImage;
	}
	
	public ButtonComponent(TextureRegion up, TextureRegion down) {
		normalImage = up;
		pressedImage = down;
		currentImage = normalImage;
	}
	
	private boolean pressed = false;
	private boolean wasTouched = false;
	
	@Override
	public void update() {
		spriteRenderer.gui = true;
		if (Gdx.input.isTouched() && !wasTouched) {
			if (!pressed && insideBox()) {
				pressed = true;
				currentImage = pressedImage;
			}
		}
		
		if (!Gdx.input.isTouched()) {
			if (pressed && insideBox()) {
				playSFX();
				gameObject.onClick();
			}
			currentImage = normalImage;
			pressed = false;
		}
		
		if (pressed) {
			Cactus2DApplication.guiLock = true;
		}
		
		spriteRenderer.spriteRegion = currentImage;
		wasTouched = Gdx.input.isTouched();
	}
	
	private void playSFX() {
		AudioComponent sfx = getComponent(AudioComponent.class);
		if (sfx != null) {
			sfx.sound.play();
		}
	}

	@Override
	public void lateUpdate() {
		Cactus2DApplication.guiLock = false;
	}
	
	private boolean insideBox() {
		int x = Gdx.input.getX();
		int y = Gdx.graphics.getHeight() - Gdx.input.getY();
//		x *= GCoreApplication.camera.zoom;
//		y *= GCoreApplication.camera.zoom;
//		System.out.println(x+" - "+y);
//		System.out.println(transform.getPosition());
		boolean isX = x >= transform.getPosition().x && x <= transform.getPosition().x + normalImage.getRegionWidth()*Cactus2DApplication.invCameraZoom;
		boolean isY = y >= transform.getPosition().y && y <= transform.getPosition().y + normalImage.getRegionHeight()*Cactus2DApplication.invCameraZoom;
		return isX && isY;
	}

}
