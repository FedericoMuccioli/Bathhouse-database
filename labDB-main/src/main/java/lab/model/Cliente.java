package lab.model;

import java.util.Date;
import java.util.Objects;

public class Cliente {
    private final String codiceFiscale;
    private final String nome;
    private final String cognome;
    private final TipoCliente tipoCliente;
    private final String telefono;
    
    public Cliente(final String codiceFiscale, final String nome, final String cognome, final String telefono, final TipoCliente tipoCliente) {
        this.codiceFiscale = codiceFiscale;
        this.nome = Objects.requireNonNull(nome);
        this.cognome = Objects.requireNonNull(cognome);
        this.tipoCliente = Objects.requireNonNull(tipoCliente);
        this.telefono = telefono;
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

	public TipoCliente getTipoCliente() {
		return tipoCliente;
	}

	public String getTelefono() {
		return telefono;
	}

    @Override
	public String toString() {
    	String s = " ";
    	return nome.concat(s).concat(cognome).concat(s).concat(tipoCliente.toString()).concat(telefono.isBlank() ? "" : s + telefono).concat(s).concat(codiceFiscale);	
    }
	
}
