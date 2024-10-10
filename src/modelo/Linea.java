package modelo;

/**
 * 
 * Clase modelo de Linea.
 * @author Admin
 *
 */
public class Linea {
	
	private String codigo;
	private String nombre;
	
	/**
	 * Constructor.
	 * @param codigo
	 * @param nombre
	 */
	public Linea(String codigo, String nombre) {
		this.codigo = codigo;
		this.nombre = nombre;		
	}
	
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	@Override
	public String toString() {
		return "Linea " + codigo;
	}

	

}
