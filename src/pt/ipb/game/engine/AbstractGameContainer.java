package pt.ipb.game.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractGameContainer implements GameContainer {
	protected GameLoop gameLoop;
	protected Game game;
	
	List<InputHandler> inputHandlers = new ArrayList<>();

	@Override
	public void init(GameLoop loop, Game game) {
		this.gameLoop = loop;
		this.game = game;
		if (game != null)
			game.startup(this);
	}

	@Override
	public void start() {
		if (gameLoop == null)
			Logger.getLogger(GameLoop.class.getName()).severe("loop == null");
		if (game == null)
			Logger.getLogger(GameLoop.class.getName()).severe("game == null");
		gameLoop.start(this, game);
	}

	@Override
	public void stop() {
		if (gameLoop != null)
			gameLoop.stop();
	}

	@Override
	public void update(double delta) {
		updateCommands();
		if (game != null)
			game.update(this, delta);
	}

	private void updateCommands() {
		for(InputHandler handler : inputHandlers) {
			for(Command command : handler.handleInput()) {
				command.execute();
			}
		}
	}

	@Override
	public void shutdown() {
		if (game != null)
			game.shutdown(this);
		game = null;
	}

	@Override
	public void addInputHandler(InputHandler inputHandler) {
		inputHandlers.add(inputHandler);
	}

	@Override
	public void removeInputHandler(InputHandler inputHandler) {
		inputHandlers.remove(inputHandler);
	}

}
