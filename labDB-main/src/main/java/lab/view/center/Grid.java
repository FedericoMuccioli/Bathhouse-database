package lab.view.center;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.sql.SQLException;
import java.util.Date;
import java.awt.GridBagConstraints;
import javax.swing.JButton;
import javax.swing.JPanel;

import lab.db.Query;

public class Grid extends JPanel {

	public enum PostazioneStatus {NON_PRESENTE, DISPONIBILE, NON_DISPONIBILE};
	private final Query query;
	private final JButton[][] grid;
	private int anno;
	private Date dataInizio;
	private Date dataFine;

	public Grid(Query query) {
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
					PostazioneStatus postazioneStatus;
					try {
						postazioneStatus = query.getPostazioneOmbrelloneStatus(Integer.parseInt(b.getText()), anno, dataInizio, dataFine);
					} catch (NumberFormatException | SQLException e1) {
						break;
					}
					if (postazioneStatus == PostazioneStatus.DISPONIBILE) {
						b.setBackground(Color.GREEN);
						b.addActionListener(l -> new AddOmbrelloneConPrenotazione(this, query,
								Integer.parseInt(b.getText()), anno, dataInizio, dataFine));
					} else {
						if (postazioneStatus == PostazioneStatus.NON_DISPONIBILE) {
							b.setBackground(Color.RED);
						} else if (postazioneStatus == PostazioneStatus.NON_PRESENTE) {
							b.setBackground(Color.BLACK);
						}
						b.addActionListener(l -> {
							try {
								new VisualOmbrelloneConPrenotazione(query, Integer.parseInt(b.getText()), anno, dataInizio, dataFine);
							} catch (NumberFormatException e) {
								throw new IllegalStateException();
							}
						});
					}					
				}
			}
		}
	}

	private void addPostazioniSedute(final int anno) throws SQLException {
		int x = 0;
		int y = 10;
		for (var n : query.getNumeriSedute(anno)) {
			grid[x][y].setText(n.toString());
			x++;
			if (x == 10) {
				x = 0;
				y++;
			}
		}
	}

	private void addDisponibilitàSedute(final int anno, final Date dataInizio, final Date dataFine) throws NumberFormatException, SQLException {
		for (int x = 0; x < 10; x++) {
			for (int y = 9; y < 12; y++) {
				var b = grid[x][y];
				if(!b.getText().isBlank()) {
					removeAllActionListener(b);
					b.setOpaque(true);
					if (query.isSedutaPrenotata(Integer.parseInt(b.getText()), anno, dataInizio, dataFine)) {
						b.setBackground(Color.RED);
						b.addActionListener(l -> {
							try {
								new VisualSedutaConPrenotazione(query,
										Integer.parseInt(b.getText()), anno, dataInizio, dataFine);
							} catch (NumberFormatException | SQLException e) {
								throw new IllegalStateException();
							}
						});
					} else {
						b.setBackground(Color.GREEN);
						b.addActionListener(l -> new AddSedutaConPrenotazione(this, query,
								Integer.parseInt(b.getText()), anno, dataInizio, dataFine));
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
