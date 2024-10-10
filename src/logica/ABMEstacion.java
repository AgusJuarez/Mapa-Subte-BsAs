package logica;

import java.util.ArrayList;
import java.util.List;

import modelo.Estacion;
import modelo.Linea;
import modelo.Tramo;
import net.datastructures.Entry;
import net.datastructures.TreeMap;
import subte.Coordinador;

/**
 * Clase de las Altas, Bajas y Modificaciones para las Estaciones.
 * @author Admin
 *
 */
public class ABMEstacion {
	private List<Estacion> listaEstaciones;
	private Coordinador coordinador;
	
	public ABMEstacion(TreeMap<String, Estacion> estaciones) throws EstacionExisteException {
		listaEstaciones = new ArrayList<Estacion>();
		for(Entry<String, Estacion> e : estaciones.entrySet())
			this.agregarEstacion(e.getValue());

	}
	/**
	 * Agrega una estacion a la lista de estaciones.
	 * @param estacion
	 * @return Estacion
	 * @throws EstacionExisteException
	 */
	public Estacion agregarEstacion(Estacion estacion) throws EstacionExisteException {
		if(listaEstaciones.contains(estacion))
			throw new EstacionExisteException();
		listaEstaciones.add(estacion);
		return estacion;
	}
	
	/**
	 * Modifica la estacion solicitada.
	 * @param estacion
	 * @return Estación.
	 */
	 public Estacion modificarEstacion(Estacion estacion) {
	        Estacion est = buscarEstacion(estacion);
	        if (est != null) {
	            est.setNombre(estacion.getNombre());
	            est.setLinea(estacion.getLinea());
	        }
	        return est;
	    }
	/**
	 * Borra la estacion solicitada.
	 * @param estacion
	 * @return Estacion
	 */
	public Estacion borrarEstacion(Estacion estacion) {
		Estacion est = buscarEstacion(estacion);
		listaEstaciones.remove(est);
		return est;
	}
	
	/**
	 * Busca la estacion dada en el mapa de estaciones.
	 * @param estacion
	 * @return
	 */
	public Estacion buscarEstacion(Estacion estacion) {
		int pos = listaEstaciones.indexOf(estacion);
		if(pos == -1)
			return null;
		return listaEstaciones.get(pos);
	}

	/**
	 * Obtiene una lista de estaciones.
	 * @return List<Estacion>
	 */
	public List<Estacion> getEstaciones() {
		return listaEstaciones;
	}
	
	public void setCoordinador(Coordinador coordinador) {
		this.coordinador = coordinador;
	}
}
