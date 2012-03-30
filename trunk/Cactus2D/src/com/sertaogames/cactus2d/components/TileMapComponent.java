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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.tiled.TileMapRenderer;
import com.badlogic.gdx.graphics.g2d.tiled.TiledLayer;
import com.badlogic.gdx.graphics.g2d.tiled.TiledLoader;
import com.badlogic.gdx.graphics.g2d.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.sertaogames.cactus2d.Component;
import com.sertaogames.cactus2d.misc.MyTileAtlas;

public class TileMapComponent extends Component {

	public TiledMap tilemap;
	public MyTileAtlas atlas;
	public TileMapRenderer renderer;
	public FileHandle baseDir;
	private FileHandle file;

	public TileMapComponent(String path, String filename) {
		baseDir = Gdx.files.internal(path);
		file = Gdx.files.internal(path + filename);
		//renderer = GCoreApplication.assetMgr.get(path+filename, TileMapRenderer.class);
		tilemap = TiledLoader.createMap(file);
		//tilemap = renderer.getMap();

		atlas = new MyTileAtlas(tilemap, path);
		renderer = new TileMapRenderer(tilemap, atlas, 16, 16);

	}

	@Override
	public void init() {
		Map<Integer, List<Integer>> layerMap = new HashMap<Integer, List<Integer>>();
		Map<Integer, Vector2> parallaxMap = new HashMap<Integer, Vector2>();
		for (int i = 0; i < tilemap.layers.size(); i++) {
			int layerNumber = -1;
			TiledLayer l = tilemap.layers.get(i);

			String layerProperty = l.properties.get("layer");
			if (layerProperty != null)
				layerNumber = Integer.parseInt(layerProperty);

			Vector2 parallax = new Vector2(0, 0);
			String parallaxProperty = l.properties.get("parallax-x");
			if (parallaxProperty != null)
				parallax.x = Float.parseFloat(parallaxProperty);
			
			parallaxProperty = l.properties.get("parallax-y");
			if (parallaxProperty != null)
				parallax.y = Float.parseFloat(parallaxProperty);

			List<Integer> layers = layerMap.get(layerNumber);
			if (layers == null)
				layers = new ArrayList<Integer>();
			layers.add(i);
			layerMap.put(layerNumber, layers);
			parallaxMap.put(layerNumber, parallax);
		}

		for (Entry<Integer, List<Integer>> e : layerMap.entrySet()) {
			int[] layers = new int[e.getValue().size()];
			int index = 0;
			for (Integer i : e.getValue()) {
				layers[index++] = i;
			}
			TileLayerRenderer r = new TileLayerRenderer(e.getKey(), layers);

			r.parallax.set(parallaxMap.get(e.getKey()));
			gameObject.AddComponent(r);
		}
	}
	
	@Override
	public void resume() {
		atlas.resume();
	}

	@Override
	public void destroy() {
		atlas.dispose();
	}
}
