package lab.model;

public class TipoSeduta {
	
	private final int codTipoSeduta; 
	private final String nome;
	private final String desrizione;
	
	public TipoSeduta(int codTipoSeduta, String nome, String desrizione) {
		super();
		this.codTipoSeduta = codTipoSeduta;
		this.nome = nome;
		this.desrizione = desrizione;
	}

	public int getCodTipoSeduta() {
		return codTipoSeduta;
	}

	public String getNome() {
		return nome;
	}

	public String getDesrizione() {
		return desrizione;
	}

	@Override
	public String toString() {
		return nome;
	}
		
	

}
