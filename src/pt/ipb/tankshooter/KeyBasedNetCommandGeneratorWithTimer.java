package pt.ipb.tankshooter;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import pt.ipb.tankshooter.net.NCPlayerUpdated.COMMAND;
import pt.ipb.tankshooter.net.NetworkPlayers;

public class KeyBasedNetCommandGeneratorWithTimer extends KeyAdapter implements NetCommandGenerator {

	TankEntity tank;
	TankShooterGame game;
	private NetworkPlayers networkPlayers;

	Timer leftTimer = null;
	Timer rightTimer = null;
	Timer fwTimer = null;
	Timer backTimer = null;
	private int period;

	public KeyBasedNetCommandGeneratorWithTimer(TankShooterGame game, TankEntity tank, NetworkPlayers networkPlayers, int PERIOD) {
		this.tank = tank;
		this.game = game;
		this.networkPlayers = networkPlayers;
		this.period = PERIOD;
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			if (leftTimer == null) {
				leftTimer = new Timer();
				leftTimer.scheduleAtFixedRate(new TimerTask() {

					@Override
					public void run() {
						try {
							List<COMMAND> commands = new ArrayList<>();
							commands.add(leftTimer != null ? COMMAND.TURN_LEFT : COMMAND.STOP_TURN_LEFT);
							commands.add(rightTimer != null ? COMMAND.TURN_RIGHT : COMMAND.STOP_TURN_RIGHT);
							commands.add(fwTimer != null ? COMMAND.FW : COMMAND.STOP);
							commands.add(backTimer != null ? COMMAND.BACK : COMMAND.STOP_TURN_LEFT);
							networkPlayers.updatePlayer(tank.getPlayer(), commands);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				}, 0, period);
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if (rightTimer == null) {
				rightTimer = new Timer();
				rightTimer.scheduleAtFixedRate(new TimerTask() {

					@Override
					public void run() {
						try {
							List<COMMAND> commands = new ArrayList<>();
							commands.add(leftTimer != null ? COMMAND.TURN_LEFT : COMMAND.STOP_TURN_LEFT);
							commands.add(rightTimer != null ? COMMAND.TURN_RIGHT : COMMAND.STOP_TURN_RIGHT);
							commands.add(fwTimer != null ? COMMAND.FW : COMMAND.STOP);
							commands.add(backTimer != null ? COMMAND.BACK : COMMAND.STOP_TURN_LEFT);
							networkPlayers.updatePlayer(tank.getPlayer(), commands);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				}, 0, period);
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			if (fwTimer == null) {
				fwTimer = new Timer();
				fwTimer.scheduleAtFixedRate(new TimerTask() {

					@Override
					public void run() {
						try {
							List<COMMAND> commands = new ArrayList<>();
							commands.add(leftTimer != null ? COMMAND.TURN_LEFT : COMMAND.STOP_TURN_LEFT);
							commands.add(rightTimer != null ? COMMAND.TURN_RIGHT : COMMAND.STOP_TURN_RIGHT);
							commands.add(fwTimer != null ? COMMAND.FW : COMMAND.STOP);
							commands.add(backTimer != null ? COMMAND.BACK : COMMAND.STOP_TURN_LEFT);
							networkPlayers.updatePlayer(tank.getPlayer(), commands);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				}, 0, period);
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			if (backTimer == null) {
				backTimer = new Timer();
				backTimer.scheduleAtFixedRate(new TimerTask() {

					@Override
					public void run() {
						try {
							List<COMMAND> commands = new ArrayList<>();
							commands.add(leftTimer != null ? COMMAND.TURN_LEFT : COMMAND.STOP_TURN_LEFT);
							commands.add(rightTimer != null ? COMMAND.TURN_RIGHT : COMMAND.STOP_TURN_RIGHT);
							commands.add(fwTimer != null ? COMMAND.FW : COMMAND.STOP);
							commands.add(backTimer != null ? COMMAND.BACK : COMMAND.STOP_TURN_LEFT);
							networkPlayers.updatePlayer(tank.getPlayer(), commands);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				}, 0, period);
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			try {
				List<COMMAND> commands = new ArrayList<>();
				commands.add(COMMAND.FIRE);
				networkPlayers.updatePlayer(tank.getPlayer(), commands);
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
			leftTimer.cancel();
			leftTimer = null;
			try {
				List<COMMAND> commands = new ArrayList<>();
				commands.add(leftTimer != null ? COMMAND.TURN_LEFT : COMMAND.STOP_TURN_LEFT);
				commands.add(rightTimer != null ? COMMAND.TURN_RIGHT : COMMAND.STOP_TURN_RIGHT);
				commands.add(fwTimer != null ? COMMAND.FW : COMMAND.STOP);
				commands.add(backTimer != null ? COMMAND.BACK : COMMAND.STOP_TURN_LEFT);
				networkPlayers.updatePlayer(tank.getPlayer(), commands);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			rightTimer.cancel();
			rightTimer = null;
			try {
				List<COMMAND> commands = new ArrayList<>();
				commands.add(leftTimer != null ? COMMAND.TURN_LEFT : COMMAND.STOP_TURN_LEFT);
				commands.add(rightTimer != null ? COMMAND.TURN_RIGHT : COMMAND.STOP_TURN_RIGHT);
				commands.add(fwTimer != null ? COMMAND.FW : COMMAND.STOP);
				commands.add(backTimer != null ? COMMAND.BACK : COMMAND.STOP_TURN_LEFT);
				networkPlayers.updatePlayer(tank.getPlayer(), commands);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			fwTimer.cancel();
			fwTimer = null;
			try {
				List<COMMAND> commands = new ArrayList<>();
				commands.add(leftTimer != null ? COMMAND.TURN_LEFT : COMMAND.STOP_TURN_LEFT);
				commands.add(rightTimer != null ? COMMAND.TURN_RIGHT : COMMAND.STOP_TURN_RIGHT);
				commands.add(fwTimer != null ? COMMAND.FW : COMMAND.STOP);
				commands.add(backTimer != null ? COMMAND.BACK : COMMAND.STOP_TURN_LEFT);
				networkPlayers.updatePlayer(tank.getPlayer(), commands);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			backTimer.cancel();
			backTimer = null;
			try {
				List<COMMAND> commands = new ArrayList<>();
				commands.add(leftTimer != null ? COMMAND.TURN_LEFT : COMMAND.STOP_TURN_LEFT);
				commands.add(rightTimer != null ? COMMAND.TURN_RIGHT : COMMAND.STOP_TURN_RIGHT);
				commands.add(fwTimer != null ? COMMAND.FW : COMMAND.STOP);
				commands.add(backTimer != null ? COMMAND.BACK : COMMAND.STOP_TURN_LEFT);
				networkPlayers.updatePlayer(tank.getPlayer(), commands);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	public void keyTyped(KeyEvent e) {
	}

}
