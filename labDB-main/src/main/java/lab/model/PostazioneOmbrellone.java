package lab.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lab.utils.Utils;

public class PostazioneOmbrellone {

	private final int anno;
	private final int numeroOmbrellone;
	private final Date dataInizio;
	private final Optional<Date> dataFine;
	private final int fila;
	private final int colonna;

	public PostazioneOmbrellone(final int anno, final int numeroOmbrellone, 
			final Date dataInizio, final Optional<Date> dataFine, final int fila, final int colonna) {
		this.anno = anno;
		this.numeroOmbrellone = numeroOmbrellone;
		this.dataInizio = dataInizio;
		this.dataFine = dataFine;
		this.fila = fila;
		this.colonna = colonna;
	}

	public int getAnno() {
		return anno;
	}

	public int getNumeroOmbrellone() {
		return numeroOmbrellone;
	}

	public Date getDataInizio() {
		return dataInizio;
	}

	public Optional<Date> getDataFine() {
		return dataFine;
	}

	public int getFila() {
		return fila;
	}

	public int getColonna() {
		return colonna;
	}

	public static List<PostazioneOmbrellone> readPostazioniOmbrelloniFromResultSet(final ResultSet resultSet) throws SQLException {
		final List<PostazioneOmbrellone> postazioniOmbrelloni = new ArrayList<>();
		while (resultSet.next()) {
			final var postazioneOmbrellone = new PostazioneOmbrellone(resultSet.getInt("anno"), 
					resultSet.getInt("numeroOmbrellone"), Utils.sqlDateToDate(resultSet.getDate("dataInizio")),
					Optional.ofNullable(Utils.sqlDateToDate(resultSet.getDate("dataFine"))), 
					resultSet.getInt("fila"), resultSet.getInt("colonna"));
			postazioniOmbrelloni.add(postazioneOmbrellone);
		}
		return postazioniOmbrelloni;
	}

	@Override
	public String toString() {
		return "PostazioneOmbrellone [anno=" + anno + ", numeroOmbrellone=" + numeroOmbrellone + ", dataInizio="
				+ dataInizio + ", dataFine=" + dataFine + ", fila=" + fila + ", colonna=" + colonna + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(anno, colonna, dataFine, dataInizio, fila, numeroOmbrellone);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PostazioneOmbrellone other = (PostazioneOmbrellone) obj;
		return anno == other.anno && colonna == other.colonna && Objects.equals(dataFine, other.dataFine)
				&& Objects.equals(dataInizio, other.dataInizio) && fila == other.fila
				&& numeroOmbrellone == other.numeroOmbrellone;
	}

}
