package lab.view.right;

import java.sql.SQLException;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JButton;
import javax.swing.JPanel;

import lab.db.Query;
import lab.model.Dipendente.TipoDipendente;
import lab.view.top.Period;

public class RightPanel extends JPanel {
	
	public RightPanel(Query query, Period period) {
		setLayout(new GridBagLayout());
		
		final JButton addBagnino = new JButton("Aggiungi bagnino");
		final JButton addBarista = new JButton("Aggiungi barista");
		final JButton addCliente = new JButton("Aggiungi cliente");
		final JButton addProdottoBar = new JButton("Aggiungi prodotto bar");
		final JButton visualOrdiniBar = new JButton("Visualizza ordini bar");
		
		addBagnino.addActionListener(l -> new AddDipendente(query, TipoDipendente.Bagnino));
		addBarista.addActionListener(l -> new AddDipendente(query, TipoDipendente.Barista));
		addCliente.addActionListener(l -> {
			try {
				new AddCliente(query);
			} catch (SQLException e) {
			}
		});
		addProdottoBar.addActionListener(l -> {
			try {
				new AddProdottoBar(query);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
		visualOrdiniBar.addActionListener(l -> {
			try {
				var datePeriod = period.getPeriod();
				new VisualOrdiniBar(query, datePeriod.getX(), datePeriod.getY());
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
		
		var c = new GridBagConstraints();
		c.gridy=1;
		add(addCliente, c);
		c.gridy++;
		add(addBarista, c);
		c.gridy++;
		add(addBagnino, c);
		c.gridy++;
		add(addProdottoBar, c);
		c.gridy++;
		add(visualOrdiniBar, c);
	}

}
