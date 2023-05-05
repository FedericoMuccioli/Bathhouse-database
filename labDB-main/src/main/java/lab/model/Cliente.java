package lab.model;

import java.util.Date;
import java.util.Objects;

public class Cliente {
    private final String codiceFiscale;
    private final String nome;
    private final String cognome;
    private final String tipoCliente;
    private final String telefono;
    
    public Cliente(final String codiceFiscale, final String nome, final String cognome, final String telefono, final String tipoCliente) {
        this.codiceFiscale = codiceFiscale;
        this.nome = Objects.requireNonNull(nome);
        this.cognome = Objects.requireNonNull(cognome);
        this.tipoCliente = Objects.requireNonNull(tipoCliente);
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

	public String getTipoCliente() {
		return tipoCliente;
	}

	public String getTelefono() {
		return telefono;
	}

    @Override
	public String toString() {
    	String s = " ";
    	return nome.concat(s).concat(cognome).concat(s).concat(tipoCliente).concat(s).concat(telefono).concat(s).concat(codiceFiscale);
//		return "Bagnino [codiceFiscale=" + codiceFiscale + ", nome=" + nome + ", cognome=" + cognome
//				+ ", tipoCliente=" + tipoCliente + ", telefono=" + telefono + "]";
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
		return Objects.equals(codiceFiscale, other.codiceFiscale) && tipoCliente == other.tipoCliente
				&& Objects.equals(cognome, other.cognome) && Objects.equals(nome, other.nome)
				&& Objects.equals(telefono, other.telefono);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(codiceFiscale, tipoCliente, cognome, nome, telefono);
	}
	
}
