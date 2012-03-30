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

import com.badlogic.gdx.Gdx;
import com.sertaogames.cactus2d.Component;
import com.sertaogames.cactus2d.Cactus2DApplication;
import com.sertaogames.cactus2d.Cactus2DLevel;

public class SplashTouch extends Component {
	
	private Class<? extends Cactus2DLevel> level;

	private boolean wasTouched = false;
	private float timeout = 5f;
	@Override
	public void update() {
		if ((timeout <= 0 || (wasTouched && !Gdx.input.isTouched()))) {
			gameObject.onClick();
		}
		else
			timeout -= Cactus2DApplication.deltaTime;
		
		wasTouched = Gdx.input.isTouched();
	}
}
