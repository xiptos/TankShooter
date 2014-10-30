package pt.ipb.tankshooter.net;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;

@SuppressWarnings("serial")
public class PlayersIconCellRenderer extends JLabel implements TableCellRenderer {

	public PlayersIconCellRenderer() {
		setHorizontalAlignment(SwingConstants.LEFT);
		setVerticalAlignment(CENTER);
		setOpaque(true);
	}

	public Component getTableCellRendererComponent(JTable table, Object icon, boolean isSelected, boolean hasFocus,
			int row, int column) {
		ImageIcon image = (ImageIcon) icon;
		setIcon(image);
		table.setRowHeight(image.getIconHeight());
		if (isSelected) {
            setBackground(table.getSelectionBackground());
        } else {
            setBackground(table.getBackground());
        }
		return this;
	}
}
