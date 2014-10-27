package pt.ipb.tankshooter.net;

import java.util.ArrayList;
import java.util.List;

import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;

import pt.ipb.game.engine.Command;
import pt.ipb.game.engine.InputHandler;
import pt.ipb.tankshooter.TankEntity;
import pt.ipb.tankshooter.TankShooterGame;
import pt.ipb.tankshooter.UpdateTankCommand;

public class NetUcastInputHandler extends ReceiverAdapter implements InputHandler {

	List<Command> commandList = new ArrayList<>();
	private TankShooterGame game;
	private JChannel channel;

	public NetUcastInputHandler(TankShooterGame game, String clusterName) throws Exception {
		this.game = game;
		this.channel = new JChannel("etc/protocol.xml");
		this.channel.setReceiver(this);
		this.channel.connect(clusterName);
	}
	
	public void sendUpdate(TankEntity tank) throws Exception {
		NCUpdatePlayer updatePlayer = new NCUpdatePlayer(tank);
		channel.send(null, updatePlayer);
	}

	@Override
	public void receive(Message msg) {
		Object obj = msg.getObject();
		if (obj instanceof NCUpdatePlayer) {
			NCUpdatePlayer updatePlayer = (NCUpdatePlayer) obj;
			TankEntity tank = game.getTank(updatePlayer.getId());
			commandList.add(new UpdateTankCommand(tank, updatePlayer));
		}
	}

	@Override
	public List<Command> handleInput() {
		List<Command> commandsToProcess = new ArrayList<>(commandList);
		commandList.clear();
		return commandsToProcess;
	}
}
