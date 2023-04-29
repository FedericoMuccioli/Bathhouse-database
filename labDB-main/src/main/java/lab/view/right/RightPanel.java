package lab.view.right;

import java.sql.Connection;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JButton;
import javax.swing.JPanel;

import lab.view.center.AddPostazioneOmbrellone;

public class RightPanel extends JPanel {
	
	public RightPanel(final Connection connection) {
		setLayout(new GridBagLayout());
		
//		final JButton addPostazioneOmbrellone = new JButton("Aggiungi postazione ombrellone");
		final JButton removePostazioneOmbrellone = new JButton("Rimuovi postazione ombrellone");
//		final JButton addPostazioneSedutaRiva = new JButton("Aggiungi postazione seduta riva");
		final JButton addBagnino = new JButton("Aggiungi bagnino");
		final JButton addCliente = new JButton("Aggiungi cliente");
		
//		addPostazioneOmbrellone.addActionListener(l -> new AddPostazioneOmbrellone(connection));
		removePostazioneOmbrellone.addActionListener(l -> new RemovePostazioneOmbrellone(connection));
//		addPostazioneSedutaRiva.addActionListener(l -> new AddPostazioneSedutaRiva(connection));
		addBagnino.addActionListener(l -> new AddBagnino(connection));
		addCliente.addActionListener(l -> new AddCliente(connection));
		
		var c = new GridBagConstraints();
//		add(addPostazioneOmbrellone);
//		c.gridy=1;
		add(removePostazioneOmbrellone, c);
		c.gridy=1;
//		add(addPostazioneSedutaRiva, c);
		c.gridy=2;
		add(addCliente, c);
		c.gridy=3;
		add(addBagnino, c);
	}

}
