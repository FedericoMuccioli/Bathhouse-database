package lab.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import lab.model.Dipendente;
import lab.model.Cliente;
import lab.model.PrenotazioneOmbrellone;
import lab.model.PrenotazioneSeduta;
import lab.model.PostazioneOmbrellone;
import lab.model.TipoCliente;
import lab.model.TipoSeduta;
import lab.utils.Utils;

public class Query {

	private final Connection connection;

	public Query(Connection connection) {
		this.connection = connection;
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
	
	public boolean isSedutaPrenotata(int numeroSeduta, int anno, Date dataInizio, Date dataFine) throws SQLException {
		String query = "SELECT * FROM SeduteConPrenotazioni WHERE anno = ? AND numeroSeduta = ? AND NOT ((dataInizio < ? AND dataFine < ?) OR (dataInizio > ? AND dataFine > ?))";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(1, anno);
		statement.setInt(2, numeroSeduta);
		statement.setDate(3, Utils.dateToSqlDate(dataInizio));
		statement.setDate(4, Utils.dateToSqlDate(dataInizio));
		statement.setDate(5, Utils.dateToSqlDate(dataFine));
		statement.setDate(6, Utils.dateToSqlDate(dataFine));
		return statement.executeQuery().next();
	}
	
	public List<PostazioneOmbrellone> getOmbrelloniPiantati(int anno) throws SQLException {
		String query = "SELECT * FROM PostazioniOmbrelloni WHERE anno = ?";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(1, anno);
		return PostazioneOmbrellone.readPostazioniOmbrelloniFromResultSet(statement.executeQuery());
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
	
	public List<TipoSeduta> getTipiSedute() throws SQLException {
		var tipiSedute = new ArrayList<TipoSeduta>();
		String query = "SELECT * FROM TipiSedute";
		ResultSet rs = connection.createStatement().executeQuery(query);
		while (rs.next()) {
			tipiSedute.add(new TipoSeduta(rs.getInt("codiceTipoSeduta"), rs.getString("nome"), rs.getString("descrizione")));
		}
		return tipiSedute;
	}

	public void addStagione(int stagione) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("INSERT INTO Spiagge VALUES (?)");
		statement.setInt(1, stagione);
		statement.executeUpdate();
	}
	
	public boolean insertCliente(Cliente cliente) throws SQLException {
		String query = "INSERT INTO Clienti VALUES (?, ?, ?, ?, ?)";
		PreparedStatement statement = connection.prepareStatement(query);
		int i = 1;
		statement.setString(i++, cliente.getCodiceFiscale());
		statement.setString(i++, cliente.getNome());
		statement.setString(i++, cliente.getCognome());
		statement.setString(i++, cliente.getTelefono());
		statement.setInt(i++, cliente.getTipoCliente().getCodiceUnivoco());
		if (statement.executeUpdate() == 0) {
			return false;
		}
		return true;
	}

	public boolean insertBagnino(Dipendente bagnino) throws SQLException {
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
	
	public boolean insertBarista(Dipendente barista) throws SQLException {
		String query = "INSERT INTO Baristi (codiceFiscale, nome, cognome, dataDiNascita, indirizzo, telefono) VALUES (?, ?, ?, ?, ?, ?)";
		PreparedStatement statement = connection.prepareStatement(query);
		int i = 1;
		statement.setString(i++, barista.getCodiceFiscale());
		statement.setString(i++, barista.getNome());
		statement.setString(i++, barista.getCognome());
		statement.setDate(i++, Utils.dateToSqlDate(barista.getDataNascita()));
		statement.setString(i++, barista.getIndirizzo());
		statement.setString(i++, barista.getTelefono());
		if (statement.executeUpdate() == 0) {
			return false;
		}
		return true;
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
	
	public boolean insertPrenotazioneSeduta(int numeroSeduta, int anno, Date dataInizio, Date dataFine, Double prezzo, int codTipoSeduta, String codiceFiscale, int bagnino) throws SQLException {
		String query = "INSERT INTO SeduteConPrenotazioni VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement statement = connection.prepareStatement(query);
		int i = 1;
		statement.setInt(i++, anno);
		statement.setInt(i++, numeroSeduta);
		statement.setDate(i++, Utils.dateToSqlDate(dataInizio));
		statement.setDate(i++, Utils.dateToSqlDate(dataFine));
		statement.setDouble(i++, prezzo);
		statement.setInt(i++, codTipoSeduta);
		statement.setString(i++, codiceFiscale);
		statement.setInt(i++, bagnino);
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

	public List<Integer> getStagioni() throws SQLException {
		var stagioni = new ArrayList<Integer>();
		ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM Spiagge ORDER BY anno DESC");
		while (rs.next()) {
			stagioni.add(rs.getInt("anno"));
		}
		return stagioni;
	}
	
	public List<Cliente> getClienti() throws SQLException {
		String query = "SELECT C.*, T.nome AS tipoCliente FROM Clienti C LEFT JOIN TipiClienti T ON C.codiceTipoCliente = T.codiceTipoCliente";
		ResultSet rs = connection.createStatement().executeQuery(query);
		var clienti = new ArrayList<Cliente>();
		while (rs.next()) {
			var tipoCliente = new TipoCliente(rs.getInt("codiceTipoCliente"), rs.getString("tipoCliente"));
			clienti.add(new Cliente(rs.getString("codiceFiscale"),  rs.getString("nome"), rs.getString("cognome"), rs.getString("telefono"), tipoCliente));
		}
		return clienti;
	}

	public List<Dipendente> getBagnini() throws SQLException {
		String query = "SELECT * FROM Bagnini";
		ResultSet rs = connection.createStatement().executeQuery(query);
		var bagnini = new ArrayList<Dipendente>();
		while (rs.next()) {
			bagnini.add(new Dipendente(rs.getString("codiceFiscale"),  rs.getString("nome"), rs.getString("cognome"), rs.getInt("codiceUnivoco"), rs.getDate("dataDiNascita") ,rs.getString("indirizzo"), rs.getString("telefono")));
		}
		return bagnini;
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


	public List<PrenotazioneOmbrellone> getPrenotazioniOmbrellone(int anno, int numeroOmbrellone, Date dataInizio, Date dataFine) throws SQLException {
		String query = "SELECT O.*, T.nome AS tipoCliente,"
				+ " Cl.codiceTipoCliente, Cl.codiceFiscale AS codiceFiscaleCliente, Cl.nome AS nomeCliente, Cl.cognome AS cognomeCliente, Cl.telefono AS telefonoCliente,"
				+ " B.codiceFiscale AS codiceFiscaleBagnino,B.codiceUnivoco, B.dataDiNascita, B.indirizzo, B.nome AS nomeBagnino, B.cognome AS cognomeBagnino, B.telefono AS telefonoBagnino,"
				+ " GROUP_CONCAT(Ts.nome SEPARATOR ' ') AS sedute, GROUP_CONCAT(C.quantita SEPARATOR ' ') AS quantitaSedute"
				+ " FROM OmbrelloniConPrenotazione O JOIN Clienti Cl ON O.codiceFiscaleCliente = Cl.codiceFiscale"
				+ " JOIN Bagnini B ON O.codiceUnivocoBagnino = B.codiceUnivoco"
				+ " JOIN TipiClienti T ON Cl.codiceTipoCliente = T.codiceTipoCliente"
				+ " JOIN Composizioni C ON O.numeroOmbrellone = C.numeroOmbrellone AND O.anno = C.anno AND O.dataInizio = C.dataInizio"
				+ " JOIN TipiSedute Ts ON C.codiceTipoSeduta = Ts.codiceTipoSeduta"
				+ " WHERE O.anno = ? AND O.numeroOmbrellone = ? AND NOT ((O.dataInizio < ? AND O.dataFine < ?) OR (O.dataInizio > ? AND O.dataFine > ?))"
				+ " GROUP BY O.numeroOmbrellone, O.anno, O.dataInizio, O.dataFine";
		int i = 1;
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(i++, anno);
		statement.setInt(i++, numeroOmbrellone);
		statement.setDate(i++, Utils.dateToSqlDate(dataInizio));
		statement.setDate(i++, Utils.dateToSqlDate(dataInizio));
		statement.setDate(i++, Utils.dateToSqlDate(dataFine));
		statement.setDate(i++, Utils.dateToSqlDate(dataFine));
		ResultSet rs = statement.executeQuery();
		var prenotazioniOmbrellone = new ArrayList<PrenotazioneOmbrellone>();

		while(rs.next()) {
			int lettini = 0;
			int sedie = 0;
			int sdraio = 0;
			var sedute = Arrays.asList(rs.getString("sedute").split(" "));
			var quantita = Arrays.asList(rs.getString("quantitaSedute").split(" ")).stream().map(Integer::parseInt).collect(Collectors.toList());
			for (var s : sedute) {
				switch (s) {
				case "lettino" : lettini = quantita.get(sedute.indexOf(s)); break;
				case "sedia" : sedie = quantita.get(sedute.indexOf(s)); break;
				case "sdraio" : sdraio = quantita.get(sedute.indexOf(s)); break;
				}
			}
			var tipoCliente = new TipoCliente(rs.getInt("codiceTipoCliente"), rs.getString("tipoCliente"));
			var cliente = new Cliente(rs.getString("codiceFiscaleCliente"), rs.getString("nomeCliente"), rs.getString("cognomeCliente"), rs.getString("telefonoCliente"), tipoCliente);
			var bagnino = new Dipendente(rs.getString("codiceFiscaleBagnino"), rs.getString("nomeBagnino"), rs.getString("cognomeBagnino"), rs.getInt("codiceUnivoco"), rs.getDate("dataDiNascita"), rs.getString("indirizzo"), rs.getString("telefonoBagnino"));
			var ombrelloneConPrenotazione = new PrenotazioneOmbrellone(numeroOmbrellone, anno, rs.getDate("dataInizio"), rs.getDate("dataFine"), rs.getDouble("prezzo"), 
					lettini, sedie, sdraio, cliente, bagnino);
			prenotazioniOmbrellone.add(ombrelloneConPrenotazione);
		}
		return prenotazioniOmbrellone;
	}

	public List<PrenotazioneSeduta> getPrenotazioniSeduta(int anno, int numeroSeduta, Date dataInizio, Date dataFine) throws SQLException {
		String query = "SELECT O.*, T.nome AS tipoCliente, Ts.codiceTipoSeduta, Ts.nome AS nomeSeduta, Ts.descrizione AS descrizioneSeduta,"
				+ " Cl.codiceTipoCliente, Cl.codiceFiscale AS codiceFiscaleCliente, Cl.nome AS nomeCliente, Cl.cognome AS cognomeCliente, Cl.telefono AS telefonoCliente,"
				+ " B.codiceFiscale AS codiceFiscaleBagnino,B.codiceUnivoco, B.dataDiNascita, B.indirizzo, B.nome AS nomeBagnino, B.cognome AS cognomeBagnino, B.telefono AS telefonoBagnino"
				+ " FROM SeduteConPrenotazioni O JOIN Clienti Cl ON O.codiceFiscaleCliente = Cl.codiceFiscale"
				+ " JOIN Bagnini B ON O.codiceUnivocoBagnino = B.codiceUnivoco"
				+ " JOIN TipiClienti T ON Cl.codiceTipoCliente = T.codiceTipoCliente"
				+ " JOIN TipiSedute Ts ON O.codiceTipoSeduta = Ts.codiceTipoSeduta"
				+ " WHERE O.anno = ? AND O.numeroSeduta = ? AND NOT ((O.dataInizio < ? AND O.dataFine < ?) OR (O.dataInizio > ? AND O.dataFine > ?))";
		int i = 1;
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(i++, anno);
		statement.setInt(i++, numeroSeduta);
		statement.setDate(i++, Utils.dateToSqlDate(dataInizio));
		statement.setDate(i++, Utils.dateToSqlDate(dataInizio));
		statement.setDate(i++, Utils.dateToSqlDate(dataFine));
		statement.setDate(i++, Utils.dateToSqlDate(dataFine));
		ResultSet rs = statement.executeQuery();
		var prenotazioniSeduta = new ArrayList<PrenotazioneSeduta>();

		while(rs.next()) {
			var tipoSeduta = new TipoSeduta(rs.getInt("codiceTipoSeduta"), rs.getString("nomeSeduta"), rs.getString("descrizioneSeduta"));
			var tipoCliente = new TipoCliente(rs.getInt("codiceTipoCliente"), rs.getString("tipoCliente"));
			var cliente = new Cliente(rs.getString("codiceFiscaleCliente"), rs.getString("nomeCliente"), rs.getString("cognomeCliente"), rs.getString("telefonoCliente"), tipoCliente);
			var bagnino = new Dipendente(rs.getString("codiceFiscaleBagnino"), rs.getString("nomeBagnino"), rs.getString("cognomeBagnino"), rs.getInt("codiceUnivoco"), rs.getDate("dataDiNascita"), rs.getString("indirizzo"), rs.getString("telefonoBagnino"));
			var ombrelloneConPrenotazione = new PrenotazioneSeduta(numeroSeduta, anno, rs.getDate("dataInizio"), rs.getDate("dataFine"), rs.getDouble("prezzo"), 
					tipoSeduta, cliente, bagnino);
			prenotazioniSeduta.add(ombrelloneConPrenotazione);
		}
		return prenotazioniSeduta;
	}



}















