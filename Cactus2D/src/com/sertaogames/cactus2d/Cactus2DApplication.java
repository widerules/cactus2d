package com.sertaogames.cactus2d;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader.TextureParameter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.sertaogames.cactus2d.components.MusicPlayer;

public class Cactus2DApplication implements ApplicationListener {

	public static boolean DEBUG = false;
	public static final float PHYSICS_SCALE = 100;
	public static final float INV_PHYSICS_SCALE = 0.01f;
	public static float sfxVolume = 1f;

	Cactus2DLevel currentLevel;
	Cactus2DLevel nextLevel;
	public static SpriteBatch spriteBatch;
	public static Box2DDebugRenderer debugRenderer;
	public static OrthographicCamera camera;
	public static float time = 0;
	public static float deltaTime = 0;
	public static boolean simulatePhysics = true;
	public static Vector2 cameraOffset = new Vector2();
	public static boolean guiLock = false;
	public static float cameraZoom = 1f;
	public static float invCameraZoom = 1f;

	public static Cactus2DApplication instance;
	public static AssetManager assetMgr;

	public static MusicPlayer jukeBox = new MusicPlayer(1f);

	public void share(String message) {

	}

	public void loadLevel(Cactus2DLevel level) {
		nextLevel = level;
	}

	public static <T> T loadAsset(String path, Class<T> type) {
		if (!assetMgr.isLoaded(path)) {
			assetMgr.load(path, type);
			assetMgr.finishLoading();
		}
		return assetMgr.get(path, type);
	}

	public static <T> T loadAsset(String path, Class<T> type,
			AssetLoaderParameters<T> params) {
		if (!assetMgr.isLoaded(path)) {
			assetMgr.load(path, type, params);
			assetMgr.finishLoading();
		}
		return assetMgr.get(path, type);
	}

	public static Texture loadTexture(String path) {
		return loadTexture(path, false);
	}

	public static Texture loadTexture(String path, boolean mipmap) {
		TextureParameter p = new TextureParameter();
		if (mipmap) {
			p.genMipMaps = true;
		}
		Texture tx = loadAsset(path, Texture.class, p);
		if (mipmap) {
			tx.setFilter(TextureFilter.MipMapLinearNearest,
					TextureFilter.Nearest);
		}
		return tx;
	}

	public static TextureAtlas loadAtlas(String path) {
		TextureAtlas atlas = loadAsset(path, TextureAtlas.class);
		return atlas;
	}

	public static Sound loadSound(String path) {
		return loadAsset(path, Sound.class);
	}

	public static Music loadMusic(String path) {
		return loadAsset(path, Music.class);
	}

	public static BitmapFont loadFont(String path) {
		return loadAsset(path, BitmapFont.class);
	}

	@Override
	public void create() {
		instance = this;
		spriteBatch = new SpriteBatch();
		// if (DEBUG) {
		debugRenderer = new Box2DDebugRenderer();
		// }
		assetMgr = new AssetManager();
		resetCamera();
		invCameraZoom = 1f / cameraZoom;
	}

	@Override
	public void dispose() {
		if (currentLevel != null) {
			currentLevel.destroy();
		}
		GameObject.gameObjects.clear();
		if (assetMgr != null)
			assetMgr.clear();
		if (jukeBox != null)
			jukeBox.clear();
	}

	@Override
	public void pause() {
		if (currentLevel != null) {
			currentLevel.setPaused(true);
		}
	}

	public static Color backgroundColor = Color.WHITE;

	@Override
	public void render() {
		if (Gdx.input.isKeyPressed(Keys.D))
			DEBUG = true;
		else
			DEBUG = false;
		deltaTime = Gdx.graphics.getDeltaTime();
		time += deltaTime;
		if (deltaTime > 0.5f)
			deltaTime = 0.005f;

		//
		Gdx.graphics.getGL10().glClearColor(backgroundColor.r,
				backgroundColor.g, backgroundColor.b, backgroundColor.a);
		Gdx.graphics.getGL10().glClear(GL10.GL_COLOR_BUFFER_BIT);

		if (currentLevel != null) {
			currentLevel.controlUpdate();
		}

		processChangeLevel();

		// run physics here
		if (simulatePhysics)
			if (currentLevel != null) {
				currentLevel.physicsUpdate();
			}

		if (currentLevel != null) {
			currentLevel.controlLateUpdate();
		}

		jukeBox.update();

		camera.update();
		cameraOffset.set(-Gdx.graphics.getWidth() / 2, -Gdx.graphics.getHeight() / 2);

		proj.setToOrtho2D(0, 0, Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());

		spriteBatch.setProjectionMatrix(proj);
		if (currentLevel != null) {
			currentLevel.controlRender();
		}

		proj.setToOrtho2D(0, 0, Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		spriteBatch.begin();
		spriteBatch.setColor(Color.WHITE);
		spriteBatch.setProjectionMatrix(proj);
		if (currentLevel != null) {
			currentLevel.renderGUI();
		}
		spriteBatch.end();

		if (DEBUG && currentLevel != null) {
			renderPos.set(camera.position.x, camera.position.y);
			renderPos.sub(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
			renderPos.mul(Cactus2DApplication.INV_PHYSICS_SCALE);
			proj.setToOrtho2D(renderPos.x, renderPos.y, Gdx.graphics.getWidth() * INV_PHYSICS_SCALE * camera.zoom, Gdx.graphics.getHeight() * INV_PHYSICS_SCALE * camera.zoom);
			debugRenderer.render(currentLevel.world, proj);
		}

	}

	private Vector2 renderPos = new Vector2();
	private Matrix4 proj = new Matrix4();

	private void processChangeLevel() {
		boolean dispose = false;
		if (nextLevel != null && currentLevel != null) {
			dispose = true;
			currentLevel.destroy();

			// System.out.println(GameObject.gameObjectNumber());
			if (GameObject.gameObjectNumber() > 0) {
				System.out.println("Problem on level loading: "
						+ currentLevel.name);
				System.out.println("Leaked objects: " + GameObject.gameObjects);
			}
		}

		if (dispose) {
			assetMgr.clear();
			// assetMgr = new AssetManager();
		}
		if (nextLevel != null) {
			// System.out.println("new level");
			resetCamera();
			currentLevel = nextLevel;
			currentLevel.controlInit();
			nextLevel = null;
		}
	}

	private void resetCamera() {
		camera = new OrthographicCamera(Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		camera.position.set(Gdx.graphics.getWidth() / 2,
				Gdx.graphics.getHeight() / 2, 0);
		camera.zoom = cameraZoom;
	}

	@Override
	public void resize(int arg0, int arg1) {
		spriteBatch.getProjectionMatrix().setToOrtho2D(0, 0,
				Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}

	@Override
	public void resume() {
		if (currentLevel != null) {
			currentLevel.setPaused(false);
		}
		// if (currentLevel != null) {
		// currentLevel.destroy();
		// GameObject.gameObjects.clear();
		// assetMgr.clear();
		// currentLevel = null;
		// }
	}

}
