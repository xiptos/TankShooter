package pt.ipb.tankshooter.model;

import javax.swing.event.EventListenerList;

public abstract class AbstractPlayerModel implements PlayerModel {

	EventListenerList listenerList = new EventListenerList();

	public AbstractPlayerModel() {
	}

	public void addPlayerListener(PlayerListener l) {
		listenerList.add(PlayerListener.class, l);
	}

	public void removePlayerListener(PlayerListener l) {
		listenerList.remove(PlayerListener.class, l);
	}

	protected void firePlayerSelected(PlayerEvent e) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == PlayerListener.class) {
				((PlayerListener) listeners[i + 1]).playerSelected(e);;
			}
		}
	}

	protected void firePlayerEntered(PlayerEvent e) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == PlayerListener.class) {
				((PlayerListener) listeners[i + 1]).playerEntered(e);;
			}
		}
	}

	protected void firePlayerExited(PlayerEvent e) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == PlayerListener.class) {
				((PlayerListener) listeners[i + 1]).playerExited(e);
			}
		}
	}

}
