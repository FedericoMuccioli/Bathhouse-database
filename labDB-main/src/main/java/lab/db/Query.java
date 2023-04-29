package lab.db;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lab.model.PostazioneOmbrellone;
import lab.utils.Utils;

public class Query {

	private final Connection connection;

	public Query(Connection connection) {
		this.connection = connection;
	}
	
	public List<Integer> getStagioni() throws SQLException {
		var stagioni = new ArrayList<Integer>();
		ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM Spiagge ORDER BY anno DESC");
		while (rs.next()) {
			stagioni.add(rs.getInt("anno"));
        }
		return stagioni;
	}
	
	public void addStagione(int stagione) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("INSERT INTO Spiagge VALUES (?)");
		statement.setInt(1, stagione);
		statement.executeUpdate();
	}
	
	public List<PostazioneOmbrellone> getOmbrelloniPiantati(int anno) throws SQLException {
		String query = "SELECT * FROM PostazioniOmbrelloni WHERE anno = ?";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(1, anno);
		return PostazioneOmbrellone.readPostazioniOmbrelloniFromResultSet(statement.executeQuery());
	}
	
	public boolean isOmbrellonePiantato(int numeroOmbrellone, int anno, Date dataInizio, Date dataFine) throws SQLException {
		String query = "SELECT * FROM PostazioniOmbrelloni WHERE anno = ? AND numeroOmbrellone = ? AND dataInizio <= ? AND (dataFine >= ? OR dataFine is null)";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(1, anno);
		statement.setInt(2, numeroOmbrellone);
		statement.setDate(3, Utils.dateToSqlDate(dataInizio));
		statement.setDate(4, Utils.dateToSqlDate(dataFine));
		return statement.executeQuery().next();
	}
	
	public boolean isOmbrellonePrenotato(int numeroOmbrellone, int anno, Date dataInizio, Date dataFine) throws SQLException {
		String query = "SELECT * FROM OmbrelloniConPrenotazione WHERE anno = ? AND numeroOmbrellone = ? AND NOT ((dataInizio < ? AND dataFine < ?) OR (dataInizio > ? AND dataFine > ?))";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(1, anno);
		statement.setInt(2, numeroOmbrellone);
		statement.setDate(3, Utils.dateToSqlDate(dataInizio));
		statement.setDate(4, Utils.dateToSqlDate(dataInizio));
		statement.setDate(5, Utils.dateToSqlDate(dataFine));
		statement.setDate(6, Utils.dateToSqlDate(dataFine));
		return statement.executeQuery().next();
	}
	

}
