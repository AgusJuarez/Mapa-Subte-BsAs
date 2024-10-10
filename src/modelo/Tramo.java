package modelo;

/**
 * Clase modelo de Tramo.
 * @author Admin
 *
 */
public class Tramo {
	private int tiempo;
	private int trasbordo;
	private Estacion e1;
	private Estacion e2;
	private int congestion;
	
	/**
	 * Constructor.
	 * @param e1
	 * @param e2
	 * @param tiempo
	 * @param congestion
	 * @param trasbordo
	 */
	public Tramo(Estacion e1, Estacion e2, int tiempo, int congestion, int trasbordo) {
		this.e1 = e1;
		this.e2 = e2;
		this.tiempo = tiempo;
		this.trasbordo = trasbordo;
		this.congestion = congestion;
	}
	public int getTrasbordo() {
		return trasbordo;
	}
	public void setTrasbordo(int trasbordo) {
		this.trasbordo = trasbordo;
	}
	public Estacion getE1() {
		return e1;
	}
	public void setE1(Estacion e1) {
		this.e1 = e1;
	}
	public Estacion getE2() {
		return e2;
	}
	public void setE2(Estacion e2) {
		this.e2 = e2;
	}
	public int getTiempo() {
		return tiempo;
	}
	public void setTiempo(int tiempo) {
		this.tiempo = tiempo;
	}
	public int getCongestion() {
		return congestion;
	}
	public void setCongestion(int congestion) {
		this.congestion = congestion;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((e1 == null) ? 0 : e1.hashCode());
		result = prime * result + ((e2 == null) ? 0 : e2.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tramo other = (Tramo) obj;
		if (e1 == null) {
			if (other.e1 != null)
				return false;
		} else if (!e1.equals(other.e1))
			return false;
		if (e2 == null) {
			if (other.e2 != null)
				return false;
		} else if (!e2.equals(other.e2))
			return false;
		return true;
	}
	

}
