package lab.model;

import java.sql.Time;

public class FasciaOraria {
	
	private final int id; 
	private final Time inizio;
	private final Time fine;
	
	public FasciaOraria(int id, Time inizio, Time fine) {
		super();
		this.id = id;
		this.inizio = inizio;
		this.fine = fine;
	}
	
	public FasciaOraria(Time inizio, Time fine) {
		this(-1, inizio, fine);
	}
	
	public int getId() {
		return id;
	}

	public Time getInizio() {
		return inizio;
	}

	public Time getFine() {
		return fine;
	}

	@Override
	public String toString() {
		return inizio == null || fine == null ? "qualsiasi" : inizio.toString().substring(0, 5) + " - " + fine.toString().substring(0, 5);
	}
	
	
}
