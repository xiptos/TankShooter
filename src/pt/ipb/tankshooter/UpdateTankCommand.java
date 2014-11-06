package pt.ipb.tankshooter;

import pt.ipb.game.engine.Command;
import pt.ipb.tankshooter.model.Player;

public class UpdateTankCommand implements Command {
	TankEntity tank;
	Player player;

	public UpdateTankCommand(TankEntity tank, Player player) {
		this.tank = tank;
		this.player = player;
	}

	@Override
	public void execute() {
		if (tank != null) {
			tank.setAngle(player.getAngle());
			tank.setX(player.getX());
			tank.setY(player.getY());
			tank.getPlayer().setPoints(player.getPoints());
		}
	}

}
