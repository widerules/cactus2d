/*******************************************************************************
 * Copyright 2011 See AUTHORS file.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package com.sertaogames.cactus2d.misc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.tiled.SimpleTileAtlas;
import com.badlogic.gdx.graphics.g2d.tiled.TileMapRenderer;
import com.badlogic.gdx.graphics.g2d.tiled.TileSet;
import com.badlogic.gdx.graphics.g2d.tiled.TiledMap;
import com.badlogic.gdx.math.MathUtils;
import com.sertaogames.cactus2d.Cactus2DApplication;

/** Contains an atlas of tiles by tile id for use with {@link TileMapRenderer} It does not need to be loaded with packed files.
 * Just plain textures.
 * @author Tomas Lazaro */
public class MyTileAtlas extends SimpleTileAtlas {
	
	private String dir = "";
	private TiledMap map = null;
	
	/** Creates a TileAtlas for use with {@link TileMapRenderer}.
	 * @param map The tiled map
	 * @param inputDir The directory containing all needed textures in the map */
	public MyTileAtlas (TiledMap map, String inputDir) {
		super(map,Gdx.files.internal(inputDir));
		this.map = map;
		dir = inputDir;
		//loadTextures();
	}
	
	private void loadTextures() {
		for (TileSet set : map.tileSets) {
			Pixmap pixmap;// = new Pixmap(inputDir.child(set.imageName));
			pixmap = Cactus2DApplication.loadAsset(dir+"/"+set.imageName, Pixmap.class);

			int originalWidth = pixmap.getWidth();
			int originalHeight = pixmap.getHeight();

			if (!MathUtils.isPowerOfTwo(originalWidth) || !MathUtils.isPowerOfTwo(originalHeight)) {
				final int width = MathUtils.nextPowerOfTwo(originalWidth);
				final int height = MathUtils.nextPowerOfTwo(originalHeight);

				Pixmap potPixmap = new Pixmap(width, height, pixmap.getFormat());
				potPixmap.drawPixmap(pixmap, 0, 0, 0, 0, width, height);
				pixmap.dispose();
				pixmap = potPixmap;
			}
			Texture texture = new Texture(pixmap);
			pixmap.dispose();
			textures.add(texture);

			int idx = 0;
			TextureRegion[][] regions = split(texture, originalWidth, originalHeight, map.tileWidth, map.tileHeight, set.spacing,
				set.margin);
			for (int y = 0; y < regions[0].length; y++) {
				for (int x = 0; x < regions.length; x++) {
					regionsMap.put(idx++ + set.firstgid, regions[x][y]);
				}
			}
		}
	}

	public void resume() {
		dispose();
		loadTextures();
	}

	private static TextureRegion[][] split (Texture texture, int totalWidth, int totalHeight, int width, int height, int spacing,
		int margin) {
		// TODO add padding support
		int xSlices = (totalWidth - margin) / (width + spacing);
		int ySlices = (totalHeight - margin) / (height + spacing);

		TextureRegion[][] res = new TextureRegion[xSlices][ySlices];
		for (int x = 0; x < xSlices; x++) {
			for (int y = 0; y < ySlices; y++) {
				res[x][y] = new TextureRegion(texture, margin + x * (width + spacing), margin + y * (height + spacing), width, height);
			}
		}
		return res;
	}
}
