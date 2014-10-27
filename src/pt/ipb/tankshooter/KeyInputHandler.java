package pt.ipb.tankshooter;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import pt.ipb.game.engine.Command;
import pt.ipb.game.engine.InputHandler;

public class KeyInputHandler extends KeyAdapter implements InputHandler {

	private static final long PERIOD = 100;
	List<Command> commandList;
	TankEntity tank;
	TankShooterGame game;
	
	Timer fwTimer = null;
	Timer rightTimer = null;
	Timer leftTimer = null;

	public KeyInputHandler(TankShooterGame game, TankEntity tank) {
		this.tank = tank;
		this.game = game;
		commandList = new ArrayList<>();
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			if (leftTimer==null) {
				leftTimer = new Timer();
				leftTimer.scheduleAtFixedRate(new TimerTask() {
					@Override
					public void run() {
						commandList.add(new TurnLeftCommand(tank, -TankShooterGame.ANGLE_SPEED));
					}
				}, 0, PERIOD);
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if (rightTimer==null) {
				rightTimer = new Timer();
				rightTimer.scheduleAtFixedRate(new TimerTask() {
					@Override
					public void run() {
						commandList.add(new TurnRightCommand(tank, TankShooterGame.ANGLE_SPEED));
					}
				}, 0, PERIOD);
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			if (fwTimer==null) {
				fwTimer = new Timer();
				fwTimer.scheduleAtFixedRate(new TimerTask() {
					@Override
					public void run() {
						commandList.add(new ForwardCommand(tank, TankShooterGame.MOVE_SPEED));
					}
				}, 0, PERIOD);
			}
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
			leftTimer.cancel();
			leftTimer = null;
			commandList.add(new TurnLeftCommand(tank, 0));
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			rightTimer.cancel();
			rightTimer = null;
			commandList.add(new TurnRightCommand(tank, 0));
		}
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			fwTimer.cancel();
			fwTimer = null;
			commandList.add(new ForwardCommand(tank, 0));
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
