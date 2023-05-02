package lab.model;

import java.util.Date;
import java.util.Objects;

public class Cliente {
    private final String codiceFiscale;
    private final String nome;
    private final String cognome;
    private final int codiceUnivoco;
    private final Date dataDiNascita;
    private final String indirizzo;
    private final String telefono;
    
    public Cliente(final String codiceFiscale, final String nome, final String cognome, final int codiceUnivoco,
    		final Date dataDiNascita, final String indirizzo, final String telefono) {
        this.codiceFiscale = codiceFiscale;
        this.nome = Objects.requireNonNull(nome);
        this.cognome = Objects.requireNonNull(cognome);
        this.codiceUnivoco = Objects.requireNonNull(codiceUnivoco);
        this.dataDiNascita = Objects.requireNonNull(dataDiNascita);
        this.indirizzo = Objects.requireNonNull(indirizzo);
        this.telefono = Objects.requireNonNull(telefono);
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

	public Date getDataDiNascita() {
		return dataDiNascita;
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
				+ ", codiceUnivoco=" + codiceUnivoco + ", dataDiNascita=" + dataDiNascita + ", indirizzo=" + indirizzo
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
		Cliente other = (Cliente) obj;
		return Objects.equals(codiceFiscale, other.codiceFiscale) && codiceUnivoco == other.codiceUnivoco
				&& Objects.equals(cognome, other.cognome) && Objects.equals(dataDiNascita, other.dataDiNascita)
				&& Objects.equals(indirizzo, other.indirizzo) && Objects.equals(nome, other.nome)
				&& Objects.equals(telefono, other.telefono);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(codiceFiscale, codiceUnivoco, cognome, dataDiNascita, indirizzo, nome, telefono);
	}
	
}
