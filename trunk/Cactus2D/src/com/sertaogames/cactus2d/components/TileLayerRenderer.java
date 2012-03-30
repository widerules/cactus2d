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

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.sertaogames.cactus2d.Cactus2DApplication;
import com.sertaogames.cactus2d.RendererComponent;

public class TileLayerRenderer extends RendererComponent {

	private int[] layers;
	private TileMapComponent tm;

	public TileLayerRenderer(int layer, int[] l) {
		super();
		this.layer = layer;
		layers = l;
		ownBatch = true;
	}

	private Vector3 originalCampos = new Vector3();
	@Override
	public void init() {
		tm = getComponent(TileMapComponent.class);
		originalCampos.set(Cactus2DApplication.camera.position);
	}

	private Vector3 temp = new Vector3();

	@Override
	public void render() {
		
		temp.set(Cactus2DApplication.camera.position);
		float diffX = originalCampos.x - temp.x;
		float diffY = originalCampos.y - temp.y;
		Cactus2DApplication.camera.position.sub(transform.getPosition().x+diffX*parallax.x, transform.getPosition().y+diffY*parallax.y, 0);
		Cactus2DApplication.camera.update();
		tm.renderer.render(Cactus2DApplication.camera, layers);
		Cactus2DApplication.camera.position.set(temp);
		Cactus2DApplication.camera.update();
	}

}
