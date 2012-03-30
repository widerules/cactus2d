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

package com.sertaogames.cactus2d.components.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.ui.Window.WindowStyle;
import com.sertaogames.cactus2d.RendererComponent;

public class GUIWindow extends RendererComponent {
	
	public float value = 0;
	public Skin skin = new Skin();
	Window window;
	private Stage ui;
	
	public GUIWindow(String label) {
		layer = 10;
		ownBatch = true;
		ui = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
		Gdx.input.setInputProcessor(ui);
		skin = new Skin(Gdx.files.internal("data/gui/uiskin.json"), Gdx.files.internal("data/gui/uiskin.png"));
		window = new Window("window", label, ui, skin.getStyle(WindowStyle.class), Gdx.graphics.getWidth(), Gdx.graphics.getHeight()-50);
		window.x = 0;
		window.y = 25;
		window.defaults().spaceBottom(10);
		window.row();
		ui.addActor(window);
	}
	
	@Override
	public void destroy() {
		window.remove();
	}
	
	@Override
	protected void render() {
		ui.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		ui.draw();
	}

}
