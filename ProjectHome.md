## Cactus2D - the free 2D game engine for Android, PC, Mac and Linux ##

Cactus 2D brings the appraised game object component model approach to 2D games. Build games using the existent library of highly reusable components, or unleash your programming skills and create custom ones to make your dream gameplay come to life.

Cactus 2D is free and open source, available through the LGPL license, which gives you the freedom to create commercial projects as well.

Cactus 2D is built on top of other great open source tools such as Libgdx and Box 2D.

## Features ##

Although Cactus 2D is still in alpha version, it has been used to developed both [Candy Soldier](http://sertaogames.com/games/candysoldier/) and [Terremoto](http://sertaogames.com/games/terremoto/) (whose source code and art assets are also avaiable at http://code.google.com/p/jogo-terremoto-sertaogames/). The following features and component are already available in the current version of Cactus2D:

  * Game object component model - classes to represent the key concepts in a game: Level, GameObject, Component (abstract) and several reusable components;
  * Transform - every game object has a Transform component, representing its position and orientation relative to the simulation world;
  * SpriteRenderer;
  * Physics - makes a game object take part on the physics simulation (Box 2D), either being a static, dynamic or kinect object. All physics-enabled game objects receive the collision and trigger callbacks;
  * TileMap - component that imports all layers of a TMX tilema
  * TileMapPhysics - import either an object or tile layer from a TMX file as the polygons to create the physics of a level;
  * TouchComponent

Features that will be available in the next versions of Cactus 2D:

  * XML based format for storing levels and prefabs;
  * Runtime instantiation of prefabs and levels from XML files;
  * Visual Editor for setting up game objects, components, prefabs and levels (reflection based attribute injection during level initialization, editing of component attributes on-the-fly from the visual editor).