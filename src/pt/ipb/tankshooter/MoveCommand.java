package pt.ipb.tankshooter;

import pt.ipb.game.engine.Command;

public class MoveCommand implements Command {
	double speed;
	TankEntity tank;

	public MoveCommand(TankEntity tank, double speed) {
		this.tank = tank;
		this.speed = speed;
	}

	@Override
	public void execute() {
		if (tank != null) {
			tank.setSpeed(speed);
		}
	}

}
