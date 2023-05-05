package lab.model;

import java.util.Date;
import java.util.Objects;

public class Bagnino {
    private final String codiceFiscale;
    private final String nome;
    private final String cognome;
    private final int codiceUnivoco;
    private final Date dataNascita;
    private final String indirizzo;
    private final String telefono;
    
    public Bagnino(final String codiceFiscale, final String nome, final String cognome, final int codiceUnivoco,
    		final Date dataNascita, final String indirizzo, final String telefono) {
        this.codiceFiscale = codiceFiscale;
        this.nome = Objects.requireNonNull(nome);
        this.cognome = Objects.requireNonNull(cognome);
        this.codiceUnivoco = Objects.requireNonNull(codiceUnivoco);
        this.dataNascita = Objects.requireNonNull(dataNascita);
        this.indirizzo = Objects.requireNonNull(indirizzo);
        this.telefono = Objects.requireNonNull(telefono);
    }
    
    public Bagnino(final String codiceFiscale, final String nome, final String cognome,
    		final Date dataNascita, final String indirizzo, final String telefono) {
    	this(codiceFiscale, nome, cognome, -1, dataNascita, indirizzo, telefono);
    }

    public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public String getNome() {
		return nome;
	}

	public String getCognome() {
		return cognome;
	}

	public int getCodiceUnivoco() {
		return codiceUnivoco;
	}

	public Date getDataNascita() {
		return dataNascita;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public String getTelefono() {
		return telefono;
	}

    @Override
	public String toString() {
		return "Bagnino [codiceFiscale=" + codiceFiscale + ", nome=" + nome + ", cognome=" + cognome
				+ ", codiceUnivoco=" + codiceUnivoco + ", dataNascita=" + dataNascita + ", indirizzo=" + indirizzo
				+ ", telefono=" + telefono + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Bagnino other = (Bagnino) obj;
		return Objects.equals(codiceFiscale, other.codiceFiscale) && codiceUnivoco == other.codiceUnivoco
				&& Objects.equals(cognome, other.cognome) && Objects.equals(dataNascita, other.dataNascita)
				&& Objects.equals(indirizzo, other.indirizzo) && Objects.equals(nome, other.nome)
				&& Objects.equals(telefono, other.telefono);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(codiceFiscale, codiceUnivoco, cognome, dataNascita, indirizzo, nome, telefono);
	}
	
}
