package pt.ipb.game.engine;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
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
public abstract class Entity {
	String id;
	/** The current x location of this entity */
	private double x;
	/** The current y location of this entity */
	private double y;
	/** The sprite that represents this entity */
	// Images for each animation
	private BufferedImage image;
	/** The current speed of this entity (pixels/sec) */
	protected double speed;
	/** The current angle of this entity (radians) */
	private double angle;

	/** The rectangle used for this entity during collisions resolution */
	private Rectangle me = new Rectangle();
	/** The rectangle used for other entities during collision resolution */
	private Rectangle him = new Rectangle();
	private double angleSpeed;

	public Entity(BufferedImage image, int x, int y) {
		this(UUID.randomUUID().toString(), image, x, y);
	}

	/**
	 * Construct a entity based on a sprite image and a location.
	 * 
	 * @param ref
	 *            The reference to the image to be displayed for this entity
	 * @param d
	 *            The initial x location of this entity
	 * @param e
	 *            The initial y location of this entity
	 */
	public Entity(String id, BufferedImage image, double d, double e) {
		this.id = id;
		this.image = image;
		this.x = d;
		this.y = e;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Entity) {
			Entity other = (Entity) obj;
			if (other.getId() != null) {
				return other.getId().equals(getId());
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		if (getId() == null) {
			return super.hashCode();
		} else {
			return getId().hashCode();
		}
	}

	public String getId() {
		return id;
	}

	/**
	 * Request that this entity move itself based on a certain ammount of time
	 * passing.
	 * 
	 * @param moveSpeed
	 *            The ammount of time that has passed in milliseconds
	 */
	public void move(double moveSpeed) {
		setAngle((getAngle() + angleSpeed) % (2 * Math.PI));
		// update the location of the entity based on move speeds
		double dx = Math.cos(getAngle()) * speed;
		double dy = Math.sin(getAngle()) * speed;
		setX(getX()+ (moveSpeed * dx));
		setY(getY() + (moveSpeed * dy));
	}

	public void rotate(double angle) {
		setAngle((getAngle() + angle) % (2 * Math.PI));
	}

	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}

	/**
	 * Set the horizontal speed of this entity
	 * 
	 * @param dx
	 *            The horizontal speed of this entity (pixels/sec)
	 */
	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public void setAngleSpeed(double angleSpeed) {
		this.angleSpeed = angleSpeed;
	}

	/**
	 * Get the horizontal speed of this entity
	 * 
	 * @return The horizontal speed of this entity (pixels/sec)
	 */
	public double getSpeed() {
		return speed;
	}

	/**
	 * Draw this entity to the graphics context provided
	 * 
	 * @param g
	 *            The graphics context on which to draw
	 */
	public void draw(Graphics g) {
		BufferedImage image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D b2g = image.createGraphics();
		AffineTransform saveXform = b2g.getTransform();
		AffineTransform at = new AffineTransform();
		at.rotate(getAngle(), image.getWidth() / 2, image.getHeight() / 2);
		b2g.transform(at);
		b2g.drawImage(getIcon(), 0, 0, null);
		b2g.setTransform(saveXform);

		g.drawImage(image, (int) getX(), (int) getY(), null);
	}

	/**
	 * Do the logic associated with this entity. This method will be called
	 * periodically based on game events
	 */
	public void doLogic() {
	}

	/**
	 * Get the x location of this entity
	 * 
	 * @return The x location of this entity
	 */
	public double getX() {
		return x;
	}

	/**
	 * Get the y location of this entity
	 * 
	 * @return The y location of this entity
	 */
	public double getY() {
		return y;
	}

	public int getWidth() {
		if (getIcon() != null) {
			return getIcon().getWidth(null);
		}
		return 0;
	}

	public int getHeight() {
		if (getIcon() != null) {
			return getIcon().getHeight(null);
		}
		return 0;
	}

	/**
	 * Check if this entity collised with another.
	 * 
	 * @param other
	 *            The other entity to check collision against
	 * @return True if the entities collide with each other
	 */
	public boolean collidesWith(Entity other) {
		me.setBounds((int) getX(), (int) getY(), getWidth(), getHeight());
		him.setBounds((int) other.getX(), (int) other.getY(), other.getWidth(), other.getHeight());

		return me.intersects(him);
	}

	/**
	 * Notification that this entity collided with another.
	 * 
	 * @param other
	 *            The entity with which this entity collided.
	 */
	public abstract void collidedWith(Entity other);

	public Image getIcon() {
		return image;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

}