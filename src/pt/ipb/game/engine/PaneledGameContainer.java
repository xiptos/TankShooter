package pt.ipb.game.engine;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class PaneledGameContainer extends JPanel implements GraphicalGameContainer, Runnable {
	protected GameLoop gameLoop;
	protected Game game;
	
	List<InputHandler> inputHandlers = new ArrayList<>();

	public PaneledGameContainer(int width, int height) {
		// Double buffer strategy
		super(true);
		setPreferredSize(new Dimension(width, height));
		// create input handlers
		requestFocus();
	}

	@Override
	public boolean isResizable() {
		return true;
	}

	@Override
	public boolean requestResize(int width, int height) {
		setSize(width, height);
		return true;
	}

	@Override
	public void draw() {
		repaint();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		// clear the screen
		g.setColor(Color.black);
		g.fillRect(0, 0, getWidth(), getHeight());
		if (game != null)
			game.draw(this, g2);
	}

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
		requestFocus();
		new Thread(this).start();
	}

	@Override
	public void run() {
		gameLoop.start(this, game);
	};
	
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
