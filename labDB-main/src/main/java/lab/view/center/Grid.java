package lab.view.center;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.awt.GridBagConstraints;
import javax.swing.JButton;
import javax.swing.JPanel;
import lab.model.PostazioneOmbrellone;
import lab.utils.Utils;

public class Grid extends JPanel {

	private final Connection connection;
	private final JButton[][] grid;

	public Grid(final Connection connection) {
		this.connection = connection;
		setLayout(new GridBagLayout());
		var c = new GridBagConstraints();
		grid = new JButton[10][12];
		for (int x = 0; x < 10; x++) {
			c.gridx = x;
			for (int y = 0; y < 12; y++) {
				c.gridy = y;
				var b = new JButton();
				b.setOpaque(true);
				grid[x][y] = b;
				add(b, c);
			}
		}
	}

	public void updateGrid(final int anno, final Date dataInizio, final Date dataFine) {
		resetGrid();
		try {
			addPostazioniOmbrelloni(anno, dataInizio, dataFine);
			addDisponibilitàOmbrelloni(anno, dataInizio, dataFine);
			addPostazioniSedute(anno);
			addDisponibilitàSedute(anno, dataInizio, dataFine);
		} catch (Exception e) {
			return;
		}
	}

	private void addPostazioniSedute(final int anno) throws SQLException {
		final String query = "SELECT numeroSeduta FROM PostazioniSeduteRiva WHERE anno = ? ORDER BY numeroSeduta";
		try (final PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, anno);
			final ResultSet resultSet = statement.executeQuery();
			int x = 0;
			int y = 10;
			while(resultSet.next()) {
				grid[x][y].setText(String.valueOf(resultSet.getInt("numeroSeduta")));
				x++;
				if (x == 10) {
					x = 0;
					y++;
				}
			}
		}
	}

	private void addPostazioniOmbrelloni(final int anno, final Date dataInizio, final Date dataFine) throws SQLException {
		final List<PostazioneOmbrellone> postazioniOmbrelloni;
		final String query = "SELECT * FROM PostazioniOmbrelloni WHERE anno = ? AND dataInizio <= ? AND (dataFine >= ? OR dataFine is null)";
		try (final PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, anno);
			statement.setDate(2, Utils.dateToSqlDate(dataInizio));
			statement.setDate(3, Utils.dateToSqlDate(dataFine));
			final ResultSet resultSet = statement.executeQuery();
			postazioniOmbrelloni = PostazioneOmbrellone.readPostazioniOmbrelloniFromResultSet(resultSet);
		}

		for (var o : postazioniOmbrelloni) {
			grid[o.getColonna()-1][o.getFila()-1].setText(String.valueOf(o.getNumeroOmbrellone()));
		}
	}

	private void addDisponibilitàSedute(final int anno, final Date dataInizio, final Date dataFine) throws NumberFormatException, SQLException {
		for (int x = 0; x < 10; x++) {
			for (int y = 9; y < 12; y++) {
				var b = grid[x][y];
				if(!b.getText().isEmpty()) {
					final String query = "SELECT * FROM SeduteConPrenotazioni WHERE anno = ? "
							+ "AND numeroSeduta = ? AND NOT ((dataInizio < ? AND dataFine < ?) OR (dataInizio > ? AND dataFine > ?))";
					try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
						statement.setInt(1, anno);
						statement.setInt(2, Integer.parseInt(b.getText()));
						statement.setDate(3, Utils.dateToSqlDate(dataInizio));
						statement.setDate(4, Utils.dateToSqlDate(dataInizio));
						statement.setDate(5, Utils.dateToSqlDate(dataFine));
						statement.setDate(6, Utils.dateToSqlDate(dataFine));
						final ResultSet resultSet = statement.executeQuery();
						b.setOpaque(true);
						if (resultSet.next()) {
							b.setBackground(Color.RED);
							b.addActionListener(l -> {
								try {
									new VisualSedutaConPrenotazione(connection,
											Integer.parseInt(b.getText()), anno, dataInizio, dataFine);
								} catch (NumberFormatException | SQLException e) {
									throw new IllegalStateException();
								}
							});
						} else {
							b.setBackground(Color.GREEN);
							b.addActionListener(l -> new AddSedutaConPrenotazione(connection,
									Integer.parseInt(b.getText()), anno, dataInizio, dataFine));
						}
					}
				}
			}
		}
	}

	private void addDisponibilitàOmbrelloni(final int anno, final Date dataInizio, final Date dataFine) throws NumberFormatException, SQLException {
		for (int x = 0; x < 10; x++) {
			for (int y = 0; y < 10; y++) {
				var b = grid[x][y];
				if(!b.getText().isEmpty()) {
					final String query = "SELECT * FROM OmbrelloniConPrenotazione WHERE anno = ? "
							+ "AND numeroOmbrellone = ? AND NOT ((dataInizio < ? AND dataFine < ?) OR (dataInizio > ? AND dataFine > ?))";
					try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
						statement.setInt(1, anno);
						statement.setInt(2, Integer.parseInt(b.getText()));
						statement.setDate(3, Utils.dateToSqlDate(dataInizio));
						statement.setDate(4, Utils.dateToSqlDate(dataInizio));
						statement.setDate(5, Utils.dateToSqlDate(dataFine));
						statement.setDate(6, Utils.dateToSqlDate(dataFine));
						final ResultSet resultSet = statement.executeQuery();
						b.setOpaque(true);
						if (resultSet.next()) {
							b.setBackground(Color.RED);
							b.addActionListener(l -> {
								try {
									new VisualOmbrelloneConPrenotazione(connection,
											Integer.parseInt(b.getText()), anno, dataInizio, dataFine);
								} catch (NumberFormatException | SQLException e) {
									throw new IllegalStateException();
								}
							});
						} else {
							b.setBackground(Color.GREEN);
							b.addActionListener(l -> new AddOmbrelloneConPrenotazione(connection,
									Integer.parseInt(b.getText()), anno, dataInizio, dataFine));
						}
					}
				}
			}
		}
	}

	private void resetGrid() {
		for (int x = 0; x < 10; x++) {
			for (int y = 0; y < 12; y++) {
				var b = grid[x][y];
				b.setText("");
				b.setOpaque(false);
				for (var al : b.getActionListeners()) {//check if right
					b.removeActionListener(al);
				}
			}
		}
	}

}
