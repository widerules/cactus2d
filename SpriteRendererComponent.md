# SpriteRendererComponent #

A sprite is an image used to represent the appearance of an game object.These game objects can be characters, obstacles in the scenario, the scenario includes their own GUI (Graphic User Interface) which are the menus, buttons and game information to be displayed on screen.

In Cactus2D to apply a sprite in a game object, use the component called SpriteRendererComponent. Here's the code for the creation and use of a component type SpriteRendererComponent:

```
public class GameLevel extends Cactus2DLevel {
   @Override
   public void init() {
      GameObject sertao = new GameObject("sertao");
      Texture texture = Cactus2DApplication.loadTexture("data/sertao.png");
      SpriteRendererComponent spriteRenderer = new SpriteRendererComponent(texture);
      sertao.AddComponent(spriteRenderer);
      addGameObject(sertao);
   }
}
```

After defining an object of type Texture loading an image file as shown in second line from method _init()_. When instantiating a SpriteRendererComponent passes it as parameter the newly created texture and then we have the component to be added to any game object, as shown in fourth line from method _init()_ of the listings above.