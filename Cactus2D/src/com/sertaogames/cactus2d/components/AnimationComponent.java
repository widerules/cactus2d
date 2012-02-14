package com.sertaogames.cactus2d.components;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.sertaogames.cactus2d.Component;
import com.sertaogames.cactus2d.Cactus2DApplication;

public class AnimationComponent extends Component {

	public Map<String, Animation> animations = new HashMap<String, Animation>();
	public Map<String, Boolean> flips = new HashMap<String, Boolean>();
	private boolean currentFlip = false;
	public Animation currentAnimation;
	public String currentName = "";
	public boolean looping = true;
	private float time = 0;

	@Override
	public void update() {
		if (currentAnimation != null && spriteRenderer != null) {
			spriteRenderer.flip = currentFlip;
			spriteRenderer.flipNumber = currentFlip ? -1 : 1;
			spriteRenderer.spriteRegion = currentAnimation.getKeyFrame(time, looping);
		}
		time += Cactus2DApplication.deltaTime;
	}

	public void play(String animationName, boolean looping) {
		currentName = animationName;
		Animation anim = animations.get(animationName);
		currentFlip = flips.get(animationName);
		this.looping = looping;
		if (anim != currentAnimation) {
			currentAnimation = anim;
			time = 0;
		}
	}

	public void play(String animationName) {
		play(animationName, true);
	}

	public void addAnimation(String name, Texture sheet, int sizeX, int sizeY, int begin, int end, int fps, boolean flipped) {
		int[] frames = new int[end - begin + 1];
		for (int i = 0; i < frames.length; i++)
			frames[i] = i + begin;
		addAnimation(name, sheet, sizeX, sizeY, frames, fps, flipped);
	}

	public void addAnimation(String name, Texture sheet, int sizeX, int sizeY, int[] frameNumbers, int fps, boolean flipped) {
		TextureRegion[][] tmp = TextureRegion.split(sheet, sizeX, sizeY);
		TextureRegion[] frames = new TextureRegion[frameNumbers.length];
		int index = 0;
		int countX = tmp[0].length;

		for (int i = 0; i < frameNumbers.length; i++) {
			int frameNumber = frameNumbers[i];
			int y = frameNumber / countX;
			int x = frameNumber % countX;
			frames[index++] = tmp[y][x];
		}
		float tpf = 1.0f / fps;
		animations.put(name, new Animation(tpf, frames));
		flips.put(name, flipped);
	}

}
