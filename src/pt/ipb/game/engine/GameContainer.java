package pt.ipb.game.engine;

public interface GameContainer {
	/**
	 * Called by the game loop at the start of the game.
	 * 
	 * @param loop
	 *            game loop that called startup.
	 * @param game
	 *            currently running game.
	 */
	void init(GameLoop loop, Game game);

	/**
	 * Run the given game with the game loop being managed by the provided game
	 * loop.
	 */
	void start();

	/**
	 * Request that the game stop after the current update or draw.
	 */
	void stop();

	/**
	 * Called by the game loop at.
	 * 
	 * @param delta
	 *            time since the last update.
	 */
	void update(double delta);

	/**
	 * Called by the game loop.
	 */
	void draw();

	/**
	 * Called by the game loop at the end of the game.
	 */
	void shutdown();

	/**
	 * Register input handlers (keyboard, network, mouse, touch...)
	 */
	void addInputHandler(InputHandler inputHandler);

	/**
	 * Unregister input handlers (keyboard, network, mouse, touch...)
	 */
	void removeInputHandler(InputHandler inputHandler);

}
