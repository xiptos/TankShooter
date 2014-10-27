package pt.ipb.tankshooter.net;

import java.util.ArrayList;
import java.util.List;

import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.blocks.MessageDispatcher;
import org.jgroups.blocks.RequestHandler;
import org.jgroups.blocks.RequestOptions;

import pt.ipb.game.engine.Command;
import pt.ipb.game.engine.InputHandler;
import pt.ipb.tankshooter.TankEntity;
import pt.ipb.tankshooter.TankShooterGame;
import pt.ipb.tankshooter.UpdateTankCommand;

public class NetInputHandler implements InputHandler, RequestHandler {

	List<Command> commandList = new ArrayList<>();
	private TankShooterGame game;
	private MessageDispatcher dispatcher;

	public NetInputHandler(TankShooterGame game, JChannel channel) throws Exception {
		this.game = game;
		this.dispatcher = new MessageDispatcher(channel, this);
	}
	
	public void sendUpdate(TankEntity tank) throws Exception {
		NCUpdatePlayer updatePlayer = new NCUpdatePlayer(tank);
		dispatcher.castMessage(null, new Message(null, updatePlayer), RequestOptions.ASYNC());
	}

	@Override
	public Object handle(Message msg) throws Exception {
		Object obj = msg.getObject();
		if (obj instanceof NCUpdatePlayer) {
			NCUpdatePlayer updatePlayer = (NCUpdatePlayer) obj;
			TankEntity tank = game.getTank(updatePlayer.getId());
			commandList.add(new UpdateTankCommand(tank, updatePlayer));
		}
		return null;
	}

	@Override
	public List<Command> handleInput() {
		List<Command> commandsToProcess = new ArrayList<>(commandList);
		commandList.clear();
		return commandsToProcess;
	}
}
