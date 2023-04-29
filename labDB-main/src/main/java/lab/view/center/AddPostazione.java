package lab.view.center;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import lab.db.Query;
import lab.utils.Utils;

public class AddPostazione extends JDialog {
	
	public enum Tipologia {OMBRELLONE, SEDUTA}
	
	private static final int OMBRELLONI = 100;
	private static final int LETTINI = 20;
	
	public AddPostazione(Tipologia tip, Query query, Grid grid, int anno, int fila, int colonna, Date inizio) {
		final var panel = new JPanel(new GridBagLayout());
		panel.setPreferredSize(new Dimension(800,300));
		var label = new JLabel(tip.toString());
		var numero = new JComboBox<Integer>();
		final JLabel alert = new JLabel();
		final var button = new JButton("AGGIUNGI");
			
		try {
			List<Integer> numeri = IntStream.rangeClosed(1, tip == Tipologia.OMBRELLONE ? OMBRELLONI : LETTINI).boxed().collect(Collectors.toList());
			numeri.removeAll(tip == Tipologia.OMBRELLONE ? query.getNumeriOmbrelloni(anno) : query.getNumeriSedute(anno));
			numero.setModel(new DefaultComboBoxModel<Integer>(numeri.toArray(new Integer[0])));
		} catch (SQLException e) {
			alert.setText("Errore nella lettura dei numeri disponibili. Riprovare.");
		}
		
		
		button.addActionListener(l -> {
			try {
				if (tip == Tipologia.OMBRELLONE) {
					query.insertPostazioneOmbrellone((int) numero.getSelectedItem(), fila, colonna, anno, inizio);
				} else {
					query.insertPostazioneSeduta((int) numero.getSelectedItem(), anno);
				}
		        alert.setText("Inserimento eseguito");
		        grid.updateGrid();
		        dispose();
	        } catch (final Exception e) {
	        	alert.setText("Inserimento non eseguito");
	        }
		});
		
		panel.add(label);
		panel.add(numero);
		panel.add(button);
		var c = new GridBagConstraints();
		c.gridy=1;
		c.gridwidth = 10;
		panel.add(alert, c);
		add(panel);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

}
