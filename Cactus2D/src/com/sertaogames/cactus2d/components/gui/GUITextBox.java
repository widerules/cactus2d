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
