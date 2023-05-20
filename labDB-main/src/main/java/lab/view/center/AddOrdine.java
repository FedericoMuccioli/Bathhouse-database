package lab.view.center;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.util.Date;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import lab.db.Query;
import lab.model.Dipendente;
import lab.model.TipoCliente;
import lab.model.TipoProdotto;
import lab.view.utilities.MyDefaultTableCellRenderer;

public class AddOrdine extends JDialog {
	
	private JTextField totale;
	private DecimalFormat df = new DecimalFormat("#.00");

	public AddOrdine(Query query, int anno, int numeroOmbrellone, final Date dataInizio) throws SQLException {
		var panel = new JPanel(new GridBagLayout());
		panel.setPreferredSize(new Dimension(800,300));
		setTitle("Ordinazione ombr:" + String.valueOf(numeroOmbrellone));
		
		var topPanel = new JPanel(new GridBagLayout());
		var botPanel = new JPanel(new GridBagLayout());
		var consegnaLabel = new JLabel("Ora consegna:");
		var consegna = new JTextField("00:00", 16);
		var totaleLabel = new JLabel("Totale:");
		totale = new JTextField("000.00", 16);
		var baristaLabel = new JLabel("Barista:");
		var barista = new JComboBox<Dipendente>();
		
		final var aggiungi = new JButton("aggiungi");
		final var button = new JButton("ORDINA");
		final JLabel alert = new JLabel();
		consegnaLabel.setHorizontalAlignment(SwingConstants.CENTER);
		totaleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		baristaLabel.setHorizontalAlignment(SwingConstants.CENTER);
		barista.setPreferredSize(totale.getPreferredSize());
		totale.setEditable(false);
		updateTotale(0);
		barista.setModel(new DefaultComboBoxModel<Dipendente>(query.getBaristi().toArray(new Dipendente[0])));
		
		var tableModel = new DefaultTableModel();
		var table = new JTable(tableModel);
		table.getTableHeader().setDefaultRenderer(new MyDefaultTableCellRenderer());
		tableModel.addColumn("nome");
		tableModel.addColumn("tipo");
		tableModel.addColumn("descrizione");
		tableModel.addColumn("prezzo");
		MyDefaultTableCellRenderer.resizeAndCenterTable(table);
		
		aggiungi.addActionListener( e -> {
			try {
				var prodotti = query.getProdotti();
				
				if (!prodotti.isEmpty()) {
					for (var p : prodotti) {
						Object[] rowData = {p.getNome(), p.getTipo(), p.getDescrizione(), p.getPrezzo()};
						tableModel.addRow(rowData);
					}
					MyDefaultTableCellRenderer.resizeAndCenterTable(table);
				}
			} catch (SQLException e1) {
				alert.setText("Errore, riprovare");
			}
		});
		
		
//		
//		
//		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
//		    @Override
//		    public void valueChanged(ListSelectionEvent e) {
//		        if (!e.getValueIsAdjusting()) {
//		            int selectedRow = table.getSelectedRow();
//		            if (selectedRow != -1) {
//		            	
//		                Date dataInizio = (Date) tableModel.getValueAt(selectedRow, 0);
//		                
//		            }
//		        }
//		    }
//		});
//		panel.add(new JScrollPane(table));
//		panel.add(alert, BorderLayout.SOUTH);
//		add(panel);
		
		
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
		topPanel.add(aggiungi, c);
		c.gridy++;
		topPanel.add(button, c);
		c.gridy++;
		topPanel.add(alert, c);
		
		c = new GridBagConstraints();
		c.gridy = 0;
		panel.add(topPanel, c);
		c.gridy++;
		botPanel.add(new JScrollPane(table));
		panel.add(botPanel, c);
		
		add(panel);
		pack();
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private void updateTotale(double prezzo) {
		if (prezzo == 0) {
			totale.setText("0,00");	
		} else {
	        totale.setText(df.format(prezzo));
		}
	}

}


