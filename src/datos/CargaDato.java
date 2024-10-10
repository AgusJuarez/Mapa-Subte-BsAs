package datos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import interfaz.Interfaz;

import java.util.Properties;
import java.util.Scanner;

import gui.Constantes;
import modelo.Estacion;
import modelo.Linea;
import modelo.Tramo;
import net.datastructures.TreeMap;

/**
 * Clase encargada de leer los archivos externos y cargar los datos en el programa.
 * @author Admin
 *
 */
public class CargaDato {
	
	private Scanner lector;
	private String archivoLinea;
	private String archivoEstacion;
	private String archivoTramo;
	
	private TreeMap<String, Linea> lineas;
	private TreeMap<String, Estacion> estaciones;
	private List<Tramo> tramos;
	private TreeMap <Integer, Integer> congestiones;
	
	
	/**
	 * Constructor
	 * @throws FileNotFoundException
	 */
	public CargaDato() throws FileNotFoundException {
		lineas = new TreeMap<String, Linea>();
		estaciones = new TreeMap<String, Estacion>();
		tramos = new ArrayList<Tramo>();
		congestiones = new TreeMap<Integer , Integer>();
		
	}
	
	
	/**
	 * Carga el nombre de los archivos que contienen los datos. (Estaciones, tramos y congestiones)
	 * @throws IOException
	 */
	public void parametros() throws IOException {	
			Properties prop = new Properties();		
			InputStream input = new FileInputStream("config.properties");
			// load a properties file
			prop.load(input);
			// get the property value
			archivoLinea = prop.getProperty("linea");		
			archivoEstacion = prop.getProperty("estacion");
			archivoTramo = prop.getProperty("tramo");
			
			congestiones.put(1, Integer.parseInt(prop.getProperty("congestionBaja")));
			congestiones.put(2, Integer.parseInt(prop.getProperty("congestionMedia")));
			congestiones.put(3, Integer.parseInt(prop.getProperty("congestionAlta")));
//			lineas = this.cargarLineas();
			
	}

	
	/**
	 * Carga los datos desde el archivo de lineas.
	 * @return TreeMap<String, Linea>
	 * @throws FileNotFoundException
	 */
	public TreeMap<String, Linea> cargarLineas() throws FileNotFoundException {
			lector = new Scanner(new File(archivoLinea));
			lector.useDelimiter("\\s*;\\s*");
			String codigo, nombre;
			int i = 0;
			while (lector.hasNext()) {
				codigo = lector.next();
				nombre = lector.next();
				lineas.put(codigo, new Linea(codigo, nombre));
			}
			lector.close();
		
		return lineas;
	}

	
	/**
	 * Carga los datos de las estaciones situado en el archivo de estaciones.
	 * @return TreeMap<String, Estacion>
	 * @throws FileNotFoundException
	 */
	public TreeMap<String, Estacion> cargarEstaciones() throws FileNotFoundException {
			lector = new Scanner(new File(archivoEstacion));
			lector.useDelimiter("\\s*;\\s*");
			String codigo, nombre, linea;
			while (lector.hasNext()) {				
				codigo = lector.next();				
				nombre = lector.next();
				linea = lector.next();
				estaciones.put(codigo, new Estacion(codigo, nombre, lineas.get(linea)));
			}
			lector.close();
			
		return estaciones;
	}

	
	/**
	 * Carga los datos de los tramos encontrado en el archivo de tramos.
	 * @return List<Tramo>
	 * @throws FileNotFoundException
	 */
	public  List<Tramo> cargarTramos() throws FileNotFoundException {
			lector = new Scanner(new File(archivoTramo));
			lector.useDelimiter("\\s*;\\s*");
			Estacion v1, v2;
			int tiempo;
			int tipo;
			int congestion;
			while (lector.hasNext()) {
				v1 = estaciones.get(lector.next());
				v2 = estaciones.get(lector.next());
				tiempo = lector.nextInt();
				congestion = lector.nextInt();
				tipo = lector.nextInt();
				if (tipo == Constantes.VALOR_TRASBORDO)
					tramos.add(new Tramo(v1, v2, tiempo, congestion, Constantes.TRASBORDO));
				else
					tramos.add(new Tramo(v1, v2, tiempo, congestion, tipo));
			}
			lector.close();
		
		return tramos;
	}
	
	
	/**
	 * Devuelve un mapa de las congestiones.
	 * @return TreeMap<Integer , Integer>
	 */
	public TreeMap<Integer , Integer> getCongestiones() {
		return congestiones;
	}

}
