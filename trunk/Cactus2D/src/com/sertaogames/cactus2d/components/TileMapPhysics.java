package com.sertaogames.cactus2d.components;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.tiled.TiledLayer;
import com.badlogic.gdx.graphics.g2d.tiled.TiledObject;
import com.badlogic.gdx.graphics.g2d.tiled.TiledObjectGroup;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.sertaogames.cactus2d.Component;
import com.sertaogames.cactus2d.Cactus2DApplication;

public class TileMapPhysics extends Component {

	private List<Body> bodies = new ArrayList<Body>();
	private TileMapComponent tm;
	private String physicsLayerName;
	private boolean objectLayer = false;

	public TileMapPhysics(String physicsLayer, boolean objectLayer) {
		this.physicsLayerName = physicsLayer;
		this.objectLayer = objectLayer;
	}

	@Override
	public void init() {
		tm = getComponent(TileMapComponent.class);
		if (!objectLayer)
			createFromBlocks();
		else
			createFromObjects();
	}

	private void createFromObjects() {
		TiledObjectGroup physicsObjects = null;
		for (TiledObjectGroup g : tm.tilemap.objectGroups) {
			if (g.name.equals(physicsLayerName))
				physicsObjects = g;
		}
		for (TiledObject physicsObject : physicsObjects.objects) {
			float y = tm.tilemap.height*tm.tilemap.tileHeight - physicsObject.y;
			Vector2 pos = new Vector2(physicsObject.x+physicsObject.width/2,y-physicsObject.height/2);
			Vector2 size = new Vector2(physicsObject.width,physicsObject.height);
			createBodyFromObject(pos, size);
		}
	}

	private void createBodyFromObject(Vector2 pos, Vector2 size) {
		Body rigidbody;
		PolygonShape groundPoly = new PolygonShape();

		size.mul(Cactus2DApplication.INV_PHYSICS_SCALE*0.5f);
		groundPoly.setAsBox(size.x, size.y);

		BodyDef groundBodyDef = new BodyDef();
		groundBodyDef.fixedRotation = true;
		groundBodyDef.type = BodyType.StaticBody;
		rigidbody = gameObject.world.createBody(groundBodyDef);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = groundPoly;
		fixtureDef.filter.groupIndex = 0;
		fixtureDef.filter.categoryBits = 0x4;
		rigidbody.createFixture(fixtureDef);
		groundPoly.dispose();

		pos.add(transform.getPosition());
		pos.mul(Cactus2DApplication.INV_PHYSICS_SCALE);
		rigidbody.setTransform(pos, transform.getAngle());
		rigidbody.setUserData(gameObject);
		bodies.add(rigidbody);
	}

	private void createFromBlocks() {
		TiledLayer physicsLayer = null;
		for (TiledLayer layer : tm.tilemap.layers) {
			if (layer.name.equals(physicsLayerName))
				physicsLayer = layer;
		}

		for (int i = 0; i < physicsLayer.getHeight(); i++) {
			int begin = -1;
			int last = -1;
			for (int j = 0; j < physicsLayer.getWidth(); j++) {
				int tile = physicsLayer.tiles[i][j];
				// System.out.println(tile);

				if (tile != 0) {
					last = j;
					if (begin < 0) {
						begin = j;
					}
				}

				if ((j == physicsLayer.getWidth() - 1 || tile == 0) && last >= 0) {
					createBodyFromBlocks(begin, last, i);
					begin = -1;
					last = -1;
				}
			}
		}
	}

	private void createBodyFromBlocks(int begin, int last, int i) {
		Vector2 tileSize = new Vector2(tm.tilemap.tileWidth, tm.tilemap.tileHeight);

		Body rigidbody;
		PolygonShape groundPoly = new PolygonShape();
		int width = last - begin + 1;
		float height = tileSize.y;
		width *= tileSize.x;
		// System.out.println("i: "+i+" width: "+width);
		Vector2 temp = new Vector2(width, tileSize.y);
		temp.mul(Cactus2DApplication.INV_PHYSICS_SCALE * 0.5f);
		groundPoly.setAsBox(temp.x, temp.y);

		BodyDef groundBodyDef = new BodyDef();
		groundBodyDef.fixedRotation = true;
		groundBodyDef.type = BodyType.StaticBody;
		rigidbody = gameObject.world.createBody(groundBodyDef);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = groundPoly;
		fixtureDef.filter.groupIndex = 0;
		fixtureDef.filter.categoryBits = 0x4;
		rigidbody.createFixture(fixtureDef);
		groundPoly.dispose();

		temp.set(new Vector2(begin * tileSize.x + width / 2, (tm.tilemap.height - i) * tileSize.y - tileSize.y / 2));
		temp.add(transform.getPosition());
		temp.mul(Cactus2DApplication.INV_PHYSICS_SCALE);
		rigidbody.setTransform(temp, transform.getAngle());
		rigidbody.setUserData(gameObject);
		bodies.add(rigidbody);
	}

	@Override
	public void destroy() {
		for (Body rigidbody : bodies) {
			rigidbody.getWorld().destroyBody(rigidbody);
		}
	}
}
