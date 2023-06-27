package lab.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.sql.Connection;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import lab.db.ConnectionProvider;

public class AccessMenu extends JPanel {
	
	public AccessMenu(final MainMenu mainMenu) {
		setLayout(new GridBagLayout());
		
		final JTextField root = new JTextField("root");
		final JTextField password = new JTextField("Federico320302.");
		final JTextField nomeDatabase = new JTextField("StabilimentoBalneare2");
		final JLabel alert = new JLabel();
		
		final var button = new JButton("CONNETTI");
		button.addActionListener(l -> {
			final Connection connection;
			try {
				connection = new ConnectionProvider(root.getText(), 
						password.getText(), nomeDatabase.getText()).getMySQLConnection();
			} catch (Exception e) {
				alert.setText("Could not establish a connection with db, retry.");
				return;
			}
			mainMenu.accessed(connection);
		});
		
		add(root);
		add(password);
		add(nomeDatabase);
		add(button);
		var c = new GridBagConstraints();
		c.gridy=1;
		c.gridwidth = 10;
		add(alert, c);
	}

}
