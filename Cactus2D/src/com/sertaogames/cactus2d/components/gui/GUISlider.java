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
