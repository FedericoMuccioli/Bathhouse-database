package lab.model;

import java.util.List;
import java.util.Objects;

public class Prodotto {
    private final int id;
    private final String nome;
    private final String descrizione;
    private final TipoProdotto tipo;
    private final List<FasciaOraria> fasceOrarie;
    private final double prezzo;

    public Prodotto(String nome, String descrizione, TipoProdotto tipo, double prezzo) {
        this(-1, nome, descrizione, tipo, null, prezzo);
    }
    
    public Prodotto(String nome, String descrizione, TipoProdotto tipo, List<FasciaOraria> fasceOrarie, double prezzo) {
        this(-1, nome, descrizione, tipo, fasceOrarie, prezzo);
    }
    
    public Prodotto(int id, String nome, String descrizione, TipoProdotto tipo, List<FasciaOraria> fasceOrarie, double prezzo) {
        this.id = id;
        this.nome = Objects.requireNonNull(nome);
        this.descrizione = Objects.requireNonNull(descrizione);
        this.tipo = Objects.requireNonNull(tipo);
        this.fasceOrarie = fasceOrarie;
        this.prezzo = prezzo;
    }
    
    public int getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public TipoProdotto getTipo() {
		return tipo;
	}
	
	public List<FasciaOraria> getFasceOrarie() {
		return fasceOrarie;
	}
	
	public double getPrezzo() {
		return prezzo;
	}

    @Override
	public String toString() {
    	String s = " ";
    	return nome.concat(s).concat(descrizione).concat(s).concat(tipo.toString()).concat(s).concat(String.valueOf(prezzo).concat("€"));	
    }

	@Override
	public int hashCode() {
		return Objects.hash(descrizione, fasceOrarie, id, nome, prezzo, tipo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Prodotto other = (Prodotto) obj;
		return Objects.equals(descrizione, other.descrizione) && Objects.equals(fasceOrarie, other.fasceOrarie)
				&& id == other.id && Objects.equals(nome, other.nome)
				&& Double.doubleToLongBits(prezzo) == Double.doubleToLongBits(other.prezzo)
				&& Objects.equals(tipo, other.tipo);
	}
	
}
