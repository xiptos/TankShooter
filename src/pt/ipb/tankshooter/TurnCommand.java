package pt.ipb.tankshooter;

import pt.ipb.game.engine.Command;

public class TurnCommand implements Command {
	double angle;
	TankEntity tank;

	public TurnCommand(TankEntity tank, double angle) {
		this.tank = tank;
		this.angle = angle;
	}

	@Override
	public void execute() {
		if (tank != null) {
			tank.setAngleSpeed(angle);
		}
	}

}
