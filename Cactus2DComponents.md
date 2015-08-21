# Cactus2D Components #

Cactus2D comes with several classes that represent reusable components which are very useful for most games. Here is a brief explanation about each one of these components.

**SpriteRendererComponent**: Component responsible for applying a texture on a game object by loading files that contain sprites.

**AudioComponent**: Component that reproduces a custom sound.

**AnimationComponent**: Based on the sprites loaded with the SpriteRendererComponent, the AnimationComponent defines the animation settings for the game object, such as frequency and sequence of the sprites to be rendered.

**PhysicsComponent**: For every object that should interact with other objects and need to simulate real world physics, this component must be added.

**TileMapComponent**: Component responsible for loading and handling tilemaps on a game scene.

**ButtonComponent**: Defines a button that can be instantiated with two images (one to represent the button unpressed and the other to be rendered when the button is pressed).

**LabelComponent**: Component that draws a label on the screen based on the .fnt file created for the game.

**TouchComponent**: Captures the game inputs on the touchscreen.

**TransformComponent**: Encapsulates the x, y and z coordinates of the game object’s position.

**BoxCollider**: Delimits a region to represent the physical area occupied by a game object.

Each one of these components can be added to the various objects of the game. For example, in a certain game we have a character who must jump over some obstacles to avoid colliding with them. In this case, the components to be used will be SpriteRendererComponent, AnimationComponent, PhysicsComponent and BoxCollider. With SpriteRendererComponent added to the game object (the character), you can define the file which contains the texture of the character to be rendered, with AnimationComponent you can define what is the range of sprites that appear in each animation. In addition, you also need to define the physical object that represents the character and the objects that represent the obstacles. For this, the PhysicsComponent and BoxCollider components should be added to each object that need to simulate physical collision.

Depending on the game, sometimes we need to customize specific components. For instance, now let’s imagine that our character must collect scores by touching some special obstacles. In this case, a customized component must be created to implement this specific need. This component should have a counter to store the scores and also other features to be decided according to the game’s need.

In fact, every game can be implemented using a library of reusable Cactus2D components added to the specific components that the game requires. Thus, the game will have objects with behaviors that interact with each other.