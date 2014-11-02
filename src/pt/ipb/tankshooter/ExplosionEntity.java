package pt.ipb.tankshooter;

import pt.ipb.game.engine.Entity;
import pt.ipb.game.engine.Sprite;
import pt.ipb.game.engine.SpriteEntity;

public class ExplosionEntity extends SpriteEntity {

	private TankShooterGame game;

	int spriteCount = 0;

	/**
	 * Create a new entity to represent the players ship
	 * 
	 * @param game
	 *            The game in which the ship is being created
	 * @param ref
	 *            The reference to the sprite to show for the ship
	 * @param d
	 *            The initial x location of the player's ship
	 * @param e
	 *            The initial y location of the player's ship
	 */
	public ExplosionEntity(Sprite[] tankSprites, TankShooterGame game, int x, int y) {
		super(tankSprites, x, y);
		this.game = game;
		this.spriteCount = tankSprites.length;
	}

	@Override
	public void move(double moveSpeed) {
		animation.setAnimationDirection(1);
		timeToUpdate -= moveSpeed * 100;

		if (timeToUpdate < 0) {
			animation.update();
			if (animation.getSprite() != null)
				timeToUpdate = animation.getSprite().getFrameDelay();
			spriteCount--;
		}
		if (spriteCount <= 0) {
			game.removeEntity(this);
		}
	}

	@Override
	public void collidedWith(Entity other) {
	}

}