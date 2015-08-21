# ButtonComponent #

The GUI elements are created in much the same way as other objects described so far, with only some differences in their behaviors. Their sprites should always be rendered above the other Game Objects. To do this simply assign the true value of the property named in spriteRendererComponent gui object.

```
public static GameObject createButton() {
	GameObject go = new GameObject("button");

	go.transform.getPosition().set(Gdx.graphics.getWidth()-175*Cactus2DApplication.invCameraZoom, 32 * Cactus2DApplication.invCameraZoom);
		
	SpriteRenderer sr = new SpriteRenderer(null);
	sr.gui = true;
	go.AddComponent(sr);
		
	Texture tx = Cactus2DApplication.loadTexture("data/textures/buttons-all.png",true);
	TextureRegion[][] tmp = TextureRegion.split(tx, 64, 64);
	go.AddComponent(new ButtonComponent(tmp[4][7],tmp[5][7]));
			
	return go;
}

```

The ButtonComponent has some attributes of type TextureRegion to define a button that can be instantiated with images:

  * normalImage: Represent the button unpressed.
  * pressedImage: To be rendered when the button is pressed.
  * currentImage: Stores the current state of the button.