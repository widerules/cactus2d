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

import com.sertaogames.cactus2d.Component;
import com.sertaogames.cactus2d.Cactus2DApplication;
import com.sertaogames.cactus2d.Cactus2DLevel;

public class LevelLoader extends Component {
	
	private Class<? extends Cactus2DLevel> levelToLoad;

	public LevelLoader(Class<? extends Cactus2DLevel> levelToLoad) {
		this.levelToLoad = levelToLoad;
	}

	@Override
	public void onClick() {
		loadLevel();
	}

	public void loadLevel() {
		try {
			Cactus2DApplication.instance.loadLevel(levelToLoad.newInstance());
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

}
