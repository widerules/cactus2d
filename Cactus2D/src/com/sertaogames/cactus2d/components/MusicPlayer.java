/**

 This file is part of Cactus2D.

    Cactus2D is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Cactus2D is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Cactus2D.  If not, see <http://www.gnu.org/licenses/>.
*/

package com.sertaogames.cactus2d.components;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.scenes.scene2d.ui.utils.AndroidClipboard;
import com.sertaogames.cactus2d.Component;
import com.sertaogames.cactus2d.Cactus2DApplication;

public class MusicPlayer {

	private AssetManager musics = new AssetManager();
	private List<Music> musicList = new ArrayList<Music>();
	private Music current;
	public float volume;
	public boolean loopingAll = false;
	public boolean playing = true;

	public MusicPlayer(float v) {
		volume = v;
	}

	public void addMusic(String path) {
		musics.load(path, Music.class);
		musics.finishLoading();
		musicList.add(musics.get(path, Music.class));
	}

	public void play(String name) {
		currentIndex = 0;
		if (current != null)
			current.stop();
		current = musics.get(name, Music.class);
		try {
			current.setVolume(volume);
			current.play();
			current.setLooping(!loopingAll);
		} catch (Exception e) {

		}
	}

	public void clear() {
		stop();
		current = null;
		musics.clear();
		musicList.clear();
	}
	
	public void update() {
		if (playing && current != null) {
			current.setVolume(volume);
		}
		if (playing && current != null) {
			if (!current.isPlaying()) {
				playNext();
			}
		}
	}

	public void stop() {
		if (current != null && current.isPlaying())
			current.stop();
	}

	private int currentIndex = 0;

	public void playNext() {
		currentIndex++;
		if (currentIndex >= musicList.size())
			currentIndex = 0;
		play(currentIndex);
	}

	public void play(int i) {
		if (i == currentIndex && current != null && current.isPlaying())
			return;
		if (current != null) {
			current.stop();
		}
		current = musicList.get(i);
		currentIndex = i;
		current.setLooping(!loopingAll);
		current.play();
	}

	public void destroy() {
		if (current != null)
			current.stop();
	}
}
