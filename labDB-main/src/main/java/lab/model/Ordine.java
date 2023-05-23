package lab.model;

import java.util.Date;

public class Ordine {
	
	public enum Stato {IN_ELABORAZIONE, COMPLETATO}
	
	private int idOrdine;
	private final Date dataOrdine;
	private final Date oraConsegna;
	private final double prezzo;
	private final Stato stato;
	private final Dipendente barista;
	private final int numeroOmbrellone;
	private final int anno;
	private final Date dataInizio;
	
	public Ordine(Date dataOrdine, Date oraConsegna, double prezzo, Stato stato, Dipendente barista, int numeroOmbrellone, int anno, Date dataInizio) {
		this(-1, dataOrdine, oraConsegna, prezzo, stato, barista, numeroOmbrellone, anno, dataInizio);
	}
	
	public Ordine(int idOrdine, Date dataOrdine, Date oraConsegna, double prezzo, Stato stato, Dipendente barista, int numeroOmbrellone, int anno, Date dataInizio) {
		this.idOrdine = idOrdine;
		this.dataOrdine = dataOrdine;
		this.oraConsegna = oraConsegna;
		this.prezzo = prezzo;
		this.stato = stato;
		this.barista = barista;
		this.numeroOmbrellone = numeroOmbrellone;
		this.anno = anno;
		this.dataInizio = dataInizio;	
	}

	public void setIdOrdine(int idOrdine) {
		this.idOrdine = idOrdine;
	}
	
	public int getIdOrdine() {
		return idOrdine;
	}

	public Date getDataOrdine() {
		return dataOrdine;
	}


	public Date getOraConsegna() {
		return oraConsegna;
	}


	public double getPrezzo() {
		return prezzo;
	}


	public Stato getStato() {
		return stato;
	}


	public Dipendente getBarista() {
		return barista;
	}


	public int getNumeroOmbrellone() {
		return numeroOmbrellone;
	}


	public int getAnno() {
		return anno;
	}


	public Date getDataInizio() {
		return dataInizio;
	}

	@Override
	public String toString() {
		return "Id:" + idOrdine + ", Ombrellone:"
				+ numeroOmbrellone + ", DataOrdine:" + dataOrdine + "_" + oraConsegna
				+ ", Prezzo:" + prezzo + "€, Barista:" + barista;
	} 

}
