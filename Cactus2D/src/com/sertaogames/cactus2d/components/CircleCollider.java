package com.sertaogames.cactus2d.components;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.sertaogames.cactus2d.Cactus2DApplication;

public class CircleCollider extends Collider {

	public CircleCollider(float radius, Vector2 offset) {
		this.offset = offset;
		radius *= Cactus2DApplication.INV_PHYSICS_SCALE;
		CircleShape circle = new CircleShape();
		circle.setRadius(radius);
		shape = circle;
	}
	
	
}
