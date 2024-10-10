package logica;

import java.util.ArrayList;
import java.util.List;

import modelo.Tramo;
import subte.Coordinador;

/**
 * Clase de las Altas, Bajas y Modificaciones para los Tramos.
 * @author Admin
 *
 */
public class ABMTramo {

	private List<Tramo> listaTramos;
	private Coordinador coordinador;
	
	/** Constructor
	 * @param tramos
	 * @throws EstacionExisteException
	 * @throws LineaExisteException
	 */
	public ABMTramo(List<Tramo> tramos ) throws EstacionExisteException, LineaExisteException {
		listaTramos = new ArrayList<Tramo>();
		this.listaTramos = tramos;
	}
	public List<Tramo> getTramos() {
		return listaTramos;
	}
	/**
	 * Busca un tramo en la lista de Tramos dado un tramo unicamente con los vertices involucrados.
	 * @param tramo
	 * @return
	 */
	public Tramo buscarTramo(Tramo tramo) {
		int pos = listaTramos.indexOf(tramo);
		if(pos == -1)
			return null;
		return listaTramos.get(pos);
	}
	
	/**
	 * Modificar los parametros de un tramo.
	 * @param tramo
	 * @return
	 */
	public Tramo modificarTramo(Tramo tramo) {
		Tramo t = buscarTramo(tramo);
		listaTramos.remove(tramo);
		listaTramos.add(t);
		return t;
	}
	
	/**
	 * Borra el tramo pasado como parametro.
	 * @param tramo
	 * @return
	 */
	public Tramo borrarTramo(Tramo tramo) {
		Tramo t = buscarTramo(tramo);
		listaTramos.remove(t);
		return t;
	}
	
	/**
	 * Agrega un tramo a la lista.
	 * @param tramo
	 * @return
	 * @throws TramoExisteException
	 */
	public Tramo agregarTramo(Tramo tramo) throws TramoExisteException {
		if(listaTramos.contains(tramo))
			throw new TramoExisteException();
		listaTramos.add(tramo);
		return tramo;
	}
	
	public void setCoordinador(Coordinador coordinador) {
		this.coordinador = coordinador;
	}
}
