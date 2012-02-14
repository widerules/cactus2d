package com.sertaogames.cactus2d;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.sertaogames.cactus2d.components.SpriteRendererComponent;
import com.sertaogames.cactus2d.components.Transform;

public final class GameObject extends GameObjectComposite {

	public static List<GameObject> gameObjects = new ArrayList<GameObject>();

	private List<Component> components = new ArrayList<Component>();
	private List<Component> componentsToAdd = new ArrayList<Component>();
	private List<Component> componentsToRemove = new ArrayList<Component>();
	public final Transform transform = new Transform();
	public SpriteRendererComponent spriteRenderer;
	
	@Override
	public void resume() {
		for (int i = 0; i < components.size(); i++) {
			Component co = components.get(i);
			co.resume();
		}
		super.resume();
	}
	
	public static int gameObjectNumber() {
		return gameObjects.size();
	}
	
	@Override
	public String toString() {
		return name;
	}

	public GameObject(String name) {
		gameObjects.add(this);
		this.name = name;
		components.add(transform);
	}

	public void onClick() {
		for (int i = 0; i < components.size(); i++) {
			Component co = components.get(i);
			co.onClick();
		}
	}
	
	public void onTouchBegin(Vector2 touchPosition) {
		for (int i = 0; i < components.size(); i++) {
			Component co = components.get(i);
			co.onTouchBegin(touchPosition);
		}
	}
	
	public void onTouchStay(Vector2 touchPosition) {
		for (int i = 0; i < components.size(); i++) {
			Component co = components.get(i);
			co.onTouchStay(touchPosition);
		}
	}
	
	public void onTouchEnd(Vector2 touchPosition) {
		for (int i = 0; i < components.size(); i++) {
			Component co = components.get(i);
			co.onTouchEnd(touchPosition);
		}
	}
	
	public void destroy() {
		if (!destroyed) {
			active = false;
			destroyed = true;
			// in case level is not destroyed, delay destruction
			if (parent != null && !parent.destroyed){
				parent.removeGameObject(this);
			}
			else // when level is already being destroyed.
				onDestroy();
		}
	}

	@Override
	protected void onDestroy() {
		//System.out.println(name);
		super.onDestroy();
		gameObjects.remove(this);
		if (parent != null){
			parent.removeRenderers(renderers);
		}
		for (int i = 0; i < components.size(); i++) {
			Component co = components.get(i);
			co.destroy();
		}
	}

	public void onCollisionEnter(Contact contact, GameObject other) {
		for (int i = 0; i < components.size(); i++) {
			Component co = components.get(i);
			co.onCollisionEnter(contact, other);
		}
	}

	public void onCollisionExit(Contact contact, GameObject other) {
		for (int i = 0; i < components.size(); i++) {
			Component co = components.get(i);
			co.onCollisionExit(contact, other);
		}
	}

	public void onCollisionStay(Contact contact, GameObject other) {
		for (int i = 0; i < components.size(); i++) {
			Component co = components.get(i);
			co.onCollisionStay(contact, other);
		}
	}

	public void onTriggerEnter(Contact contact, GameObject other) {
		for (int i = 0; i < components.size(); i++) {
			Component co = components.get(i);
			co.onTriggerEnter(contact, other);
		}
	}

	public void onTriggerExit(Contact contact, GameObject other) {
		for (int i = 0; i < components.size(); i++) {
			Component co = components.get(i);
			co.onTriggerExit(contact, other);
		}
	}

	public void onTriggerStay(Contact contact, GameObject other) {
		for (int i = 0; i < components.size(); i++) {
			Component co = components.get(i);
			co.onTriggerStay(contact, other);
		}
	}

	public void AddComponent(Component co) {
		co.gameObject = this;
		if (SpriteRendererComponent.class.isAssignableFrom(co.getClass()))
			spriteRenderer = (SpriteRendererComponent) co;
		componentsToAdd.add(co);
	}

	public void RemoveComponent(Component co) {
		componentsToRemove.add(co);
	}

	public <T> T getComponent(Class<T> clazz) {
		for (int i = 0; i < components.size(); i++) {
			Component co = components.get(i);
			if (clazz.isAssignableFrom(co.getClass()))
				return (T) co;
		}
		for (int i = 0; i < componentsToAdd.size(); i++) {
			Component co = componentsToAdd.get(i);
			if (clazz.isAssignableFrom(co.getClass()))
				return (T) co;
		}
		return null;
	}

	public final void update() {
		if (active && !paused) {
			for (int i = 0; i < components.size(); i++) {
				Component co = components.get(i);
				co.controlUpdate();
			}
			super.update();
		}
		processAddRemoveComponents();
	}

	@Override
	public void physicsUpdate() {
		if (active && !paused) {
			for (int i = 0; i < components.size(); i++) {
				Component co = components.get(i);
				co.controlPhysicsUpdate();
			}
			super.physicsUpdate();
		}
	}

	private Set<RendererComponent> renderersToRemove = new HashSet<RendererComponent>();

	private void processAddRemoveComponents() {
		for (int i = 0; i < componentsToAdd.size(); i++) {
			Component co = componentsToAdd.get(i);
			if (co.getClass() == SpriteRendererComponent.class)
				spriteRenderer = (SpriteRendererComponent) co;
			components.add(co);
			if (RendererComponent.class.isAssignableFrom(co.getClass())) {
				RendererComponent rc = (RendererComponent) co;
				renderers.add(rc);
				parent.addRenderer(rc);
			}
		}
		componentsToAdd.clear();

		for (int i = 0; i < componentsToRemove.size(); i++) {
			Component co = componentsToRemove.get(i);
			components.remove(co);
			if (co != null) {
				co.destroy();
				if (RendererComponent.class.isAssignableFrom(co.getClass())) {
					RendererComponent rc = (RendererComponent) co;
					renderersToRemove.add(rc);
					renderers.remove(rc);
				}
			}
		}
		componentsToRemove.clear();
		if (!renderersToRemove.isEmpty())
			parent.removeRenderers(renderersToRemove);
		for (int i = 0; i < components.size(); i++) {
			Component co = components.get(i);
			if (!co.initialized) {
				co.gameObject = this;
				co.controlInit();
			}
		}
	}

	public final void lateUpdate() {
		if (active && !paused) {
			for (int i = 0; i < components.size(); i++) {
				Component co = components.get(i);
				co.controlLateUpdate();
			}
			super.lateUpdate();
		}
	}

	@Override
	public void controlInit() {
		// System.out.println("initializing " + name);
		processAddRemoveComponents();
		for (int i = 0; i < components.size(); i++) {
			Component co = components.get(i);
			if (!co.initialized) {
				co.gameObject = this;
				co.controlInit();
			}
		}
		super.controlInit();
	}

//	public void controlDestroy() {
//		
//	}

	@Override
	public void renderGUI() {
		if (active) {
			for (int i = 0; i < components.size(); i++) {
				Component co = components.get(i);
				co.renderGUI();
			}
			super.renderGUI();
		}
	}

	public static GameObject find(String name) {
		for (int i = 0; i < gameObjects.size(); i++) {
			GameObject go = gameObjects.get(i);
			if (go.name.equals(name))
				return go;
		}
		return null;
	}

	public static List<GameObject> findAll(String name) {
		List<GameObject> found = new ArrayList<GameObject>();
		for (int i = 0; i < gameObjects.size(); i++) {
			GameObject go = gameObjects.get(i);
			if (go.name.equals(name))
				found.add(go);
		}
		return found;
	}

	@Override
	public void setActive(boolean active) {
		super.setActive(active);
		for (int i = 0; i < components.size(); i++) {
			Component co = components.get(i);
			if (active)
				co.enabled = true;
			else
				co.enabled = false;
		}
	}

}
