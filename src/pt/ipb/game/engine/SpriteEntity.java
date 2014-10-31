package pt.ipb.game.engine;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.UUID;

/**
 * An entity represents any element that appears in the game. The entity is
 * responsible for resolving collisions and movement based on a set of
 * properties defined either by subclass or externally.
 * 
 * Note that doubles are used for positions. This may seem strange given that
 * pixels locations are integers. However, using double means that an entity can
 * move a partial pixel. It doesn't of course mean that they will be display
 * half way through a pixel but allows us not lose accuracy as we move.
 * 
 * @author Kevin Glass
 */
public abstract class SpriteEntity extends Entity {
	/** The sprite that represents this entity */
	// Images for each animation
	private Sprite[] sprites;
	private Animation animation;
	/** time to update animation */
	protected double timeToUpdate = 0;

	public SpriteEntity(Sprite[] sprites, int x, int y) {
		this(UUID.randomUUID().toString(), sprites, x, y);
	}

	/**
	 * Construct a entity based on a sprite image and a location.
	 * 
	 * @param ref
	 *            The reference to the image to be displayed for this entity
	 * @param x
	 *            The initial x location of this entity
	 * @param y
	 *            The initial y location of this entity
	 */
	public SpriteEntity(String id, Sprite[] sprites, int x, int y) {
		super(id, null, x, y);
		this.sprites = sprites;
		this.animation = new Animation(sprites);
		this.animation.start();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof SpriteEntity) {
			SpriteEntity other = (SpriteEntity) obj;
			if (other.getId() != null) {
				return other.getId().equals(getId());
			}
		}
		return false;
	}

	/**
	 * Request that this entity move itself based on a certain ammount of time
	 * passing.
	 * 
	 * @param moveSpeed
	 *            The ammount of time that has passed in milliseconds
	 */
	public void move(double moveSpeed) {
		super.move(moveSpeed);

		animation.setAnimationDirection(speed < 0 ? -1 : 1);
		timeToUpdate -= moveSpeed * Math.abs(speed);

		if (timeToUpdate < 0) {
			animation.update();
			timeToUpdate = animation.getSprite().getFrameDelay();
		}
	}

	/**
	 * Draw this entity to the graphics context provided
	 * 
	 * @param g
	 *            The graphics context on which to draw
	 */
	@Override
	public void draw(Graphics g) {
		BufferedImage image = new BufferedImage(animation.getSprite().getWidth(), animation.getSprite().getHeight(),
				BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D b2g = image.createGraphics();
		AffineTransform saveXform = b2g.getTransform();
		AffineTransform at = new AffineTransform();
		at.rotate(angle, image.getWidth() / 2, image.getHeight() / 2);
		b2g.transform(at);
		b2g.drawImage(animation.getSprite().getFrame(), 0, 0, null);
		b2g.setTransform(saveXform);

		g.drawImage(image, (int) x, (int) y, null);
	}

	/**
	 * Get the x location of this entity
	 * 
	 * @return The x location of this entity
	 */
	public int getX() {
		return (int) x;
	}

	/**
	 * Get the y location of this entity
	 * 
	 * @return The y location of this entity
	 */
	public int getY() {
		return (int) y;
	}

	public int getWidth() {
		if (sprites.length != 0) {
			return sprites[0].getWidth();
		}
		return 0;
	}

	public int getHeight() {
		if (sprites.length != 0) {
			return sprites[0].getHeight();
		}
		return 0;
	}

	public Image getIcon() {
		if (sprites.length > 0) {
			return sprites[0].getFrame();
		}
		return null;
	}

}