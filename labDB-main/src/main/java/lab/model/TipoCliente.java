package lab.model;

import java.util.Objects;

public class TipoCliente {
    private final int codiceUnivoco;
    private final String tipo;
    
    public TipoCliente(int codiceUnivoco, String tipo) {
        this.codiceUnivoco = codiceUnivoco;
        this.tipo = tipo;
    }

    public int getCodiceUnivoco() {
		return codiceUnivoco;
	}

	public String getTipo() {
		return tipo;
	}

    @Override
	public String toString() {
		return this.tipo;
	}

	@Override
	public int hashCode() {
		return Objects.hash(codiceUnivoco, tipo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TipoCliente other = (TipoCliente) obj;
		return codiceUnivoco == other.codiceUnivoco && Objects.equals(tipo, other.tipo);
	}
	
}
