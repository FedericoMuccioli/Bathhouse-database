package lab.model;

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
		
	

}