package pt.ipb.tankshooter;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import pt.ipb.game.engine.Command;
import pt.ipb.game.engine.InputHandler;

public class KeyInputHandler extends KeyAdapter implements InputHandler {

	List<Command> commandList;
	TankEntity tank;
	TankShooterGame game;

	public KeyInputHandler(TankShooterGame game, TankEntity tank) {
		this.tank = tank;
		this.game = game;
		commandList = new ArrayList<>();
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			commandList.add(new TurnCommand(tank, -TankShooterGame.ANGLE_SPEED));
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			commandList.add(new TurnCommand(tank, TankShooterGame.ANGLE_SPEED));
		}
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			commandList.add(new MoveCommand(tank, TankShooterGame.MOVE_SPEED));
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			commandList.add(new MoveCommand(tank, -TankShooterGame.MOVE_SPEED));
		}
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			commandList.add(new FireCommand(game, tank));
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
			commandList.add(new TurnCommand(tank, 0));
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			commandList.add(new TurnCommand(tank, 0));
		}
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			commandList.add(new MoveCommand(tank, 0));
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			commandList.add(new MoveCommand(tank, 0));
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
