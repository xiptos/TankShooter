package pt.ipb.game.engine;

public interface GraphicalGameContainer extends GameContainer {

	/**
	 * Get the height of the game container.
	 * 
	 * @return the height of the game container.
	 */
	int getHeight();

	/**
	 * Get the width of the game container.
	 * 
	 * @return the width of the game container.
	 */
	int getWidth();

	/**
	 * Ask if the container supports resizing.
	 * 
	 * @return true is the container is re-sizable.
	 */
	boolean isResizable();

	/**
	 * Request the game container to resize.
	 * 
	 * @param width
	 *            new width.
	 * @param height
	 *            new height.
	 * @return true if the resize was successful.
	 */
	boolean requestResize(int width, int height);

}
