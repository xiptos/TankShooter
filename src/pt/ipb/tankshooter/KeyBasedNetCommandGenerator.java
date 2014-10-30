package pt.ipb.tankshooter;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import pt.ipb.tankshooter.net.NCPlayerUpdated.COMMAND;
import pt.ipb.tankshooter.net.NetworkPlayers;

public class KeyBasedNetCommandGenerator extends KeyAdapter implements NetCommandGenerator {
	TankEntity tank;
	TankShooterGame game;
	private NetworkPlayers networkPlayers;

	boolean leftDown = false;
	boolean rightDown = false;
	boolean fwDown = false;
	boolean backDown = false;

	public KeyBasedNetCommandGenerator(TankShooterGame game, TankEntity tank, NetworkPlayers networkPlayers) {
		this.tank = tank;
		this.game = game;
		this.networkPlayers = networkPlayers;
	}

	public void keyPressed(KeyEvent e) {
		List<COMMAND> commands = new ArrayList<>();

		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			leftDown = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			rightDown = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			fwDown = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			backDown = true;
		}

		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			commands.add(COMMAND.FIRE);
		}
		commands.add(leftDown ? COMMAND.TURN_LEFT : COMMAND.STOP_TURN_LEFT);
		commands.add(rightDown ? COMMAND.TURN_RIGHT : COMMAND.STOP_TURN_RIGHT);
		commands.add(fwDown ? COMMAND.FW : COMMAND.STOP);
		commands.add(backDown ? COMMAND.BACK : COMMAND.STOP_TURN_LEFT);
		
		try {
			networkPlayers.updatePlayer(tank.getPlayer(), commands);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}

	/**
	 * Notification from AWT that a key has been released.
	 *
	 * @param e
	 *            The details of the key that was released
	 */
	public void keyReleased(KeyEvent e) {
		List<COMMAND> commands = new ArrayList<>();

		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			leftDown = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			rightDown = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			fwDown = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			backDown = false;
		}

		commands.add(leftDown ? COMMAND.TURN_LEFT : COMMAND.STOP_TURN_LEFT);
		commands.add(rightDown ? COMMAND.TURN_RIGHT : COMMAND.STOP_TURN_RIGHT);
		commands.add(fwDown ? COMMAND.FW : COMMAND.STOP);
		commands.add(backDown ? COMMAND.BACK : COMMAND.STOP_TURN_LEFT);
		try {
			networkPlayers.updatePlayer(tank.getPlayer(), commands);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	public void keyTyped(KeyEvent e) {
	}

}
