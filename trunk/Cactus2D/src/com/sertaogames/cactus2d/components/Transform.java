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
