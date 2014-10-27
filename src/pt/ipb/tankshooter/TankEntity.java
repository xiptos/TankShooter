package pt.ipb.tankshooter;

import pt.ipb.game.engine.Entity;
import pt.ipb.game.engine.Sprite;

public class TankEntity extends Entity {
	/** The game in which the ship exists */
	private TankShooterGame game;
	private String name;
	private int num;
	
	/**
	 * Create a new entity to represent the players ship
	 *  
	 * @param game The game in which the ship is being created
	 * @param ref The reference to the sprite to show for the ship
	 * @param x The initial x location of the player's ship
	 * @param y The initial y location of the player's ship
	 */
	public TankEntity(String id, int num, TankShooterGame game,Sprite[] tankSprites,int x,int y) {
		super(id, tankSprites,x,y);
		this.name = id;
		this.game = game;
		this.num = num;
	}
	
	public int getNum() {
		return num;
	}
	
	/**
	 * Request that the ship move itself based on an elapsed ammount of
	 * time
	 * 
	 * @param moveSpeed The time that has elapsed since last move (ms)
	 */
	public void move(double moveSpeed) {
		// if we're moving left and have reached the left hand side
		// of the screen, don't move
		if (Math.abs(getAngle())>Math.PI/2 && (x < 10)) {
			return;
		}
		// if we're moving right and have reached the right hand side
		// of the screen, don't move
		if (Math.abs(getAngle())<Math.PI/2 && (x > 750)) {
			return;
		}

		if (Math.abs(getAngle())>Math.PI && (y > 550)) {
			return;
		}

		if (Math.abs(getAngle())<Math.PI && (y < 10)) {
			return;
		}

		super.move(moveSpeed);
	}
	
	/**
	 * Notification that the player's ship has collided with something
	 * 
	 * @param other The entity with which the ship has collided
	 */
	public void collidedWith(Entity other) {
		// if its an alien, notify the game that the player
		// is dead
		if (other instanceof ShotEntity) {
			game.notifyDeath();
		}
	}

	public String getName() {
		return name;
	}
}