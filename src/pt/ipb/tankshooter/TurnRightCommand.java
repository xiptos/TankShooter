package pt.ipb.tankshooter;

import pt.ipb.game.engine.Command;

public class TurnRightCommand implements Command {
	double angle;
	TankEntity tank;

	public TurnRightCommand(TankEntity tank, double angle) {
		this.tank = tank;
		this.angle = angle;
	}

	@Override
	public void execute() {
		tank.setAngleSpeed(angle);
	}

}
