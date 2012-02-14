package com.sertaogames.cactus2d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.sertaogames.cactus2d.components.SpriteRendererComponent;
import com.sertaogames.cactus2d.components.Transform;

public abstract class Component {

	public GameObject gameObject;
	public Transform transform;
	public SpriteRendererComponent spriteRenderer;
	protected boolean initialized = false;
	public boolean enabled = true;
	protected Transform parentTransform;

	public <T> T getComponent(Class<T> clazz) {
		return gameObject.getComponent(clazz);
	}
	
	void controlUpdate() {
		if (enabled)
			update();
	}

	public void update() {
	}
	
	void controlPhysicsUpdate() {
		if (enabled)
			physicsUpdate();
	}
	
	public void physicsUpdate() {
	}

	void controlLateUpdate() {
		if (enabled)
			lateUpdate();
	}
	
	public void lateUpdate() {
	}

	public void controlInit() {
		transform = gameObject.getComponent(Transform.class);
		spriteRenderer = gameObject.getComponent(SpriteRendererComponent.class);
		initialized = true;
		if (gameObject.parent instanceof GameObject) {
			parentTransform = ((GameObject) gameObject.parent).getComponent(Transform.class);
		}
		//System.out.println("Initialized: "+getClass().getSimpleName());
		init();
	}

	public void init() {
	}

	public void onCollisionEnter(Contact contact, GameObject other) {
	}

	public void onCollisionExit(Contact contact, GameObject other) {
	}

	public void onCollisionStay(Contact contact, GameObject other) {
	}

	public void destroy() {
	}

	public void onClick() {
	}

	public void onTriggerStay(Contact contact, GameObject other) {
	}
	
	public void onTriggerEnter(Contact contact, GameObject other) {
	}
	
	public void onTriggerExit(Contact contact, GameObject other) {
	}

	public void renderGUI() {
	}
	
	public void resume() {		
	}

	public void onTouchBegin(Vector2 touchPosition) {
	}
	public void onTouchStay(Vector2 touchPosition) {
	}
	public void onTouchEnd(Vector2 touchPosition) {
	}
}
