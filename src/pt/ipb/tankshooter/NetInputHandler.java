package pt.ipb.tankshooter;

import java.util.ArrayList;
import java.util.List;

import pt.ipb.game.engine.Command;
import pt.ipb.game.engine.InputHandler;
import pt.ipb.tankshooter.net.NetworkEvent;
import pt.ipb.tankshooter.net.NetworkListener;
import pt.ipb.tankshooter.net.Player;

public class NetInputHandler implements NetworkListener, InputHandler {

	List<Command> commandList = new ArrayList<>();
	private TankShooterGame game;

	public NetInputHandler(TankShooterGame game) {
		this.game = game;
	}

	@Override
	public List<Command> handleInput() {
		synchronized (commandList) {
			List<Command> commandsToProcess = new ArrayList<>(commandList);
			commandList.clear();
			return commandsToProcess;
		}
	}

	@Override
	public void playerEntered(NetworkEvent e) {
	}

	@Override
	public void playerExited(NetworkEvent e) {
	}

	@Override
	public void playerUpdated(NetworkEvent e) {
		Player player = e.getPlayer();
		TankEntity tank = game.getTank(player.getId());
		synchronized (commandList) {
			commandList.add(new UpdateTankCommand(tank, player));
		}
	}
}
