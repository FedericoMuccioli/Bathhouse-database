package lab.view.center;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridBagLayout;
import java.sql.SQLException;
import java.sql.Time;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JSpinnerDateEditor;

import lab.db.Query;
import lab.model.Dipendente;
import lab.model.Ordine;
import lab.model.Ordine.Stato;
import lab.model.Prodotto;
import lab.model.TipoCliente;
import lab.model.TipoProdotto;
import lab.utils.Pair;
import lab.utils.Utils;
import lab.view.utilities.MyDefaultTableCellRenderer;

public class AddOrdine extends JDialog {

	private JTextField totale;
	private DecimalFormat df = new DecimalFormat("0.00");
	private Prodotto prodotto;
	DefaultListModel<Pair<Prodotto, Integer>> carrello;

	public AddOrdine(Query query, int anno, int numeroOmbrellone, final Date dataInizio) throws SQLException {
		setPreferredSize(new Dimension(800,500));
		setTitle("Ordinazione ombr:" + String.valueOf(numeroOmbrellone));

		var cardLayout = new CardLayout();
		var panel = new JPanel(cardLayout);


		//PANNELLO2
		var panel2 = new JPanel(new GridBagLayout());
		var prodottoLabel = new JLabel();
		var quantitaLabel = new JLabel("quantità:");
		var quantita = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
		var aggiungi2 = new JButton("aggiungi");
		aggiungi2.addActionListener(e -> {
			addProdotto(new Pair<Prodotto, Integer>(prodotto, (Integer) quantita.getValue()));
			cardLayout.first(panel);
		});
		var c2 = new GridBagConstraints();		
		c2.fill = GridBagConstraints.BOTH;
		c2.gridy = 0;
		c2.gridwidth = 2;
		panel2.add(prodottoLabel, c2);
		c2.gridy++;
		c2.gridwidth = 1;
		panel2.add(quantitaLabel, c2);
		panel2.add(quantita, c2);
		c2.gridy++;
		c2.gridwidth = 2;
		panel2.add(aggiungi2, c2);


		//PANNELLO1
		var panel1 = new JPanel(new GridBagLayout());
		var topPanel = new JPanel(new GridBagLayout());
		var botPanel = new JPanel(new BorderLayout());
		var consegnaLabel = new JLabel("Ora consegna:");
		var consegna = new JSpinnerDateEditor();
		consegna.setEditor(new JSpinnerDateEditor.DateEditor(consegna, "HH:mm"));
		consegna.setValue(Calendar.getInstance().getTime());
		var totaleLabel = new JLabel("Totale:");
		totale = new JTextField(16);
		var baristaLabel = new JLabel("Barista:");
		var barista = new JComboBox<Dipendente>();
		carrello = new DefaultListModel<Pair<Prodotto, Integer>>();
		var lista = new JList<Pair<Prodotto, Integer>>(carrello);
		var listaScrollabile = new JScrollPane(lista);
		final var aggiungi = new JButton("aggiungi");
		final var ordina = new JButton("ORDINA");
		final JLabel alert = new JLabel();
		consegnaLabel.setHorizontalAlignment(SwingConstants.CENTER);
		totaleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		baristaLabel.setHorizontalAlignment(SwingConstants.CENTER);
		barista.setPreferredSize(totale.getPreferredSize());
		totale.setEditable(false);
		totale.setText(df.format(0));
		barista.setModel(new DefaultComboBoxModel<Dipendente>(query.getBaristi().toArray(new Dipendente[0])));

		var tableModel = new DefaultTableModel();
		var table = new JTable(tableModel);
		var d = new Dimension(500, 100);
		table.setPreferredSize(d);
		botPanel.setPreferredSize(d);
		table.getTableHeader().setDefaultRenderer(new MyDefaultTableCellRenderer());
		tableModel.addColumn("id");
		tableModel.addColumn("nome");
		tableModel.addColumn("tipo");
		tableModel.addColumn("descrizione");
		tableModel.addColumn("prezzo");
		MyDefaultTableCellRenderer.resizeAndCenterTable(table);

		aggiungi.addActionListener( e -> {
			try {
				tableModel.setRowCount(0);
				var prodotti = query.getProdotti();
				if (!prodotti.isEmpty()) {
					for (var p : prodotti) {
						Object[] rowData = {p.getId(), p.getNome(), p.getTipo(), p.getDescrizione(), p.getPrezzo()};
						tableModel.addRow(rowData);
					}
					MyDefaultTableCellRenderer.resizeAndCenterTable(table);
				}
			} catch (SQLException e1) {
				alert.setText("Errore, riprovare");
			}
		});

		table.getSelectionModel().addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
				int id = (int) tableModel.getValueAt(table.getSelectedRow(), 0);
				String nome = (String) tableModel.getValueAt(table.getSelectedRow(), 1);
				TipoProdotto tipo = (TipoProdotto) tableModel.getValueAt(table.getSelectedRow(), 2);
				String descrizione = (String) tableModel.getValueAt(table.getSelectedRow(), 3);
				Double prezzo = (Double) tableModel.getValueAt(table.getSelectedRow(), 4);
				prodotto = new Prodotto(id, nome, descrizione, tipo, prezzo);
				prodottoLabel.setText(prodotto.toString());
				cardLayout.last(panel);
			} 
			
		});
		
		ordina.addActionListener( al -> {
			if (carrello.getSize() == 0) {
				return;
			}
			
			try {
				var ordine = new Ordine(new Date(), consegna.getDate(), Double.valueOf(totale.getText().replace(",", ".")),
						Stato.IN_ELABORAZIONE, (Dipendente) barista.getSelectedItem(), numeroOmbrellone, anno, dataInizio);
				ordine.setIdOrdine(query.insertOrdine(ordine));
				for (int i = 0; i < carrello.size(); i++) {
					query.insertComposizioneOrdine(ordine, carrello.get(i).getX(), carrello.get(i).getY());
		        }
				
			} catch (Exception e1) {
				alert.setText("Errore, riprovare");
				e1.printStackTrace(); //DA CANCELLARE
				return;
			}
			alert.setText("Inserimento eseguito");
			Utils.closeJDialogAfterOneSecond(this);
		});


		var c = new GridBagConstraints();		
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.gridy = 0;
		topPanel.add(consegnaLabel, c);
		topPanel.add(consegna, c);
		c.gridy++;
		topPanel.add(baristaLabel, c);
		topPanel.add(barista, c);
		c.gridy++;
		topPanel.add(totaleLabel, c);
		topPanel.add(totale, c);
		c.gridy++;
		c.gridwidth = 2;
		topPanel.add(listaScrollabile, c);
		c.gridy++;
		topPanel.add(aggiungi, c);
		c.gridy++;
		topPanel.add(ordina, c);
		c.gridy++;
		topPanel.add(alert, c);
		c = new GridBagConstraints();
		c.gridy = 0;
		panel1.add(topPanel, c);
		c.gridy++;
		botPanel.add(new JScrollPane(table));
		panel1.add(botPanel, c);

		panel.add(panel1, "1");
		panel.add(panel2, "2");
		add(panel);

		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private void addProdotto(Pair<Prodotto, Integer> elemento) {
		int index = carrello.indexOf(elemento);
		if (index == -1) {
			carrello.addElement(elemento);
		} else {
			int qtElemento = carrello.get(index).getY() + elemento.getY();
			carrello.set(index, new Pair<Prodotto, Integer>(elemento.getX(), qtElemento));
		}
		
		
		double sum = 0;
        for (int i = 0; i < carrello.size(); i++) {
        	elemento = carrello.get(i);
        	sum += elemento.getX().getPrezzo() * elemento.getY();
        }
        totale.setText(df.format(sum));
	}

}


