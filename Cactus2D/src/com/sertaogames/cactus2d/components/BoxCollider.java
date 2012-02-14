package com.sertaogames.cactus2d.components;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.sertaogames.cactus2d.Cactus2DApplication;

public class BoxCollider extends Collider {

	public BoxCollider(Vector2 size, Vector2 offset) {
		this.offset = offset;
		size.mul(Cactus2DApplication.INV_PHYSICS_SCALE * 0.5f);
		PolygonShape poly = new PolygonShape();
		poly.setAsBox(size.x, size.y);
		shape = poly;
	}
	
	
}
