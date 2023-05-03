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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import lab.model.Cliente;

public class FoundCliente extends JDialog {

	private final JTable table;
	private final DefaultTableModel tableModel;
	private final JTextField searchField;
	private final List<Cliente> clienti;

	public FoundCliente(List<Cliente> clienti, JButton codiceFiscale) {
		super();
		var panel = new JPanel(new BorderLayout());
		panel.setPreferredSize(new Dimension(800,300));
		this.clienti = clienti;
		
        searchField = new JTextField();
        searchField.addActionListener(e -> updateClients());
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
            	updateClients();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
            	updateClients();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                 updateClients();
            }
        });

		tableModel = new DefaultTableModel();
		table = new JTable(tableModel);
		tableModel.addColumn("codiceFiscale");
		tableModel.addColumn("nome");
		tableModel.addColumn("cognome");
		tableModel.addColumn("telefono");
		tableModel.addColumn("tipoCliente");
		loadClientList(this.clienti);

		table.getSelectionModel().addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
				codiceFiscale.setText((String) tableModel.getValueAt(table.getSelectedRow(), 0));
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
	
    private void updateClients() {
        String searchText = searchField.getText().toLowerCase();
        List<Cliente> filteredClients = new ArrayList<>();
        for (Cliente cliente : clienti) {
            if (cliente.getCodiceFiscale().toLowerCase().contains(searchText) || 
            		cliente.getNome().toLowerCase().contains(searchText) ||
                    cliente.getCognome().toLowerCase().contains(searchText) ||
                    cliente.getTelefono().toLowerCase().contains(searchText) ||
            		cliente.getTipoCliente().toLowerCase().contains(searchText)){
                filteredClients.add(cliente);
            }
        }
        loadClientList(filteredClients);
    }
    
	private void loadClientList(List<Cliente> clientiList) {
		tableModel.setRowCount(0);

		for (Cliente cliente : clientiList) {
			Object[] rowData = {cliente.getCodiceFiscale(), cliente.getNome(), cliente.getCognome(), cliente.getTelefono(), cliente.getTipoCliente()};
			tableModel.addRow(rowData);
		}
	}

}
