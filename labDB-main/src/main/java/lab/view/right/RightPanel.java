package lab.view.right;

import java.sql.Connection;
import java.sql.SQLException;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JButton;
import javax.swing.JPanel;

import lab.db.Query;
import lab.view.center.AddPostazione;

public class RightPanel extends JPanel {
	
	public RightPanel(Query query) {
		setLayout(new GridBagLayout());
		
		final JButton addBagnino = new JButton("Aggiungi bagnino");
		final JButton addCliente = new JButton("Aggiungi cliente");
		
		addBagnino.addActionListener(l -> new AddBagnino(query));
		addCliente.addActionListener(l -> {
			try {
				new AddCliente(query);
			} catch (SQLException e) {
			}
		});
		
		var c = new GridBagConstraints();
		c.gridy=1;
		add(addCliente, c);
		c.gridy=2;
		add(addBagnino, c);
	}

}
