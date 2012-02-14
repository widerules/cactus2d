package com.sertaogames.cactus2d.components;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Shape;
import com.sertaogames.cactus2d.Component;

public abstract class Collider extends Component {
	
	public Shape shape;
	public Vector2 offset;

}
