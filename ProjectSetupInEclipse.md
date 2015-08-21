## Project Setup in Eclipse ##

Up to now, we saw how a game implemented with Cactus2D works and how to run it. Until this moment, the game can run in any architecture, either Mac, Linux or Windows. However, we still need to install the game on an Android device, and for that we will show how to do this step by step.

First thing first, we need to create another project to execute our game in an Android environment. For that, go to Eclipse menu and choose the **File -> New -> Android Project** option. Choose the name Cactus2DSampleAndroid and click Next. On the next screen choose one of the SDK versions which you have installed and click Next one more time. In the last screen of the wizard complete the space with the project package: com.sertaogames.cactus2d and click on Finish. It’ll be created an Android project with a class named Activity (Cactus2DSampleAndroidActivity), which is responsible for initiating the Android application.

However, two more steps are still needed. The first one is to setup the project to include the Cactus2D in the classpath of the new Android project. For this, right click on the Android project and choose the Build Path -> Configure Build Path option. Select the Projects tab and inside of it click on the button Add. Select the Cactus2D project and confirm the operation. The second step is also to include some jars from the libgdx library, in the classpath. Go to: http://code.google.com/p/libgdx/downloads and download the new version of the project. On our example, we downloaded the project libgdx-0.9.2.zip. On the Android project you’ve to create a folder called libs and paste the jars gdx-backend-adroid.jar and dgx.jar, and the armeabi and armeabi-v7a folders which we found inside the libgdx-0.9.2 and add these jars in the classpath of the Android project.

You must also copy the content of the assets folder from Cactus2D project to the folder with the same name which exists in the Android project. After that, edit the Activity class like source code list 4.

SourceCode List 4. Method to execute the sample for Android

```
public void onCreate(Bundle savedInstanceState) {
   super.onCreate(savedInstanceState);
   Cactus2DApplication game = new Cactus2DApplication();
   game.loadLevel(new GameLevel());
   initialize(game, false);
}
```
On this piece of code an object of Cactus2DApplication type is created, which is responsible for instantiating the first level to be executed, in other words the level defined on the GameLevel class. The last line shows how to call the libgdx library to execute the game and manage the game loop.