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

package com.sertaogames.cactus2d;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.TileMapRendererLoader;
import com.badlogic.gdx.assets.loaders.TextureLoader.TextureParameter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
import com.badlogic.gdx.graphics.g2d.tiled.TileMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.sertaogames.cactus2d.components.LabelComponent;

public class Cactus2DLevel extends GameObjectComposite {

	public boolean updatePhysics = true;

	private Map<String, Class> preloadAssets = new HashMap<String, Class>();
	private boolean inPreload = false;

	@Override
	public final void controlInit() {
		initPhysics();
		if (!preloadAssets.isEmpty()) {
			preloadAssets();
		} else {
			super.controlInit();
		}
	}

	LabelComponent loadingLabel;

	private void preloadAssets() {
		inPreload = true;
		for (Entry<String, Class> pre : preloadAssets.entrySet()) {

			Cactus2DApplication.assetMgr.load(pre.getKey(), pre.getValue());
		}
		// Init loading GO
		GameObject loading = new GameObject("level-splash");
		loadingLabel = new LabelComponent("Loading");
		loadingLabel.alignment = HAlignment.CENTER;
		loading.AddComponent(loadingLabel);
		loading.transform.getPosition().set(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
		addGameObject(loading);
	}

	public void preloadAsset(String name, Class clazz) {
		preloadAssets.put(name, clazz);
	}

	public void preloadTexture(String name) {
		preloadAsset(name, Texture.class);
	}

	public void preloadSound(String name) {
		preloadAsset(name, Sound.class);
	}

	public void preloadMusic(String name) {
		preloadAsset(name, Music.class);
	}

	public void preloadMapRenderer(String path, String name) {
		preloadAssets.put(name, TileMapRenderer.class);
		Cactus2DApplication.assetMgr.load(path + name, TileMapRenderer.class, new TileMapRendererLoader.TileMapParameter(path, 4, 4));
	}

	private void initPhysics() {
		world = new World(new Vector2(0, -25f), true);
		world.setContactListener(new ContactListener() {
			@Override
			public void beginContact(Contact co) {
				Fixture fa = co.getFixtureA();
				Fixture fb = co.getFixtureB();
				if (fa.isSensor() && fb.isSensor())
					return;

				GameObject a = (GameObject) fa.getBody().getUserData();
				GameObject b = (GameObject) fb.getBody().getUserData();

				if (fa.isSensor() || fb.isSensor()) {
					a.onTriggerEnter(co, b);
					b.onTriggerEnter(co, a);
				} else {
					a.onCollisionEnter(co, b);
					b.onCollisionEnter(co, a);
				}
			}

			@Override
			public void endContact(Contact co) {
				Fixture fa = co.getFixtureA();
				Fixture fb = co.getFixtureB();
				if (fa.isSensor() && fb.isSensor())
					return;

				GameObject a = (GameObject) fa.getBody().getUserData();
				GameObject b = (GameObject) fb.getBody().getUserData();

				if (fa.isSensor() || fb.isSensor()) {
					a.onTriggerExit(co, b);
					b.onTriggerExit(co, a);
				} else {
					a.onCollisionExit(co, b);
					b.onCollisionExit(co, a);
				}
			}

			@Override
			public void preSolve(Contact points, Manifold oldManifold) {
			}

			@Override
			public void postSolve(Contact co, ContactImpulse impulse) {
				Fixture fa = co.getFixtureA();
				Fixture fb = co.getFixtureB();
				if (fa.isSensor() && fb.isSensor())
					return;

				GameObject a = (GameObject) fa.getBody().getUserData();
				GameObject b = (GameObject) fb.getBody().getUserData();

				if (fa.isSensor() || fb.isSensor()) {
					a.onTriggerStay(co, b);
					b.onTriggerStay(co, a);
				} else {
					a.onCollisionStay(co, b);
					b.onCollisionStay(co, a);
				}
			}
		});
	}

	@Override
	protected void onDestroy() {
		destroyed = true;
		super.onDestroy();
		for (String s : preloadAssets.keySet()) {
			Cactus2DApplication.assetMgr.unload(s);
		}
	}

	@Override
	public void update() {
		if (inPreload) {
			Cactus2DApplication.assetMgr.update();
			//System.out.println(GCoreApplication.assetMgr.getProgress());
			inPreload = false;
			loadingLabel.text = "Loading";
			for (String assetName : preloadAssets.keySet()) {
				if (!Cactus2DApplication.assetMgr.isLoaded(assetName)) {
					// System.out.println(assetName);
					inPreload = true;
				}
			}
			if (!inPreload) {
				loadingLabel.gameObject.destroy();
				loadingLabel = null;
				preloadAssets.clear();
				super.controlInit();
			}
		} else {
			super.update();
		}
	}

	@Override
	public void physicsUpdate() {
		if (updatePhysics) {
			super.physicsUpdate();
			world.step(Cactus2DApplication.deltaTime, 8, 3);
		}
	}

	@Override
	public void render() {
		// System.out.println("BEGIN render");
		int layer = -999;
		Cactus2DApplication.spriteBatch.begin();
		Cactus2DApplication.spriteBatch.setColor(Color.WHITE);
		//Cactus2DApplication.spriteBatch.setProjectionMatrix(Cactus2DApplication.camera.combined);

		// TODO get rig of this...
		for (int i = 0; i < renderList.size(); i++) {
			RendererComponent r = renderList.get(i);
			if (r.ownBatch) {
				Cactus2DApplication.spriteBatch.end();
			} else if (r.layer > layer && layer > -999) {
				Cactus2DApplication.spriteBatch.end();
				Cactus2DApplication.spriteBatch.begin();
				Cactus2DApplication.spriteBatch.setColor(Color.WHITE);
				//Cactus2DApplication.spriteBatch.setProjectionMatrix(Cactus2DApplication.camera.combined);
				layer = r.layer;
			}
			r.controlRender();
			if (r.ownBatch) {
				Cactus2DApplication.spriteBatch.begin();
				Cactus2DApplication.spriteBatch.setColor(Color.WHITE);
				//Cactus2DApplication.spriteBatch.setProjectionMatrix(Cactus2DApplication.camera.combined);
			}
		}
		Cactus2DApplication.spriteBatch.end();
	}

	@Override
	public void destroy() {
		onDestroy();
	}

}
