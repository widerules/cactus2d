package com.sertaogames.cactus2d;

import com.badlogic.gdx.math.Vector2;

public abstract class RendererComponent extends Component implements Comparable<RendererComponent> {

	public int layer = 0;
	public Vector2 parallax = new Vector2(0,0);
	private static int indexCounter = 0;
	private int index;
	public boolean ownBatch = false;
	protected Vector2 worldPosition = new Vector2();
	
	public RendererComponent() {
		index = indexCounter++;
	}
	
	void controlRender() {
		if (enabled && gameObject.active) {
			render();
		}
	}
	
	protected abstract void render();
	
	@Override
	public int compareTo(RendererComponent o) {
		if (o == this)
			return 0;
		if (o.layer == layer )
			return o.index > index ? -1 : 1;
		return o.layer > layer ? -1 : 1;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		return false;
	}
	
	@Override
	public int hashCode() {
		return index;
	}

}
