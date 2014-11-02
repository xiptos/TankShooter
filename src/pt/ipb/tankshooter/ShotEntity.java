package pt.ipb.tankshooter;

import java.awt.image.BufferedImage;

import pt.ipb.game.engine.Entity;
import pt.ipb.tankshooter.model.Player;

/**
 * An entity representing a shot fired by the player's ship
 * 
 * @author Kevin Glass
 */
public class ShotEntity extends Entity {
	/** The game in which this entity exists */
	private TankShooterGame game;
	/** True if this shot has been "used", i.e. its hit something */
	private boolean used = false;
	private Player shooter;

	/**
	 * Create a new shot from the player
	 * 
	 * @param game
	 *            The game in which the shot has been created
	 * @param sprite
	 *            The sprite representing this shot
	 * @param x
	 *            The initial x location of the shot
	 * @param y
	 *            The initial y location of the shot
	 */
	public ShotEntity(Player shooter, TankShooterGame game, BufferedImage image, int x, int y) {
		super(image, x, y);
		this.shooter = shooter;
		this.game = game;
	}

	public Player getShooter() {
		return shooter;
	}

	/**
	 * Request that this shot moved based on time elapsed
	 * 
	 * @param delta
	 *            The time that has elapsed since last move
	 */
	@Override
	public void move(double delta) {
		// proceed with normal move
		super.move(delta);
		// if we shot off the screen, remove ourselfs
		if (getY() < 0 || getY() > game.getHeight() || getX() < 0 || getX() > game.getWidth()) {
			game.removeEntity(this);
		}
	}

	/**
	 * Notification that this shot has collided with another entity
	 * 
	 * @parma other The other entity with which we've collided
	 */
	@Override
	public void collidedWith(Entity other) {
		// prevents double kills, if we've already hit something,
		// don't collide
		if (used) {
			return;
		}

		// if we've hit an alien, kill it!
		if (other instanceof TankEntity) {
			// remove the affected entities
			game.removeEntity(this);
			game.removeEntity(other);

			// notify the game that the alien has been killed
			game.notifyTankKilled(shooter);
			used = true;
		}
	}

}