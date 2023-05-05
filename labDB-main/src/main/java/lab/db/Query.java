package lab.db;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lab.model.Bagnino;
import lab.model.Cliente;
import lab.model.OmbrelloneConPrenotazione;
import lab.model.PostazioneOmbrellone;
import lab.model.TipoCliente;
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

	public List<Integer> getNumeriOmbrelloni(int anno) throws SQLException {
		var numeriOmbrelloni = new ArrayList<Integer>();
		String query = "SELECT numeroOmbrellone FROM PostazioniOmbrelloni WHERE anno = ?";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(1, anno);
		var resultSet = statement.executeQuery();
		while (resultSet.next()) {
			numeriOmbrelloni.add(resultSet.getInt("numeroOmbrellone"));
		}
		return numeriOmbrelloni;
	}

	public List<Integer> getNumeriSedute(int anno) throws SQLException {
		var numeriSedute = new ArrayList<Integer>();
		String query = "SELECT numeroSeduta FROM PostazioniSeduteRiva WHERE anno = ?";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(1, anno);
		var resultSet = statement.executeQuery();
		while (resultSet.next()) {
			numeriSedute.add(resultSet.getInt("numeroSeduta"));
		}
		return numeriSedute;
	}

	public void insertPostazioneOmbrellone(int numeroOmbrellone, int fila, int colonna, int anno, Date dataInizio) throws SQLException {
		String query = "INSERT INTO PostazioniOmbrelloni (anno, numeroOmbrellone, dataInizio, fila, colonna) SELECT ?, ?, ?, ?, ? WHERE 100 >= (SELECT COUNT(*) FROM PostazioniOmbrelloni WHERE anno = ?)";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(1, anno);
		statement.setInt(2, numeroOmbrellone);
		statement.setDate(3, Utils.dateToSqlDate(dataInizio));
		statement.setInt(4, fila);
		statement.setInt(5, colonna);
		statement.setInt(6, anno);
		statement.executeUpdate();
	}

	public void insertPostazioneSeduta(int numeroSeduta, int anno) throws SQLException {
		String query = "INSERT INTO PostazioniSeduteRiva VALUES (?, ?)";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(1, anno);
		statement.setInt(2, numeroSeduta);
		statement.executeUpdate();
	}

	public boolean insertPrenotazioneOmbrellone(int numeroOmbrellone, int anno, Date dataInizio, Date dataFine, Double prezzo, String codiceFiscale, int bagnino) throws SQLException {
		String query = "INSERT INTO OmbrelloniConPrenotazione VALUES (?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(1, numeroOmbrellone);
		statement.setInt(2, anno);
		statement.setDate(3, Utils.dateToSqlDate(dataInizio));
		statement.setDate(4, Utils.dateToSqlDate(dataFine));
		statement.setDouble(5, prezzo);
		statement.setString(6, codiceFiscale);
		statement.setInt(7, bagnino);
		if (statement.executeUpdate() == 0) {
			return false;
		}
		return true;
	}

	public boolean insertLettini(int numeroOmbrellone, int anno, Date dataInizio, int quantità) throws SQLException {
		return insertSedute(numeroOmbrellone, anno, dataInizio, quantità, 1);
	}

	public boolean insertSedie(int numeroOmbrellone, int anno, Date dataInizio, int quantità) throws SQLException {
		return insertSedute(numeroOmbrellone, anno, dataInizio, quantità, 2);
	}

	public boolean insertSdraio(int numeroOmbrellone, int anno, Date dataInizio, int quantità) throws SQLException {
		return insertSedute(numeroOmbrellone, anno, dataInizio, quantità, 3);
	}

	private boolean insertSedute(int numeroOmbrellone, int anno, Date dataInizio, int quantità, int codiceSeduta) throws SQLException {
		String query = "INSERT INTO Composizioni VALUES (?, ?, ?, ?, ?)";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(1, numeroOmbrellone);
		statement.setInt(2, anno);
		statement.setDate(3, Utils.dateToSqlDate(dataInizio));
		statement.setInt(4, codiceSeduta);
		statement.setInt(5, quantità);
		if (statement.executeUpdate() == 0) {
			return false;
		}		
		return true;
	}



	public boolean insertBagnino(Bagnino bagnino) throws SQLException {
		String query = "INSERT INTO Bagnini (codiceFiscale, nome, cognome, dataDiNascita, indirizzo, telefono) VALUES (?, ?, ?, ?, ?, ?)";
		PreparedStatement statement = connection.prepareStatement(query);
		int i = 1;
		statement.setString(i++, bagnino.getCodiceFiscale());
		statement.setString(i++, bagnino.getNome());
		statement.setString(i++, bagnino.getCognome());
		statement.setDate(i++, Utils.dateToSqlDate(bagnino.getDataNascita()));
		statement.setString(i++, bagnino.getIndirizzo());
		statement.setString(i++, bagnino.getTelefono());
		if (statement.executeUpdate() == 0) {
			return false;
		}
		return true;
	}

	public boolean removePostazioneOmbrellone(int numeroOmbrellone, int anno, Date dataFine) throws SQLException {
		String query = "UPDATE PostazioniOmbrelloni SET dataFine = ? WHERE anno = ? AND numeroOmbrellone = ? "
				+ "AND ((NOT EXISTS (SELECT 1 FROM OmbrelloniConPrenotazione WHERE anno = ? AND numeroOmbrellone = ?)) "
				+ "OR (? >= (SELECT MAX(dataFine) FROM OmbrelloniConPrenotazione WHERE anno = ? AND numeroOmbrellone = ?)))";

		PreparedStatement statement = connection.prepareStatement(query);
		statement.setDate(1, Utils.dateToSqlDate(dataFine));
		statement.setInt(2, anno);
		statement.setInt(3, numeroOmbrellone);
		statement.setInt(4, anno);
		statement.setInt(5, numeroOmbrellone);
		statement.setDate(6, Utils.dateToSqlDate(dataFine));
		statement.setInt(7, anno);
		statement.setInt(8, numeroOmbrellone);
		return statement.executeUpdate() == 0 ? false : true;
	}

	public List<TipoCliente> getTipiClienti() throws SQLException {
		String query = "SELECT * FROM TipiClienti";
		ResultSet rs = connection.createStatement().executeQuery(query);
		var tipiClienti = new ArrayList<TipoCliente>();
		while (rs.next()) {
			tipiClienti.add(new TipoCliente(rs.getInt("codiceTipoCliente"),  rs.getString("nome")));
		}
		return tipiClienti;
	}

	public List<Cliente> getClienti() throws SQLException {
		String query = "SELECT codiceFiscale, C.nome, cognome, telefono, T.nome AS tipoCliente FROM Clienti C LEFT JOIN TipiClienti T ON C.codiceTipoCliente = T.codiceTipoCliente";
		ResultSet rs = connection.createStatement().executeQuery(query);
		var clienti = new ArrayList<Cliente>();
		while (rs.next()) {
			clienti.add(new Cliente(rs.getString("codiceFiscale"),  rs.getString("nome"), rs.getString("cognome"), rs.getString("telefono"), rs.getString("tipoCliente")));
		}
		return clienti;
	}

	public List<Bagnino> getBagnini() throws SQLException {
		String query = "SELECT * FROM Bagnini";
		ResultSet rs = connection.createStatement().executeQuery(query);
		var bagnini = new ArrayList<Bagnino>();
		while (rs.next()) {
			bagnini.add(new Bagnino(rs.getString("codiceFiscale"),  rs.getString("nome"), rs.getString("cognome"), rs.getInt("codiceUnivoco"), rs.getDate("dataDiNascita") ,rs.getString("indirizzo"), rs.getString("telefono")));
		}
		return bagnini;
	}

	public List<OmbrelloneConPrenotazione> getOmbrelloniConPrenotazioni(int anno, int numeroOmbrellone, Date dataInizio, Date dataFine) throws SQLException {
		String query = "SELECT O.*, T.nome AS tipoCliente,"
				+ " Cl.codiceTipoCliente, Cl.codiceFiscale AS codiceFiscaleCliente, Cl.nome AS nomeCliente, Cl.cognome AS cognomeCliente, Cl.telefono AS telefonoCliente,"
				+ " B.codiceFiscale AS codiceFiscaleBagnino,B.codiceUnivoco, B.dataDiNascita, B.indirizzo, B.nome AS nomeBagnino, B.cognome AS cognomeBagnino, B.telefono AS telefonoBagnino"
				+ " FROM OmbrelloniConPrenotazione O JOIN Composizioni C JOIN Clienti Cl JOIN Bagnini B JOIN TipiClienti T"
				+ " ON (O.codiceFiscaleCliente = Cl.codiceFiscale AND O.anno = C.anno AND O.numeroOmbrellone = C.numeroOmbrellone AND O.dataInizio = C.dataInizio AND Cl.codiceTipoCliente = T.codiceTipoCliente)"
				+ " WHERE O.anno = ? AND O.numeroOmbrellone = ? AND NOT ((O.dataInizio < ? AND O.dataFine < ?) OR (O.dataInizio > ? AND O.dataFine > ?))";
		int i = 1;
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(i++, anno);
		statement.setInt(i++, numeroOmbrellone);
		statement.setDate(i++, Utils.dateToSqlDate(dataInizio));
    	statement.setDate(i++, Utils.dateToSqlDate(dataInizio));
    	statement.setDate(i++, Utils.dateToSqlDate(dataFine));
    	statement.setDate(i++, Utils.dateToSqlDate(dataFine));
		ResultSet rs = statement.executeQuery();
		var ombrelloniConPrenotazioni = new ArrayList<OmbrelloneConPrenotazione>();
		
		Date exData = null;
		Date data;
		//fare poi il check
		//cambiare anche il parametro mandato al costruttore tipoCliente
		int nLettini;
		int nSedie;
		int nSdraio;
		
		
		while(rs.next()) {
			
			
//			data = resultSet.getDate("dataInizio");
//			if (data.equals(exData)) {
//				int codTipoSeduta = resultSet.getInt("codiceTipoSeduta");
//				int quantita = resultSet.getInt("quantita");
//				switch (codTipoSeduta) {
//					case 1: nLettini = quantita; break;
//					case 2: nSedie = quantita; break;
//					case 3: nSdraio = quantita; break;
//				}
//			} else {
				var cliente = new Cliente(rs.getString("codiceFiscaleCliente"), rs.getString("nomeCliente"), rs.getString("cognomeCliente"), rs.getString("telefonoCliente"), rs.getString("tipoCliente"));
				var bagnino = new Bagnino(rs.getString("codiceFiscaleBagnino"), rs.getString("nomeBagnino"), rs.getString("cognomeBagnino"), rs.getInt("codiceUnivoco"), rs.getDate("dataDiNascita"), rs.getString("indirizzo"), rs.getString("telefonoBagnino"));
				var ombrelloneConPrenotazione = new OmbrelloneConPrenotazione(numeroOmbrellone, anno, rs.getDate("dataInizio"), rs.getDate("dataFine"), rs.getDouble("prezzo"), 
						0, 0, 0, cliente, bagnino);
				ombrelloniConPrenotazioni.add(ombrelloneConPrenotazione);
//			}
		}
		return ombrelloniConPrenotazioni;
	}




}















