package pt.ipb.tankshooter;

import pt.ipb.game.engine.Entity;
import pt.ipb.game.engine.Sprite;
import pt.ipb.game.engine.SpriteEntity;
import pt.ipb.tankshooter.model.Player;

public class TankEntity extends SpriteEntity {
	/** The game in which the ship exists */
	private TankShooterGame game;
	Player player;
	
	/**
	 * Create a new entity to represent the players ship
	 *  
	 * @param game The game in which the ship is being created
	 * @param ref The reference to the sprite to show for the ship
	 * @param d The initial x location of the player's ship
	 * @param e The initial y location of the player's ship
	 */
	public TankEntity(Player player, TankShooterGame game,Sprite[] tankSprites,double x,double y) {
		super(player.getId(), tankSprites,x,y);
		this.player = player;
		this.game = game;
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
		if (getX() < 10) {
			setX(10);
			return;
		}
		// if we're moving right and have reached the right hand side
		// of the screen, don't move
		if (getX() > game.getWidth()-50) {
			setX(game.getWidth() -50);
			return;
		}

		if (getY() > game.getHeight()-50) {
			setY(game.getHeight() - 50);
			return;
		}

		if (getY() < 10) {
			setY(10);
			return;
		}

		super.move(moveSpeed);
	}

	@Override
	public void setX(double x) {
		player.setX(x);
	}
	
	@Override
	public double getX() {
		return player.getX();
	}
	
	@Override
	public void setY(double y) {
		player.setY(y);
	}
	
	@Override
	public double getY() {
		return player.getY();
	}
	
	@Override
	public void setAngle(double angle) {
		player.setAngle(angle);
	}
	
	@Override
	public double getAngle() {
		return player.getAngle();
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
		} else if(other instanceof TankEntity) {
			setSpeed(-getSpeed());
		}
	}

	public Player getPlayer() {
		return player;
	}

}