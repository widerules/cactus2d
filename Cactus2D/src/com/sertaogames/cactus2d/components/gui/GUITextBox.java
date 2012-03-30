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

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Slider.SliderStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.sertaogames.cactus2d.Component;

public class GUITextBox extends Component {
	
	public float value = 0;
	private TextField text;
	private GUIWindow gui;
	private String name;
	
	@Override
	public void init() {
		gui = getComponent(GUIWindow.class);
		text = new TextField("",gui.skin.getStyle(TextFieldStyle.class));
		Label label = new Label(name, gui.skin.getStyle(LabelStyle.class));

		gui.window.row();
		gui.window.add(label);
		gui.window.row();
		gui.window.add(text);
		gui.window.row();
	}
	
	public GUITextBox(String name) {
		this.name = name;
	}
}
