package lab.model;

import java.util.Objects;

public class TipoProdotto {
	
	private final int id; 
	private final String tipo;
	private final String desrizione;
	
	public TipoProdotto(int id, String tipo, String desrizione) {
		super();
		this.id = id;
		this.tipo = tipo;
		this.desrizione = desrizione;
	}

	public int getId() {
		return id;
	}

	public String getTipo() {
		return tipo;
	}

	public String getDesrizione() {
		return desrizione;
	}

	@Override
	public String toString() {
		return tipo;
	}

	@Override
	public int hashCode() {
		return Objects.hash(desrizione, id, tipo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TipoProdotto other = (TipoProdotto) obj;
		return Objects.equals(desrizione, other.desrizione) && id == other.id && Objects.equals(tipo, other.tipo);
	}
		
	

}