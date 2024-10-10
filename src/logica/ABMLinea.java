package logica;

import java.util.ArrayList;
import java.util.List;

import modelo.Linea;
import net.datastructures.Entry;
import net.datastructures.TreeMap;
import subte.Coordinador;

/**
 * Clase de las Altas, Bajas y Modificaciones para las Lineas.
 * @author Admin
 *
 */
public class ABMLinea {
	private List<Linea> listaLineas;
	private Coordinador coordinador;
	
	public ABMLinea(TreeMap<String, Linea> lineas) throws EstacionExisteException, LineaExisteException {

		listaLineas = new ArrayList<Linea>();

			
		for(Entry<String, Linea> l : lineas.entrySet())
			this.agregarLinea(l.getValue());
		
	}
	/**
	 * Agrega una linea a la lista de estaciones.
	 * @param linea
	 * @return Linea
	 * @throws LineaExisteException
	 */
	public Linea agregarLinea(Linea linea) throws LineaExisteException {
		if(listaLineas.contains(linea))
			throw new LineaExisteException();
		listaLineas.add(linea);
		return linea;
	}
	
	/**
	 * Modifica la linea solicitada.
	 * @param linea
	 * @return Linea.
	 */
	public Linea modificarLinea(Linea linea) {
		Linea l = buscarLinea(linea);
		listaLineas.remove(linea);
		listaLineas.add(l);
		return l;
	}
	
	/**
	 * Borra la linea solicitada.
	 * @param linea
	 * @return Linea
	 */
	public Linea borrarLinea(Linea linea) {
		Linea l = buscarLinea(linea);
		listaLineas.remove(l);
		return l;
	}
	
	/**
	 * Busca la linea dada en una lista.
	 * @param Linea
	 * @return
	 */
	public Linea buscarLinea(Linea linea) {
		int pos = listaLineas.indexOf(linea);
		if(pos == -1)
			return null;
		return listaLineas.get(pos);
	}

	/**
	 * Obtiene una lista de lineas.
	 * @return List<Linea>
	 */
	public List<Linea> getLineas() {
		return listaLineas;
	}
	
	public void setCoordinador(Coordinador coordinador) {
		this.coordinador = coordinador;
	}
}
