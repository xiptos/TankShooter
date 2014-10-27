package pt.ipb.tankshooter;

import pt.ipb.game.engine.Command;
import pt.ipb.tankshooter.net.Player;

public class UpdateTankCommand implements Command {
	TankEntity tank;
	double x;
	double y;
	double angle;

	public UpdateTankCommand(TankEntity tank, Player player) {
		this.tank = tank;
		this.x = player.getX();
		this.y = player.getY();
		this.angle = player.getAngle();
	}

	@Override
	public void execute() {
		tank.setAngle(angle);
		tank.setX(x);
		tank.setY(y);
	}

}
