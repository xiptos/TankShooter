package pt.ipb.tankshooter;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import pt.ipb.game.engine.Command;
import pt.ipb.game.engine.InputHandler;
import pt.ipb.tankshooter.net.NCPlayerUpdated;
import pt.ipb.tankshooter.net.NetworkPlayers;

public class KeyInputHandler extends KeyAdapter implements InputHandler {

	List<Command> commandList;
	TankEntity tank;
	TankShooterGame game;
	private NetworkPlayers networkPlayers;

	public KeyInputHandler(TankShooterGame game, TankEntity tank, NetworkPlayers networkPlayers) {
		this.tank = tank;
		this.game = game;
		this.networkPlayers = networkPlayers;
		commandList = new ArrayList<>();
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			commandList.add(new TurnLeftCommand(tank, -TankShooterGame.ANGLE_SPEED));
			try {
				networkPlayers.updatePlayer(tank.getPlayer(), NCPlayerUpdated.COMMAND.TURN_LEFT);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			commandList.add(new TurnRightCommand(tank, TankShooterGame.ANGLE_SPEED));
			try {
				networkPlayers.updatePlayer(tank.getPlayer(), NCPlayerUpdated.COMMAND.TURN_RIGHT);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			commandList.add(new ForwardCommand(tank, TankShooterGame.MOVE_SPEED));
			try {
				networkPlayers.updatePlayer(tank.getPlayer(), NCPlayerUpdated.COMMAND.FW);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			commandList.add(new FireCommand(game, tank));
			try {
				networkPlayers.updatePlayer(tank.getPlayer(), NCPlayerUpdated.COMMAND.FIRE);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * Notification from AWT that a key has been released.
	 *
	 * @param e
	 *            The details of the key that was released
	 */
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			commandList.add(new TurnLeftCommand(tank, 0));
			try {
				networkPlayers.updatePlayer(tank.getPlayer(), NCPlayerUpdated.COMMAND.STOP_TURN_LEFT);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			commandList.add(new TurnRightCommand(tank, 0));
			try {
				networkPlayers.updatePlayer(tank.getPlayer(), NCPlayerUpdated.COMMAND.STOP_TURN_RIGHT);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			commandList.add(new ForwardCommand(tank, 0));
			try {
				networkPlayers.updatePlayer(tank.getPlayer(), NCPlayerUpdated.COMMAND.STOP);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	public void keyTyped(KeyEvent e) {
	}

	@Override
	public List<Command> handleInput() {
		List<Command> commandsToProcess = new ArrayList<>(commandList);
		commandList.clear();
		return commandsToProcess;
	}
}
