package lab.model;

import java.util.Date;
import java.util.Objects;

public class OmbrelloneConPrenotazione {

    private final int numeroOmbrellone;
    private final int anno;
    private final Date dataInizio;
    private final Date dataFine;
    private final double prezzo;
    private final String codiceFiscaleCliente;
    private final int codiceUnivocoBagnino;
    
    
	public OmbrelloneConPrenotazione(int numeroOmbrellone, int anno, Date dataInizio, Date dataFine, double prezzo,
			String codiceFiscaleCliente, int codiceUnivocoBagnino) {
		super();
		this.numeroOmbrellone = numeroOmbrellone;
		this.anno = anno;
		this.dataInizio = dataInizio;
		this.dataFine = dataFine;
		this.prezzo = prezzo;
		this.codiceFiscaleCliente = codiceFiscaleCliente;
		this.codiceUnivocoBagnino = codiceUnivocoBagnino;
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
	public String getCodiceFiscaleCliente() {
		return codiceFiscaleCliente;
	}
	public int getCodiceUnivocoBagnino() {
		return codiceUnivocoBagnino;
	}
    
	@Override
	public String toString() {
		return "OmbrelloneConPrenotazione [numeroOmbrellone=" + numeroOmbrellone + ", anno=" + anno + ", dataInizio="
				+ dataInizio + ", dataFine=" + dataFine + ", prezzo=" + prezzo + ", codiceFiscaleCliente="
				+ codiceFiscaleCliente + ", codiceUnivocoBagnino=" + codiceUnivocoBagnino + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(anno, codiceFiscaleCliente, codiceUnivocoBagnino, dataFine, dataInizio, numeroOmbrellone,
				prezzo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OmbrelloneConPrenotazione other = (OmbrelloneConPrenotazione) obj;
		return anno == other.anno && Objects.equals(codiceFiscaleCliente, other.codiceFiscaleCliente)
				&& codiceUnivocoBagnino == other.codiceUnivocoBagnino && Objects.equals(dataFine, other.dataFine)
				&& Objects.equals(dataInizio, other.dataInizio) && numeroOmbrellone == other.numeroOmbrellone
				&& Double.doubleToLongBits(prezzo) == Double.doubleToLongBits(other.prezzo);
	}
    
}
