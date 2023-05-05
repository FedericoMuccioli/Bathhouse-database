package lab.model;

import java.util.Date;
import java.util.Objects;

public class PrenotazioneSeduta {

    private final int numeroSeduta;
    private final int anno;
    private final Date dataInizio;
    private final Date dataFine;
    private final double prezzo;
    private final TipoSeduta tipoSeduta;
    private final Cliente cliente;
    private final Bagnino bagnino;
    
    
	public PrenotazioneSeduta(int numeroOmbrellone, int anno, Date dataInizio, Date dataFine, double prezzo, TipoSeduta tipoSeduta, Cliente cliente, Bagnino bagnino) {
		this.numeroSeduta = numeroOmbrellone;
		this.anno = anno;
		this.dataInizio = dataInizio;
		this.dataFine = dataFine;
		this.prezzo = prezzo;
		this.tipoSeduta = tipoSeduta;
		this.cliente = cliente;
		this.bagnino = bagnino;
	}


	public int getNumeroOmbrellone() {
		return numeroSeduta;
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

	public TipoSeduta getTipoSeduta() {
		return tipoSeduta;
	}
	
	public Cliente getCliente() {
		return cliente;
	}
	
	public Bagnino getBagnino() {
		return bagnino;
	}
	    
}
