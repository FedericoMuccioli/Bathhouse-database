package lab.view.center;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.awt.GridBagConstraints;
import javax.swing.JButton;
import javax.swing.JPanel;

import lab.db.Query;
import lab.model.PostazioneOmbrellone;
import lab.utils.Utils;

public class Grid extends JPanel {

	private final Connection connection;
	private final Query query;
	private final JButton[][] grid;
	private int anno;
	private Date dataInizio;
	private Date dataFine;

	public Grid(final Connection connection, Query query) {
		this.connection = connection;
		this.query = query;
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
		this.anno = anno;
		this.dataInizio = dataInizio;
		this.dataFine = dataFine;
		updateGrid();
	}
	
	public void updateGrid() {
		try {
			addPostazioneMancante();
			addPostazioniOmbrelloni(anno, dataInizio, dataFine);
			addDisponibilitàOmbrelloni(anno, dataInizio, dataFine);
			addPostazioniSedute(anno);
			addDisponibilitàSedute(anno, dataInizio, dataFine);
		} catch (Exception e) {
			return;
		}
	}

	private void addPostazioniOmbrelloni(final int anno, final Date dataInizio, final Date dataFine) throws SQLException {
		for (var o : query.getOmbrelloniPiantati(anno)) {
			grid[o.getColonna()-1][o.getFila()-1].setText(String.valueOf(o.getNumeroOmbrellone()));
		}
	}
	
	private void addDisponibilitàOmbrelloni(final int anno, final Date dataInizio, final Date dataFine) {
		for (int x = 0; x < 10; x++) {
			for (int y = 0; y < 10; y++) {
				var b = grid[x][y];
				if(!b.getText().isBlank()) {
					b.setOpaque(true);
					removeAllActionListener(b);
					boolean isOmbrellonePiantato;
					boolean isOmbrellonePrenotato;
					try {
						isOmbrellonePiantato = query.isOmbrellonePiantato(Integer.parseInt(b.getText()), anno, dataInizio, dataFine);
						isOmbrellonePrenotato = query.isOmbrellonePrenotato(Integer.parseInt(b.getText()), anno, dataInizio, dataFine);
					} catch (NumberFormatException | SQLException e1) {
						e1.printStackTrace();
						break;
					}
					if (isOmbrellonePiantato && !isOmbrellonePrenotato) {
						b.setBackground(Color.GREEN);
						b.addActionListener(l -> new AddOmbrelloneConPrenotazione(this, connection,
								Integer.parseInt(b.getText()), anno, dataInizio, dataFine));
					} else {
						b.setBackground(Color.RED);
						b.addActionListener(l -> {
							try {
								new VisualOmbrelloneConPrenotazione(connection,
										Integer.parseInt(b.getText()), anno, dataInizio, dataFine);
							} catch (NumberFormatException | SQLException e) {
								throw new IllegalStateException();
							}
						});
					}
				}
			}
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
	
	private void addDisponibilitàSedute(final int anno, final Date dataInizio, final Date dataFine) throws NumberFormatException, SQLException {
		for (int x = 0; x < 10; x++) {
			for (int y = 9; y < 12; y++) {
				var b = grid[x][y];
				if(!b.getText().isBlank()) {
					removeAllActionListener(b);
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
							b.addActionListener(l -> new AddSedutaConPrenotazione(this, connection,
									Integer.parseInt(b.getText()), anno, dataInizio, dataFine));
						}
					}
				}
			}
		}
	}


	
	private void addPostazioneMancante() {
		for (int x = 0; x < 10; x++) {
			for (int y = 0; y < 12; y++) {
				var b = grid[x][y];
				b.setText("");
				b.setOpaque(false);
				removeAllActionListener(b);
				final int x_ = x + 1;
				final int y_ = y + 1;
				if (y<10) {
					b.addActionListener(l -> new AddPostazione(AddPostazione.Tipologia.OMBRELLONE, query, this, anno, y_, x_, dataInizio));
				} else {
					b.addActionListener(l -> new AddPostazione(AddPostazione.Tipologia.SEDUTA, query, this, anno, y_, x_, dataInizio));
				}
			}
		}
	}
	
	private void removeAllActionListener(JButton b) {
		for (var al : b.getActionListeners()) {
			b.removeActionListener(al);
		}
	}

}
