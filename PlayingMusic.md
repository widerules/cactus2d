# Playing Music #

In order to use music and sound loop in your game there is an object in the Cactus2DApplication class called jukebox that can be accessed from any part of the game at any time. It allows the addition of music, thereby creating a kind of playlist. A good tip is that the songs on a jukebox be added to the game and loaded on the first level, so having them available during the game and avoiding performance problems by loading audio files during the other phases of the game.

```
Cactus2DApplication.jukeBox.addMusic("data/sound/music01.mp3");
Cactus2DApplication.jukeBox.play(0);
```