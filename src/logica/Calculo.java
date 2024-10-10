package logica;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import gui.Constantes;
import interfaz.Interfaz;
import modelo.Estacion;
import modelo.Tramo;
import net.datastructures.AdjacencyMapGraph;
import net.datastructures.Edge;
import net.datastructures.Entry;
import net.datastructures.Graph;
import net.datastructures.GraphAlgorithms;
import net.datastructures.LinkedPositionalList;
import net.datastructures.Map;
import net.datastructures.Position;
import net.datastructures.PositionalList;
import net.datastructures.ProbeHashMap;
import net.datastructures.TreeMap;
import net.datastructures.Vertex;
import subte.Coordinador;

/**
 * Clase que contiene los metodos que se usan para calcular los caminos y tiempos de las peticiones del usuario.
 * @author Agustin Juarez
 *
 */
public class Calculo {
	
	private Coordinador coordinador;
	private Graph<Estacion, Tramo> subte;
	private TreeMap<String, Vertex<Estacion>> vertices;
	private TreeMap<Integer, Integer> congestiones;
	
//	private TreeMap<String, Estacion> estaciones;
	

	//Constructor.
	/**
	 * Constructor de la clase.
	 * @param estaciones
	 * @param tramos
	 * @param congestiones
	 */
	public Calculo(TreeMap<String, Estacion> estaciones, List<Tramo> tramos, TreeMap<Integer, Integer> congestiones) {

		subte = new AdjacencyMapGraph<>(false);
		
		// Cargar estaciones
		vertices = new TreeMap<String, Vertex<Estacion>>();
		for (Entry<String, Estacion> estacion : estaciones.entrySet())
			vertices.put(estacion.getKey(),
					subte.insertVertex(estacion.getValue()));

		// Cargar tramos
		for (Tramo tramo : tramos) {
			subte.insertEdge(vertices.get(tramo.getE1().getKey()),
					vertices.get(tramo.getE2().getKey()), tramo);
		}
		
		this.congestiones = congestiones;
	}
	
	/**
	 * Metodo que calcula el trayecto mas rapido (o que lleve menos tiempo) de una estacion a otra.
	 * @param redSubte
	 */
	public List<Tramo> caminoCorto(String[] vec) {
				//Grafo que contendra el subte a calcular.
				Graph<String, Integer> gd = new AdjacencyMapGraph<>(false);
				//Mapa que contendra los datos de las estaciones.
				Map<String, Vertex<String>> mp = new ProbeHashMap<>();
				
				for (Vertex<Estacion> result : subte.vertices())
					mp.put(result.getElement().getKey(), gd.insertVertex(result.getElement().getKey()));
				
				Vertex<Estacion>[] vert;
				for (Edge<Tramo> result : subte.edges()) {
					vert = subte.endVertices(result);
					gd.insertEdge(mp.get(vert[0].getElement().getKey()), mp.get(vert[1]
							.getElement().getKey()), result.getElement().getTiempo());
					
				}

				return obtenerRecorrido(GraphAlgorithms.shortestPathList(gd, mp.get(vec[0].toLowerCase()), mp.get(vec[1].toLowerCase())));
	}
	
	
	/**
	 * Calcula el trayecto de una estacion a otra con menos trasbordos entre ellas.
	 * @param vec
	 * @return PositionalList<Vertex<String>>
	 */
	public List<Tramo> menosTrasbordos(String[] vec) {
		
				//Grafo que contendra el subte a calcular.
				Graph<String, Integer> gc = new AdjacencyMapGraph<>(false);
				//Mapa que contendra los datos de las estaciones.
				Map<String, Vertex<String>> mc = new ProbeHashMap<>();
				
				for (Vertex<Estacion> result : subte.vertices())
					mc.put(result.getElement().getKey(), gc.insertVertex(result.getElement().getKey()));

				Vertex<Estacion>[] ver;
				for (Edge<Tramo> result : subte.edges()) {
					ver = subte.endVertices(result);
					gc.insertEdge(mc.get(ver[0].getElement().getKey()), mc.get(ver[1]
							.getElement().getKey()), result.getElement().getTrasbordo());
					
				}
								
				return obtenerRecorrido(GraphAlgorithms.shortestPathList(gc, mc.get(vec[0].toLowerCase()), mc.get(vec[1].toLowerCase())));				
	}
	
	
	/**
	 * Calcula el trayecto de una estacion a otra con menos congestion entre ellas.
	 * @param vec
	 * @return PositionalList<Vertex<String>>
	 */
	public List<Tramo> menosCongestion (String[] vec){

			//Grafo que contendra el subte a calcular.
			Graph<String, Integer> gc = new AdjacencyMapGraph<>(false);
			//Mapa que contendra los datos de las estaciones.
			Map<String, Vertex<String>> mc = new ProbeHashMap<>();
			
			for (Vertex<Estacion> result : subte.vertices())
				mc.put(result.getElement().getKey(), gc.insertVertex(result.getElement().getKey()));
			
			Vertex<Estacion>[] ver;
			for (Edge<Tramo> result : subte.edges()) {
				ver = subte.endVertices(result);
				int resultadoTiempo = result.getElement().getTiempo();
				gc.insertEdge(mc.get(ver[0].getElement().getKey()), mc.get(ver[1].getElement().getKey()), resultadoTiempo);

			}
					
			return obtenerRecorrido(GraphAlgorithms.shortestPathList(gc, mc.get(vec[0].toLowerCase()), mc.get(vec[1].toLowerCase())));
			
	}
	
	/**
	 * Retorna una lista con los tramos involucrados en el recorrido hecho con sus respectivos tiempos.
	 * @param camino
	 * @return List<Tramo>
	 */	
	private List<Tramo> obtenerRecorrido(PositionalList<Vertex<String>> camino) {

		//Se guarda el primer elemento de la lista.
		Position<Vertex<String>> aux = camino.first();
		//Variables auxiliares para el guardado de las estaciones involucradas en los tramos.
		Vertex<Estacion> verticeA = null; 
		Vertex<Estacion> verticeB = null;

		List<Tramo> tramos = new ArrayList<Tramo>();
		
		while (camino.after(aux) != null) {

			verticeA = vertices.get(aux.getElement().getElement());
			
			aux = camino.after(aux);

			verticeB = vertices.get(aux.getElement().getElement());
				
			tramos.add(subte.getEdge(verticeA, verticeB).getElement());
			
		}
		
		return tramos;
	}
	
	public void setCoordinador(Coordinador coordinador) {
		this.coordinador = coordinador;
	}
	
}



