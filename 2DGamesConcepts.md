# 2D Games Concepts #

<p>Before start writing a game code, a few concepts related to general game development must be understood. Some of them are explained as follows:</p>
**Game Loop**
<p>When a game is running, everything that happens on the screen is controlled by a loop, wich is responsible for all the logic and calculations that gives dynamicity to the game. This loop usually has 3 phases: controls check, refresh and show up. Cactus2D implements the loop and provides hooks for implementing phase of the update game objects.</p>
**Sprite**
<p>Sprite is an image used to represent the shape of a game object. They can be objects of the game itself, such as characters, or GUI elements, such as menus and buttons.</p>
**Animation**
<p>Sprite sheets are sequences of sprites organized in one image disposed as a grid. As the sprites are showed in sequence, we obtain an animation.</p>
**Tilemap**
<p>Tilemaps are a techinique for scenarios composition, that consists in a two-dimensional array, where each element points to a sprite in a sprite sheet. This sprites are built to be embedded, allowing space saving.</p>
**Physics**
<p>A physics library allows the simulation of the real world conditions in virtual models, using variables of the newtonian physics such as mass and velocity to detect collision (check if two graphic objects have collided), calculate displacements, gravity effects, etc.</p>
**Assets**
<p>Asset is the name used to refer to any external static resource in a game, such as images, textures, TTF fonts or bitmaps (typography), audio files, maps and scripts of code that are available for making a game. To use an asset, it is necessary to load it when the game is initiated.</p>