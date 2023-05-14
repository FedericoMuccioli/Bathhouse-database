package lab.model;

import java.util.Date;
import java.util.Objects;

public class PrenotazioneOmbrellone {

    private final int numeroOmbrellone;
    private final int anno;
    private final Date dataInizio;
    private final Date dataFine;
    private final double prezzo;
    private final int nLettini;
    private final int nSedie;
    private final int nSdraio;
    private final Cliente cliente;
    private final Dipendente bagnino;
    
    
	public PrenotazioneOmbrellone(int numeroOmbrellone, int anno, Date dataInizio, Date dataFine, double prezzo, int nLettini, int nSedie, int nSdraio, Cliente cliente, Dipendente bagnino) {
		this.numeroOmbrellone = numeroOmbrellone;
		this.anno = anno;
		this.dataInizio = dataInizio;
		this.dataFine = dataFine;
		this.prezzo = prezzo;
		this.nLettini = nLettini;
		this.nSedie = nSedie;
		this.nSdraio = nSdraio;
		this.cliente = cliente;
		this.bagnino = bagnino;
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


	public Date getDataFine() {
		return dataFine;
	}


	public double getPrezzo() {
		return prezzo;
	}

	public int getnLettini() {
		return nLettini;
	}


	public int getnSedie() {
		return nSedie;
	}


	public int getnSdraio() {
		return nSdraio;
	}
	
	public Cliente getCliente() {
		return cliente;
	}
	
	public Dipendente getBagnino() {
		return bagnino;
	}
	    
}
