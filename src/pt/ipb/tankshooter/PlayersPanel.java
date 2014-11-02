package pt.ipb.tankshooter;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;

import pt.ipb.tankshooter.net.PlayersIconCellRenderer;

@SuppressWarnings("serial")
public class PlayersPanel extends JPanel {
	TableModel playerTableModel;

	public PlayersPanel(TableModel playerTableModel) {
		super(new BorderLayout());
		this.playerTableModel = playerTableModel;

		initComponents();
	}

	private void initComponents() {
		JTable playerTable = new JTable(playerTableModel);
		playerTable.setDefaultRenderer(Image.class, new PlayersIconCellRenderer());
		playerTable.getColumn("").setMaxWidth(40);
		playerTable.getColumn("Pontos").setMaxWidth(50);
		add(new JScrollPane(playerTable), BorderLayout.CENTER);
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(200, super.getPreferredSize().width);
	}
}
