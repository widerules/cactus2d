package com.sertaogames.cactus2d.components;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.sertaogames.cactus2d.Cactus2DApplication;
import com.sertaogames.cactus2d.RendererComponent;

public class TileLayerRenderer extends RendererComponent {

	private int[] layers;
	private TileMapComponent tm;

	public TileLayerRenderer(int layer, int[] l) {
		super();
		this.layer = layer;
		layers = l;
		ownBatch = true;
	}

	private Vector3 originalCampos = new Vector3();
	@Override
	public void init() {
		tm = getComponent(TileMapComponent.class);
		originalCampos.set(Cactus2DApplication.camera.position);
	}

	private Vector3 temp = new Vector3();

	@Override
	public void render() {
		
		temp.set(Cactus2DApplication.camera.position);
		float diffX = originalCampos.x - temp.x;
		float diffY = originalCampos.y - temp.y;
		Cactus2DApplication.camera.position.sub(transform.getPosition().x+diffX*parallax.x, transform.getPosition().y+diffY*parallax.y, 0);
		Cactus2DApplication.camera.update();
		tm.renderer.render(Cactus2DApplication.camera, layers);
		Cactus2DApplication.camera.position.set(temp);
		Cactus2DApplication.camera.update();
	}

}
