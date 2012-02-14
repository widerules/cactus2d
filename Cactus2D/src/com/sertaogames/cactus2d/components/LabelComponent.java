package com.sertaogames.cactus2d.components;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.sertaogames.cactus2d.Component;
import com.sertaogames.cactus2d.Cactus2DApplication;

public class LabelComponent extends Component {

	public HAlignment alignment = HAlignment.LEFT;
	public Color color = Color.WHITE;
	public String text = "";
	public BitmapFont font;
	public Vector2 offset = new Vector2();
	private String fontName = "treasure";
	public int size = 42;
	private static List<Integer> sizes = new ArrayList<Integer>();
	static {
		sizes.add(24);
		sizes.add(32);
		sizes.add(42);
	}
	
	public LabelComponent(HAlignment alignment, String text) {
		super();
		this.alignment = alignment;
		this.text = text;
	}

	public LabelComponent(String text) {
		this.text = text;
	}
	
	public LabelComponent(String text, String fontName) {
		this.text = text;
		this.fontName = fontName;
	}
	
	@Override
	public void init() {
		/*int width = Gdx.graphics.getWidth();
		if (width < 300) {
			reduce(3);
		}
		if (width < 350) {
			reduce(2);
		}*/
		
		Texture tx = Cactus2DApplication.loadTexture("data/fonts/"+fontName+size+".png", true);
		TextureRegion[][] rg = TextureRegion.split(tx, tx.getWidth(), tx.getHeight());
		font = Cactus2DApplication.loadFont("data/fonts/"+fontName+size+".fnt");
		font.setScale( Cactus2DApplication.invCameraZoom, Cactus2DApplication.invCameraZoom);
	}
	
	/*TODO
	 * Refazer o mŽtodo reduce para mudar tamanho da fonte em rela‹o ao zoom
	 */
	private void reduce(int i) {
		int index = sizes.indexOf(size);
		index -= i;
		if (index < 0)
			index = 0;
		size = sizes.get(index);
	}


	Vector2 pos = new Vector2();
	@Override
	public void renderGUI() {
		pos.set(offset).add(transform.getPosition());
		font.setColor(color);
		font.drawMultiLine(Cactus2DApplication.spriteBatch, text, pos.x, pos.y, 0, alignment);
	}

	public HAlignment getAlignment() {
		return alignment;
	}

	public void setAlignment(HAlignment alignment) {
		this.alignment = alignment;
	}

	public String getFontName() {
		return fontName;
	}

	public void setFontName(String fontName) {
		this.fontName = fontName;
	}

}
