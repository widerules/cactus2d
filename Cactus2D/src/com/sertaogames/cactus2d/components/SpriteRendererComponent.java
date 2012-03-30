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

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.sertaogames.cactus2d.Cactus2DApplication;
import com.sertaogames.cactus2d.RendererComponent;

public class SpriteRendererComponent extends RendererComponent {

	public Texture sprite;
	public TextureRegion spriteRegion;
	public AtlasRegion atlasRegion;
	public boolean flip = false;
	public int flipNumber = 1;
	private Vector2 renderPos = new Vector2();
	public boolean gui = false;
	private Vector3 originalCampos = new Vector3();
	private Vector3 temp = new Vector3();
	private float zoomScale = 1f;
	public Color color = Color.WHITE;

	public SpriteRendererComponent(Texture sprite) {
		super();
		this.sprite = sprite;
	}

	public SpriteRendererComponent(Texture sprite, TextureRegion spriteRegion) {
		super();
		this.sprite = sprite;
		this.spriteRegion = spriteRegion;
	}

	private Vector2 halfSize = new Vector2();
	
	public Vector2 getHalfSize(){
		if (sprite != null) {
			halfSize.set(sprite.getWidth(),sprite.getHeight());
		}
		else if (spriteRegion != null) {
			halfSize.set(spriteRegion.getRegionWidth(),spriteRegion.getRegionHeight());
		}
		else if (atlasRegion != null) {
			halfSize.set(atlasRegion.getRegionWidth(),atlasRegion.getRegionHeight());
		}
		return halfSize.mul(0.5f);
	}
	
	
	@Override
	public void render() {
		if (!gui) {
			temp.set(Cactus2DApplication.camera.position);
			float diffX = originalCampos.x - temp.x;
			float diffY = originalCampos.y - temp.y;
			renderPos.set(transform.getPosition());
			renderPos.mul(zoomScale);
			renderPos.add(diffX*parallax.x,diffY*parallax.y);
			draw();
		}
	}
	
	@Override
	public void init() {
		originalCampos.set(Cactus2DApplication.camera.position);
		zoomScale = 1f/Cactus2DApplication.cameraZoom;
		super.init();
	}
	
	@Override
	public void renderGUI() {
		if (!enabled)
			return;
		Cactus2DApplication.spriteBatch.setColor(color);
		if (gui) {
			renderPos.set(transform.getPosition());
			if (spriteRegion != null) {
				Cactus2DApplication.spriteBatch.draw(spriteRegion, renderPos.x, renderPos.y, zoomScale*transform.getScale().x*spriteRegion.getRegionWidth(), zoomScale*transform.getScale().y*spriteRegion.getRegionHeight());
			}
			else if (sprite != null)
				Cactus2DApplication.spriteBatch.draw(sprite, renderPos.x, renderPos.y, zoomScale*transform.getScale().x*sprite.getWidth(), zoomScale*transform.getScale().y*sprite.getHeight());
			else if (atlasRegion != null) {
				Cactus2DApplication.spriteBatch.draw(atlasRegion, renderPos.x, renderPos.y, zoomScale*transform.getScale().x*atlasRegion.getRegionWidth(), zoomScale*transform.getScale().y*atlasRegion.getRegionHeight());
			}
		}
	}
	
	@Override
	public void destroy() {}
	
	private void draw() {
		Cactus2DApplication.spriteBatch.setColor(color);
		if (spriteRegion != null) {
			Cactus2DApplication.spriteBatch.draw(spriteRegion, renderPos.x, renderPos.y, 0, 0, spriteRegion.getRegionWidth(), spriteRegion.getRegionHeight(), transform.getScale().x*zoomScale, transform.getScale().y*zoomScale, transform.getAngle());
		}
		else if (sprite != null)
			Cactus2DApplication.spriteBatch.draw(sprite, renderPos.x, renderPos.y, transform.getScale().x*sprite.getWidth()/2, transform.getScale().y*sprite.getHeight()/2, sprite.getWidth(), sprite.getHeight(), transform.getScale().x, transform.getScale().y, transform.getAngle(), 0, 0, sprite.getWidth(), sprite.getHeight(), flip, false);
		else if (atlasRegion != null) {
			Cactus2DApplication.spriteBatch.draw(atlasRegion, renderPos.x, renderPos.y, 0, 0, atlasRegion.getRegionWidth(), atlasRegion.getRegionHeight(), flipNumber*transform.getScale().x*zoomScale, transform.getScale().y*zoomScale, transform.getAngle());
		}
	}
	
	public static SpriteRendererComponent getTextureRegion(Texture texture, int width, int height, int i, int j) {
		TextureRegion[][] tmp = TextureRegion.split(texture, width, height);
		SpriteRendererComponent sr = new SpriteRendererComponent(null,tmp[i][j]);
		return sr;
	}

}
