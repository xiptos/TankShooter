package pt.ipb.game.engine;

import java.awt.image.BufferedImage;

public class Sprite {
	private BufferedImage frame;
	private int frameDelay;

	public Sprite(BufferedImage frame, int frameDelay) {
		this.frame = frame;
		this.frameDelay = frameDelay;
	}

	public BufferedImage getFrame() {
		return frame;
	}

	public void setFrame(BufferedImage frame) {
		this.frame = frame;
	}

	public int getFrameDelay() {
		return frameDelay;
	}
	
	public void setFrameDelay(int frameDelay) {
		this.frameDelay = frameDelay;
	}
	
	public int getWidth() {
		return frame.getWidth(null);
	}

	public int getHeight() {
		return frame.getHeight(null);
	}
}
