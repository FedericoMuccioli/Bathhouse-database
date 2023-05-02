package lab.view.center;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;

import lab.model.Cliente;

public class FoundCliente extends JDialog {
	
	public FoundCliente(List<Cliente> clienti, JButton codiceFiscale) {
		super();
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	
	
}
