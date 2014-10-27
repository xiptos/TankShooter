package pt.ipb.tankshooter;

import pt.ipb.game.engine.Command;

public class TurnLeftCommand implements Command {
	double angle;
	TankEntity tank;

	public TurnLeftCommand(TankEntity tank, double angle) {
		this.tank = tank;
		this.angle = angle;
	}

	@Override
	public void execute() {
		tank.rotate(angle);
	}

}
