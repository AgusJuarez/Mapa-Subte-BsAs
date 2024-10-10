package modelo;


/**
 * Clase modelo de la Estacion.
 * @author Admin
 *
 */
public class Estacion {
	private String codigo;
	private String nombre;
	private Linea linea;

	/**
	 * 
	 * Constructor.
	 * @param codigo
	 * @param nombre
	 * @param lineas
	 */
	public Estacion(String codigo, String nombre, Linea lineas ) {
		this.codigo = codigo;
		this.nombre = nombre;
		this.linea = lineas;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getKey() {
		return codigo;
	}
	
	public void setKey(String codigo) {
		this.codigo = codigo;
	}

	public Linea getLinea() {
		return linea;
	}

	public void setLinea(Linea linea) {
		this.linea = linea;
	}

	@Override
	public String toString() {
		return "Linea " + linea.getCodigo() + " - " + nombre;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
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
		Estacion other = (Estacion) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
	
}
