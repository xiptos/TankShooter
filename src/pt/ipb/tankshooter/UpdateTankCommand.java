package pt.ipb.tankshooter;

import pt.ipb.game.engine.Command;
import pt.ipb.tankshooter.net.NCUpdatePlayer;

public class UpdateTankCommand implements Command {
	TankEntity tank;
	double x;
	double y;
	double angle;

	public UpdateTankCommand(TankEntity tank, NCUpdatePlayer obj) {
		this.tank = tank;
		this.x = obj.getX();
		this.y = obj.getY();
		this.angle = obj.getAngle();
	}

	@Override
	public void execute() {
		tank.setAngle(angle);
		tank.setX(x);
		tank.setY(y);
	}

}
