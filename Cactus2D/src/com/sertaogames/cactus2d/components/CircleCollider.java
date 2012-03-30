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
