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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Slider.SliderStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Slider.ValueChangedListener;
import com.sertaogames.cactus2d.Component;

public class GUISlider extends Component {
	
	public float value = 0;
	public Slider slider;
	private GUIWindow gui;
	private String name;
	public ValueChangedListener listener;
	
	@Override
	public void init() {
		gui = getComponent(GUIWindow.class);
		slider = new Slider(0, 1, 0.1f, gui.skin.getStyle(SliderStyle.class),name);
		Label label = new Label(name, gui.skin.getStyle(LabelStyle.class));
		slider.setValueChangedListener(listener);
		slider.setValue(value);
		gui.window.add(label);
		gui.window.row();
		gui.window.add(slider).minWidth(Gdx.graphics.getWidth()/2);
		gui.window.row();
	}
	
	public GUISlider(String name) {
		this.name = name;
	}
}
