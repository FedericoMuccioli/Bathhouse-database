package lab.view.right;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import lab.db.Query;
import lab.model.Ordine;
import lab.model.Prodotto;
import lab.model.Ordine.Stato;
import lab.utils.Pair;
import lab.view.utilities.MyDefaultTableCellRenderer;

public class VisualOrdiniBar extends JDialog {

	private final JTable table;
	private final DefaultTableModel tableModel;
	private final JTextField searchField;
	private List<Ordine> ordini;
	private List<Ordine> filteredOrdini;
	private Ordine ordine;
	private final DefaultTableModel tableModel2;
	

	public VisualOrdiniBar(final Query query, Date inizio, Date fine) throws SQLException {
		setTitle("Ordini Bar");
		setPreferredSize(new Dimension(800,300));
		var cardLayout = new CardLayout();
		var panel = new JPanel(cardLayout);
		var panel1 = new JPanel(new BorderLayout());
		var panel2 = new JPanel(new BorderLayout());
		var ordineLabel = new JLabel();
		var completatoButton = new JButton("Click per ordine COMPLETATO");
		

		tableModel = new DefaultTableModel();
		table = new JTable(tableModel);
		table.getTableHeader().setDefaultRenderer(new MyDefaultTableCellRenderer());
		tableModel.addColumn("idOrdine");
		tableModel.addColumn("dataOrdine");
		tableModel.addColumn("oraConsegna");
		tableModel.addColumn("ombrellone");
		tableModel.addColumn("prezzo");
		tableModel.addColumn("barista");
		tableModel.addColumn("stato");
		ordini = query.getOrdini(inizio, fine);
		filteredOrdini = ordini;
		loadOrdiniList(ordini);
		MyDefaultTableCellRenderer.resizeAndCenterTable(table);
		
		tableModel2 = new DefaultTableModel();
		var table2 = new JTable(tableModel2);
		table2.getTableHeader().setDefaultRenderer(new MyDefaultTableCellRenderer());
		tableModel2.addColumn("id");
		tableModel2.addColumn("nome");
		tableModel2.addColumn("descrizione");
		tableModel2.addColumn("tipo");
		tableModel2.addColumn("prezzo");
		tableModel2.addColumn("quantità");

		searchField = new JTextField();
		searchField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				updateOrdini();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				updateOrdini();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				updateOrdini();
			}
		});
		
		table.getSelectionModel().addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
				ordine = filteredOrdini.get(table.getSelectedRow());
				ordineLabel.setText(ordine.toString());
				try {
					loadProdottiList(query.getProdotti(ordine));
				} catch (SQLException e1) {
					e1.printStackTrace(); //
					return;
				}
				if (ordine.getStato() == Stato.IN_ELABORAZIONE) {
					panel2.add(completatoButton, BorderLayout.SOUTH);
				} else {
					panel2.remove(completatoButton);
				}
				cardLayout.last(panel);
			}
		});
		
		completatoButton.addActionListener(l -> {
			try {
				query.setOrdineCompletato(ordine);
				panel2.remove(completatoButton);
				panel2.revalidate();
		        panel2.repaint();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});
		
		
		
		panel1.add(searchField, BorderLayout.NORTH);
		panel1.add(new JScrollPane(table));
		panel2.add(ordineLabel, BorderLayout.NORTH);
		panel2.add(new JScrollPane(table2));
		panel.add(panel1, "1");
		panel.add(panel2, "2");
		add(panel);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void loadOrdiniList(List<Ordine> ordini) {
		tableModel.setRowCount(0);
		for (var ordine : ordini) {
			Object[] rowData = {ordine.getIdOrdine(), ordine.getDataOrdine(), ordine.getOraConsegna(), ordine.getNumeroOmbrellone(), ordine.getPrezzo(), ordine.getBarista(), ordine.getStato()};
			tableModel.addRow(rowData);
		}
	}

	private void updateOrdini() {
		filteredOrdini = new ArrayList<Ordine>();
		String searchText = searchField.getText().toLowerCase();
		for (var ordine : ordini) {
			if (String.valueOf(ordine.getIdOrdine()).toLowerCase().contains(searchText) ||
					String.valueOf(ordine.getNumeroOmbrellone()).toLowerCase().contains(searchText) ||
					ordine.getDataOrdine().toString().toLowerCase().contains(searchText) ||
					ordine.getBarista().getNome().toLowerCase().contains(searchText) ||
					ordine.getBarista().getCognome().toLowerCase().contains(searchText) ||
					ordine.getStato().toString().toLowerCase().contains(searchText)) {
				filteredOrdini.add(ordine);
			} 
		}
		loadOrdiniList(filteredOrdini);
	}

	private void loadProdottiList(List<Pair<Prodotto,Integer>> prodotti) {
		tableModel2.setRowCount(0);
		for (var prodotto : prodotti) {
			Object[] rowData = {prodotto.getX().getId(), prodotto.getX().getNome(), prodotto.getX().getDescrizione(), prodotto.getX().getTipo(), prodotto.getX().getPrezzo(), prodotto.getY()};
			tableModel2.addRow(rowData);
		}
	}
	
}
