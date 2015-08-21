# Using the Cactus2D #

Before read about [2D Games Concepts](2DGamesConcepts.md) and [The Cactus2D Object Model](TheCactus2DObjectModel.md), you're able to read this page. First we need to show how to create and to use a level with the Cactus2D

## Levels ##

This is another important concept implemented in Cactus2D. Level is how the game phases and screens are known. Each menu, each splash screen, each scene is represented by a level. In games implemented with Cactus2D, every level must extend the Cactus2DLevel class.

Now we’re going to see the example that comes in the Cactus2D package sample. When you run this example, an object is created on the game screen, which will be controlled by touch, holding and dragging the object, for this, a drag and drop component will be used.

On the source code list 1, the code represents one level for the game screen. On this level is created a game object called “sertao” and three components are added to this game object. The first component is the SpriteRendererComponent (from the Cactus2D), which apply the texture with a ball to the object. The second component is the TouchComponent. The last component, was implemented to the sample. It encapsulates the drag and drop input behavior.

The init() method is always called by the engine when the level is loaded, this way, on the GameLevel example, all the composition needed for the level are defined on the beginning.

SourceCode List 1. GameLevel Implementation.
```
public class GameLevel extends Cactus2DLevel { 
   @Override
   protected void init() {
      GameObject sertao = new GameObject("sertao");
      Texture texture = Cactus2DApplication.loadTexture("data/sertao.png");
      SpriteRendererComponent sr = new SpriteRendererComponent(texture);
      sertao.AddComponent(sr);
      sertao.AddComponent(new TouchComponent(new Vector2(100, 100)));
      sertao.AddComponent(new DragNDropComponent());
      addGameObject(sertao);
   }          
}
```
The source code list 2, show the implementation of the specific component for this example. As you can see, the update() method are inherited from the component class. On this method, which is executed from every loop of the game, the position of the ball is updated by using the input of the device’s touchscreen. On the update() method we updated the game object by the attribute transform which are inherited from Component class.

SourceCode List 2. Implementation of the Drag and Drop component.
```
public class DragNDropComponent extends Component {
   @Override
   public void onTouchStay(Vector2 touchPosition) {
      transform.setLocalPosition(touchPosition);
   }           
}
```
To execute the game, you need to instantiate the level created and execute it, like on the source code list 3. On the PC, the touch option can be simulated by the click of the mouse.

SourceCode List 3. Class that execute the Sample.
```
public class Cactus2DSample {
   public static void main(String[] args) {
      Cactus2DApplication game = new Cactus2DApplication();
      game.loadLevel(new GameLevel());
      new JoglApplication(game, "Cactus2D Sample", 320, 600, false);
   }           
}
```