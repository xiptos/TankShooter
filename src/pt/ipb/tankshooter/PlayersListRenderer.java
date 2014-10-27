package pt.ipb.tankshooter;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;

import pt.ipb.game.engine.SpriteSheet;
import pt.ipb.tankshooter.net.Player;

@SuppressWarnings("serial")
public class PlayersListRenderer extends JLabel implements ListCellRenderer<Player> {

	private SpriteSheet spriteSheet;

	public PlayersListRenderer() {
		setHorizontalAlignment(SwingConstants.LEFT);
		setVerticalAlignment(CENTER);
		setOpaque(true);

		spriteSheet = new SpriteSheet("pt/ipb/tankshooter/resources/MulticolorTanks.png", 32, 5);
	}

	/*
	 * This method finds the image and text corresponding to the selected value
	 * and returns the label, set up to display the text and image.
	 */
	public Component getListCellRendererComponent(JList<? extends Player> list, Player value, int index,
			boolean isSelected, boolean cellHasFocus) {
		if (isSelected) {
			setBackground(list.getSelectionBackground());
			setForeground(list.getSelectionForeground());
		} else {
			setBackground(list.getBackground());
			setForeground(list.getForeground());
		}

		if (value != null) {
			// Set the icon and text. If icon was null, say so.
			ImageIcon icon = new ImageIcon(spriteSheet.getSprite(0, index).getFrame());

			String pet = value.getId();
			setIcon(icon);
			if (icon != null) {
				setText(pet);
				setFont(list.getFont());
			}
		} else {
			setText("null");
		}
		return this;
	}

}
