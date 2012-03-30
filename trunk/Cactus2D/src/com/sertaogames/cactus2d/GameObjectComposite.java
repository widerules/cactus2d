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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.badlogic.gdx.physics.box2d.World;

public abstract class GameObjectComposite {

	protected boolean active = true;
	protected boolean paused = false;
	protected boolean initialized = false;
	private List<GameObject> gameObjects = new ArrayList<GameObject>();
	private List<GameObject> gameObjectsToAdd = new ArrayList<GameObject>();
	private List<GameObject> gameObjectsToRemove = new ArrayList<GameObject>();
	public String name;
	public World world;
	public GameObjectComposite parent;
	protected Set<RendererComponent> renderers = new TreeSet<RendererComponent>();
	protected List<RendererComponent> renderList = new ArrayList<RendererComponent>();
	protected boolean destroyed = false;
	
	public void resume() {
		for (int i = 0; i < gameObjects.size(); i++) {
			GameObjectComposite go = gameObjects.get(i);
			go.resume();
		}
	}

	public void controlInit() {
		initialized = true;
		processAddRemoveGameObjects();
		for (int i = 0; i < gameObjects.size(); i++) {
			GameObjectComposite go = gameObjects.get(i);
			if (!go.initialized)
				go.controlInit();
		}
		init();
	}

	protected void init() {

	}

	public abstract void destroy();

	protected void onDestroy() {
		for (int i = 0; i < gameObjects.size(); i++) {
			GameObject go = gameObjects.get(i);
			go.destroy();
		}
	}

	public void addGameObject(GameObject go) {
		gameObjectsToAdd.add(go);
	}

	public void removeGameObject(GameObject go) {
		gameObjectsToRemove.add(go);
	}

	public GameObjectComposite() {
		super();
	}

	protected final void controlUpdate() {
		if (active && !paused) {
			update();
		}
		processAddRemoveGameObjects();
	}

	public void physicsUpdate() {
		if (active && !paused) {
			for (int i = 0; i < gameObjects.size(); i++) {
				GameObject go = gameObjects.get(i);
				go.physicsUpdate();
			}
		}
	}

	private void processAddRemoveGameObjects() {
		for (int i = 0; i < gameObjectsToAdd.size(); i++) {
			GameObject go = gameObjectsToAdd.get(i);
			gameObjects.add(go);
			go.parent = this;
			go.world = this.world;
			go.controlInit();
		}
		gameObjectsToAdd.clear();
		for (int i = 0; i < gameObjectsToRemove.size(); i++) {
			GameObject go = gameObjectsToRemove.get(i);
			if (gameObjects.contains(go)) {
				go.onDestroy();
				gameObjects.remove(go);
			}
		}
		gameObjectsToRemove.clear();
	}

	public void update() {
		for (int i = gameObjects.size() - 1; i >= 0; i--) {
			GameObject go = gameObjects.get(i);
			go.update();
		}
	}

	protected final void controlLateUpdate() {
		if (active && !paused) {
			lateUpdate();
		}
	}

	public void lateUpdate() {
		for (int i = 0; i < gameObjects.size(); i++) {
			GameObject go = gameObjects.get(i);
			go.lateUpdate();
		}
	}

	protected final void controlRender() {
		if (active) {
			render();
		}
	}

	public void render() {
	}

	protected void removeRenderers(Set<RendererComponent> rs) {
		renderers.removeAll(rs);
		renderList.removeAll(rs);
		if (parent != null) {
			parent.removeRenderers(rs);
		}
	}

	protected void addRenderer(RendererComponent r) {
		if (parent != null)
			parent.addRenderer(r);
		renderers.add(r);
		renderList.clear();
		for (RendererComponent rc : renderers) {
			renderList.add(rc);
		}
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void setActiveAll(boolean active) {
		setActive(active);
		for (int i = 0; i < gameObjects.size(); i++) {
			GameObject go = gameObjects.get(i);
			go.setActiveAll(active);
		}
	}

	public boolean isPaused() {
		return paused;
	}

	public void setPaused(boolean paused) {
		this.paused = paused;
	}

	public void setPausedAll(boolean paused) {
		this.paused = paused;
		for (int i = 0; i < gameObjects.size(); i++) {
			GameObject go = gameObjects.get(i);
			go.setPaused(paused);
		}
	}

	public void renderGUI() {
		if (active) {
			for (int i = 0; i < gameObjects.size(); i++) {
				GameObject go = gameObjects.get(i);
				go.renderGUI();
			}
		}
	}

}