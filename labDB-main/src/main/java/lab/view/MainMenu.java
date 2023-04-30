package lab.view;

import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.SQLException;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import lab.db.Query;
import lab.view.center.Grid;
import lab.view.right.RightPanel;
import lab.view.top.Period;

public class MainMenu extends JFrame {
	
	public MainMenu() {
		setSize(Toolkit.getDefaultToolkit().getScreenSize());
		setContentPane(new AccessMenu(this));
        setLocationRelativeTo(null);        
        setVisible(true);
	}
	
	public void accessed(final Connection connection) {
		final Query query = new Query(connection);
		final JPanel panel = new JPanel(new BorderLayout());
		final Grid centerPanel = new Grid(connection, query);
		final JPanel topPanel = new Period(query, centerPanel);
		final JPanel rightPanel = new RightPanel(connection, query);
		panel.add(topPanel, BorderLayout.NORTH);
		panel.add(centerPanel, BorderLayout.CENTER);
		panel.add(rightPanel, BorderLayout.EAST);
		addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
            	try {
					connection.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
            	System.exit(0);
            }
        });
		setContentPane(panel);
		revalidate();
	}

}
