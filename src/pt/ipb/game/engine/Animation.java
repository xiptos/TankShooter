package pt.ipb.game.engine;

import java.util.ArrayList;
import java.util.List;

public class Animation {
	private int frameCount; // Counts ticks for change
	private int frameDelay; // frame delay 1-12 (You will have to play around
							// with this)
	private int currentFrame; // animations current frame
	private int animationDirection; // animation direction (i.e counting forward
									// or backward)
	private int totalFrames; // total amount of frames for your animation

	private boolean stopped; // has animations stopped

	private List<Sprite> spriteList = new ArrayList<Sprite>(); // Arraylist of frames

	public Animation(Sprite[] sprites) {
		this.stopped = true;

		for (int i = 0; i < sprites.length; i++) {
			this.spriteList.add(sprites[i]);
		}

		this.frameCount = 0;
		this.currentFrame = 0;
		this.animationDirection = 1;
		this.totalFrames = this.spriteList.size();

	}

	public void setAnimationDirection(int animationDirection) {
		this.animationDirection = animationDirection;
	}
	
	public void start() {
		if (!stopped) {
			return;
		}

		if (spriteList.size() == 0) {
			return;
		}

		stopped = false;
	}

	public void stop() {
		if (spriteList.size() == 0) {
			return;
		}

		stopped = true;
	}

	public void restart() {
		if (spriteList.size() == 0) {
			return;
		}

		stopped = false;
		currentFrame = 0;
	}

	public void reset() {
		this.stopped = true;
		this.frameCount = 0;
		this.currentFrame = 0;
	}

	public Sprite getSprite() {
		return spriteList.get(currentFrame);
	}

	public void update() {
		if (!stopped) {
			frameCount++;

			if (frameCount > frameDelay) {
				frameCount = 0;
				currentFrame += animationDirection;

				if (currentFrame > totalFrames - 1) {
					currentFrame = 0;
				} else if (currentFrame < 0) {
					currentFrame = totalFrames - 1;
				}
			}
		}

	}

}
