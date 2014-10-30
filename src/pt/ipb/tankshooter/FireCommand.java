package pt.ipb.tankshooter;

import pt.ipb.game.engine.Command;

public class FireCommand implements Command {
	TankEntity tank;
	private TankShooterGame game;

	public FireCommand(TankShooterGame game, TankEntity tank) {
		this.tank = tank;
		this.game = game;
	}

	@Override
	public void execute() {
		if (tank != null) {
			game.tryToFire(tank);
		}
	}

}
