# AudioComponent #

This component tells the engine how to play a certain sound, allowing you to incorporate sound effects to your game. It also has the basic controls to play and stop a sound.

The process to load and launch an audio sample with Cactus2D is quite simple, first you must create a variable of type AudioComponent to store the desired sound passing a string to the class contructor. An example of how to load an audio sample into an AudioComponent follows:

```
AudioComponent audio = new AudioComponent("data/sound/sample.wav")
```

Then, to set when the sound will be executed, for example, when a button is pressed, you can simply add this object  ButtonComponen.

```
Texture bt = Cactus2DApplication.loadTexture("data/textures/buttons-all.png",true);
TextureRegion[][] tmp = TextureRegion.split(bt, 256, 128);
go.AddComponent(new ButtonComponent(tmp[0][0],tmp[1][0]));
go.AddComponent(new LevelLoader(LevelChooserFree.class));go.AddComponent(new AudioComponent("data/sound/sound.wav"));
```