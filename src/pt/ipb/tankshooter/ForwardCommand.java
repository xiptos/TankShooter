package pt.ipb.tankshooter;

import pt.ipb.game.engine.Command;

public class ForwardCommand implements Command {
	double speed;
	TankEntity tank;

	public ForwardCommand(TankEntity tank, double speed) {
		this.tank = tank;
		this.speed = speed;
	}

	@Override
	public void execute() {
		tank.setSpeed(speed);
	}

}
