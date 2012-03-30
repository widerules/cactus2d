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
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.sertaogames.cactus2d.Cactus2DApplication;
import com.sertaogames.cactus2d.Component;

public class PhysicsComponent extends Component {

	public Body rigidbody;
	private float density = 0;
	private Collider collider = null;
	private Vector2 temp = new Vector2();
	private Vector2 temp2 = new Vector2();
	public FixtureDef fixtureDef = new FixtureDef();
	private BodyType bodyType = BodyType.DynamicBody;

	public PhysicsComponent(float density, boolean dynamic, boolean trigger) {
		fixtureDef.density = density;
		fixtureDef.isSensor = trigger;
		if (!dynamic)
			bodyType = BodyType.StaticBody;
	}
	
	public PhysicsComponent(float density, BodyType type, boolean trigger) {
		fixtureDef.density = density;
		fixtureDef.isSensor = trigger;
		bodyType = type;
	}

	@Override
	public void init() {
		collider = getComponent(Collider.class);
		BodyDef groundBodyDef = new BodyDef();

		groundBodyDef.fixedRotation = true;
		groundBodyDef.type = bodyType;

		rigidbody = gameObject.world.createBody(groundBodyDef);

		fixtureDef.shape = collider.shape;
		fixtureDef.density = density;
		rigidbody.createFixture(fixtureDef);
		collider.shape.dispose();
		temp.set(collider.offset);
		temp.add(transform.getPosition());
		temp.mul(Cactus2DApplication.INV_PHYSICS_SCALE);
		System.out.println(gameObject.name+": "+temp);
		rigidbody.setTransform(temp, transform.getAngle());
		rigidbody.setUserData(gameObject);
	}

	
	
	@Override
	public void destroy() {
		//System.out.println(gameObject.name);
		try {
			rigidbody.getWorld().destroyBody(rigidbody);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void physicsUpdate() {
		temp.set(collider.offset);
		if (rigidbody.getType().equals(BodyType.KinematicBody)) {
			temp.add(transform.getPosition());
			temp.mul(Cactus2DApplication.INV_PHYSICS_SCALE);
			rigidbody.setTransform(temp, transform.getAngle());
		} else {
			temp2.set(rigidbody.getPosition());
			temp2.mul(Cactus2DApplication.PHYSICS_SCALE);
			temp2.sub(temp);
			transform.getPosition().set(temp2);
		}
	}

	public BodyType getBodyType() {
		return bodyType;
	}

	public void setBodyType(BodyType bodyType) {
		if (rigidbody != null)
			rigidbody.setType(bodyType);
		this.bodyType = bodyType;
	}
	
}
