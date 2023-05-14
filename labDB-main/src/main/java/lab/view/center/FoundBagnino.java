package lab.view.center;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import lab.model.Dipendente;
import lab.view.utilities.MyDefaultTableCellRenderer;

public class FoundBagnino extends JDialog {

	private final JTable table;
	private final DefaultTableModel tableModel;
	private final JTextField searchField;
	private final List<Dipendente> bagnini;

	public FoundBagnino(List<Dipendente> bagnino, JButton codiceUnivoco) {
		super();
		var panel = new JPanel(new BorderLayout());
		panel.setPreferredSize(new Dimension(800,300));
		setTitle("Trova bagnino");
		this.bagnini = bagnino;
		
        searchField = new JTextField();
        searchField.addActionListener(e -> updateBagnini());
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
            	updateBagnini();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
            	updateBagnini();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                 updateBagnini();
            }
        });

		tableModel = new DefaultTableModel();
		table = new JTable(tableModel);
		table.getTableHeader().setDefaultRenderer(new MyDefaultTableCellRenderer());
		tableModel.addColumn("codiceUnivoco");
		tableModel.addColumn("nome");
		tableModel.addColumn("cognome");
		tableModel.addColumn("telefono");
		tableModel.addColumn("indirizzo");
		tableModel.addColumn("dataNascita");
		tableModel.addColumn("codiceFiscale");
		
		loadClientList(this.bagnini);

		table.getSelectionModel().addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
				codiceUnivoco.setText(String.valueOf(tableModel.getValueAt(table.getSelectedRow(), 0)));
				dispose();
			}
		});

		panel.add(searchField, BorderLayout.NORTH);
		panel.add(new JScrollPane(table));
		add(panel);
		pack();
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
    private void updateBagnini() {
        String searchText = searchField.getText().toLowerCase();
        List<Dipendente> filteredClients = new ArrayList<>();
        for (Dipendente bagnino : bagnini) {
            if (bagnino.getCodiceFiscale().toLowerCase().contains(searchText) || 
            		bagnino.getNome().toLowerCase().contains(searchText) ||
                    bagnino.getCognome().toLowerCase().contains(searchText) ||
                    bagnino.getTelefono().toLowerCase().contains(searchText) ||
            		bagnino.getIndirizzo().toLowerCase().contains(searchText)){
                filteredClients.add(bagnino);
            }
        }
        loadClientList(filteredClients);
    }
    
	private void loadClientList(List<Dipendente> bagninoList) {
		tableModel.setRowCount(0);

		for (Dipendente bagnino : bagninoList) {
			Object[] rowData = {bagnino.getCodiceUnivoco(), bagnino.getNome(), bagnino.getCognome(), bagnino.getTelefono(), bagnino.getIndirizzo(), bagnino.getDataNascita(), bagnino.getCodiceFiscale()};
			tableModel.addRow(rowData);
		}
	}

}
