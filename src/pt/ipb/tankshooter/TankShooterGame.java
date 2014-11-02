package pt.ipb.tankshooter;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import pt.ipb.game.engine.Entity;
import pt.ipb.game.engine.Game;
import pt.ipb.game.engine.GameContainer;
import pt.ipb.game.engine.Sprite;
import pt.ipb.game.engine.SpriteSheet;
import pt.ipb.tankshooter.model.Player;
import pt.ipb.tankshooter.model.PlayerEvent;
import pt.ipb.tankshooter.model.PlayerListener;

public class TankShooterGame implements Game, PlayerListener {
	/** The speed at which the player's ship should move (pixels/sec) */
	public static final double SHOT_SPEED = 300;
	/** The speed at which the player's ship should move (pixels/sec) */
	public static final double MOVE_SPEED = 100;
	/** The speed at which the player's ship should move (pixels/sec) */
	public static final double ANGLE_SPEED = 0.05;

	BufferedImage shotImage;
	SpriteSheet tankSheet;

	List<Entity> entities;
	TankEntity tank;
	Image background;

	boolean playing = false;

	/** The time at which last fired a shot */
	private long lastFire = 0;
	/** The interval between our players shot (ms) */
	private long firingInterval = 500;

	private List<Entity> removeList = new ArrayList<>();
	private int width;
	private int height;
	private SpriteSheet explosion1Sheet;
	private SpriteSheet explosion2Sheet;

	public TankShooterGame(int width, int height, Image background) {
		this.background = background;
		this.width = width;
		this.height = height;
		entities = new ArrayList<>();
		tankSheet = new SpriteSheet("pt/ipb/tankshooter/resources/MulticolorTanks.png", 32, 5);
		explosion1Sheet = new SpriteSheet("pt/ipb/tankshooter/resources/explosion2.png", 64, 8);
		explosion2Sheet = new SpriteSheet("pt/ipb/tankshooter/resources/explosion17.png", 64, 8);
		try {
			shotImage = ImageIO.read(this.getClass().getClassLoader()
					.getResource("pt/ipb/tankshooter/resources/shot.gif"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	@Override
	public void startup(GameContainer gc) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(GameContainer gc, double delta) {
		for (int i = 0; i < entities.size(); i++) {
			Entity entity = (Entity) entities.get(i);
			entity.move(delta);
		}

		// brute force collisions, compare every entity against
		// every other entity. If any of them collide notify
		// both entities that the collision has occured
		for (int p = 0; p < entities.size(); p++) {
			for (int s = p + 1; s < entities.size(); s++) {
				Entity me = (Entity) entities.get(p);
				Entity him = (Entity) entities.get(s);

				if (me.collidesWith(him)) {
					me.collidedWith(him);
					him.collidedWith(me);
				}
			}
		}

		// remove any entity that has been marked for clear up
		entities.removeAll(removeList);
		removeList.clear();
	}

	@Override
	public void draw(GameContainer gc, Graphics2D g) {
		g.drawImage(background, 0, 0, null);

		// cycle round drawing all the entities we have in the game
		for (int i = 0; i < entities.size(); i++) {
			Entity entity = (Entity) entities.get(i);

			entity.draw(g);
		}
	}

	@Override
	public void shutdown(GameContainer gc) {
		// TODO Auto-generated method stub

	}

	public void notifyDeath() {
		playing = false;
		System.out.println("Oh não... fui atingido!");
	}

	public void removeEntity(Entity entity) {
		removeList.add(entity);
	}

	public void notifyTankKilled(Player tank, Player shooter) {
		System.out.println("Tank killed...");
		addExplosion(tank);
		shooter.incPoints();
	}

	public void tryToFire(TankEntity tank) {
		// check that we have waiting long enough to fire
		if (System.currentTimeMillis() - lastFire < firingInterval) {
			return;
		}

		// if we waited long enough, create the shot entity, and record the
		// time.
		lastFire = System.currentTimeMillis();

		double h = Math.sqrt((tank.getWidth() / 2) * (tank.getWidth() / 2) + (tank.getHeight() / 2)
				* (tank.getHeight() / 2));
		double dx = Math.cos(tank.getAngle()) * h;
		double dy = Math.sin(tank.getAngle()) * h;
		int x = (int) (tank.getX() + tank.getWidth() / 2 + 2 * dx - 12);
		int y = (int) (tank.getY() + tank.getHeight() / 2 + 2 * dy - 6);

		ShotEntity shot = new ShotEntity(tank.getPlayer(), this, shotImage, x, y);
		shot.setAngle(tank.getAngle());
		shot.setSpeed(SHOT_SPEED);
		entities.add(shot);
	}

	public void addExplosion(Player player) {
		int explosionNum = new Random().nextInt(2);
		Sprite[] explosionSprites = null;
		if (explosionNum > 1) {
			explosionSprites = new Sprite[16];
			for (int i = 0; i < 4; i++) {
				for (int t = 0; t < 4; t++) {
					explosionSprites[i] = explosion1Sheet.getSprite(t, i);
				}
			}
		} else {
			explosionSprites = new Sprite[20];
			for (int i = 0; i < 5; i++) {
				for (int t = 0; t < 4; t++) {
					explosionSprites[i] = explosion2Sheet.getSprite(t, i);
				}
			}
		}

		int dx = getTank(player).getWidth() / 2 - explosionSprites[0].getWidth() / 2;
		int dy = getTank(player).getHeight() / 2 - explosionSprites[0].getHeight() / 2;

		ExplosionEntity explosion = new ExplosionEntity(explosionSprites, this, (int) player.getX() + dx,
				(int) player.getY() + dy);
		entities.add(explosion);
	}

	public void addTank(Player player) {
		Sprite[] tankSprites = new Sprite[8];
		for (int i = 0; i < 8; i++) {
			tankSprites[i] = tankSheet.getSprite(i, entities.size());
		}

		TankEntity tank = new TankEntity(player, this, tankSprites, player.getX(), player.getY());
		if (!entities.contains(tank))
			entities.add(tank);
	}

	public void setTank(TankEntity tank) {
		playing = true;
		this.tank = tank;
	}

	public TankEntity getTank(Player player) {
		for (Entity entity : entities) {
			if (entity instanceof TankEntity) {
				TankEntity tank = (TankEntity) entity;
				if (player.getId().equals(tank.getId())) {
					return tank;
				}
			}
		}
		return null;
	}

	public TankEntity getTank(String id) {
		for (Entity entity : entities) {
			if (id.equals(entity.getId())) {
				return (TankEntity) entity;
			}
		}
		return null;
	}

	public void removeTank(Player player) {
		TankEntity tank = getTank(player);
		removeEntity(tank);
	}

	public boolean isPlaying() {
		return playing;
	}

	@Override
	public void playerEntered(PlayerEvent e) {
		addTank(e.getPlayer());
	}

	@Override
	public void playerExited(PlayerEvent e) {
		removeEntity(getTank(e.getPlayer()));
	}

	@Override
	public void playerSelected(PlayerEvent e) {
		setTank(getTank(e.getPlayer()));
	}

}
