package com.sertaogames.cactus2d.components;

import com.badlogic.gdx.math.Vector2;
import com.sertaogames.cactus2d.Component;

public class Transform extends Component {

	private Vector2 position = new Vector2();
	private Vector2 scale = new Vector2(1,1);
	private float angle = 0;
	
	public float getAngle() {
		if (parentTransform != null)
			return angle + parentTransform.getAngle();
		return angle;
	}
	public void setLocalAngle(float angle) {
		this.angle = angle;
	}
	public Vector2 getScale() {
		if (parentTransform != null) {
			temp.set(scale);
			temp.x *= parentTransform.getScale().x;
			temp.y *= parentTransform.getScale().y;
		}
		return scale;
	}
	private Vector2 temp = new Vector2();
	public Vector2 getPosition() {
		if (parentTransform != null)
			return temp.set(position).add(parentTransform.getPosition());
		return position;
	}
	
	public Vector2 getLocalPosition(){
		return position;
	}
	
	public void setLocalPosition(Vector2 pos){
		position.set(pos);
	}
	
	public Vector2 getLocalScale(){
		return scale;
	}
	
	public void setLocalScale(Vector2 s){
		scale.set(s);
	}

}
