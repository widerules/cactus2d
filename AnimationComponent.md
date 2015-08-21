# Animation Component #

Animation is the process in which several images are produced individually and repeatedly by making little changes in a model. When connected together, the result is a movie that, if seen at a speed of 16 frames per second or more, creates an illusion of continuous motion. So, to make an animation we need a sequence of frames with images similar to those which we are going to call frames.

Cactus2D supports animation via Component Annimation. Before using this component, we need to create an asset that contains all parts of our animation. This asset must follow certain rules to be properly processed.
Requirements of the image that contains the animation:
  * Have quadratic measurements;
  * The frames are divided quadratically

In our example we are going to use an asset that has 1024x1024px splited into 384x192px frames. Within each frame there is an image aligned with the upper left corner of the frame, all with a little difference from the previous one. For praticing, you can search for other images on the Internet with the same characteristics, or create your own images using tools like Photoshop, Illustrator and Corel.

With our asset done, we will put it in the data folder of the project and create a class called GameLevelAnimation that extends from Cactus2DLevel. This class will help us to see our first animation, it will create a window to display it. Once you create a class, you must override the init () method, which is responsible for creating and initializing the elements that will make part of the level.

We are going to start programming in this method. First we will create the GameObject that will contain the elements that will be represented on the screen. We will call it "goAnimation" passing that string as a parameter in its constructor.

```
GameObject animatedGO = new GameObject("goAnimation");
```

Now we will load the file from the asset. It should be in the date of this project. To avoid problems of searching the descktop and Android devices we will use the handling files methods of LibGDX. After loading the image file, we will instanciate a Texture to manipulate the file in Cactus2D as an image.

```
FileHandle file = Gdx.files.internal("data/sismoAnim.png");
Texture tx  = new Texture(file);
```

Now define the variables that determine how our animation will be formed:
  * Integer framesPerSecond - The first is the speed in frames per second in which images are displayed: This variable will be called fps, it determines how many frames are displayed per second during the execution of the animation.
  * Integer sizeX and sizeY - Represents the default size of the image frames. The image is divided exactly in those sizes, and from 0 indexed from left to right as shown in Figure 01.
entire
  * Collection of frames - Determines the sequence of frames that will be displayed during the animation.


```
int fps = 5;
int sizeX = 384;
int sizeY = 192;
int[] frames = { 0, 1, 2, 3, 4, 5, 6, 7};
```

At this point effectively build the animation with the previous parameters. We create an instance of AnimationComponent, the component responsible for the animation to life on screen. From this object we call the method addAnimation parssando the parameters described above in the order arranged in the lines below and as the first parameter the name of the animation, which in our example is "animation".

```
AnimationComponent anim = new AnimationComponent();
anim.addAnimation("animation", tx, sizeX, sizeY, frames, fps, false);
anim.play("animation");
```

Now just add the image to gameObject, so that it is linked also to the level and make some adjustments, like adding a component SpriteRendererComponent. The need for this component is only to inform Cactus2D he should be drawn on the screen. Furthermore, we change the position of the object on the screen to make it more centralized.

```
animatedGO.AddComponent(new SpriteRendererComponent(null));
animatedGO.AddComponent(anim);
animatedGO.transform.getPosition().add(15, 15);
addGameObject(animatedGO);
```

Now just run the application and see the animation of a seismograph on the game screen.