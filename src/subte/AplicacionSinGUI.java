package subte;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.List;

import datos.CargaDato;
import interfaz.Interfaz;
import logica.Calculo;
import modelo.Estacion;
import modelo.Linea;
import modelo.Tramo;
import net.datastructures.TreeMap;
import net.datastructures.Vertex;


/**
 * 
 * Clase que contiene el main de la aplicacion sin la gui.
 * @author Admin
 *
 */
public class AplicacionSinGUI {
	
	public static void main (String[] args) throws FileNotFoundException, OpcionIncorrectaException {
		
		Coordinador coordinador = new Coordinador();
		
		CargaDato cargaDato = new CargaDato();
		// Cargar parametros del archivo config.properties
				try {
					cargaDato.parametros();
				} catch (IOException e) {
					System.err.print("Error al cargar parámetros");
					System.exit(-1);
				}

				// Cargar datos
				TreeMap<String, Linea> lineas = null;
				TreeMap<String, Estacion> estaciones = null;
				TreeMap<Integer, Integer> congestiones = null;
				List<Tramo> tramos = null;
				try {
					
					lineas = cargaDato.cargarLineas();
					
					estaciones = cargaDato.cargarEstaciones();

					tramos = cargaDato.cargarTramos();
					
					congestiones = cargaDato.getCongestiones();
					
				} catch (FileNotFoundException e) {
					Interfaz.mostrarError("Error al cargar archivos de datos");
					System.exit(-1);
				}

		Interfaz interfaz = new Interfaz(lineas, estaciones);
		Calculo m = new Calculo(estaciones, tramos, congestiones);
		
		m.setCoordinador(coordinador);
		coordinador.setCalculo(m);
		
		int opcion = 0;

		try {
			opcion = interfaz.elegirRecorrido();
		} catch (InputMismatchException e) {
			Interfaz.mostrarError("Opción Incorrecta. Inténtelo nuevamente.");
		}
			
		
		
		//Dependiendo de la opcion que elija el usuario, se hara la operacion elegida.
		switch (opcion) {
		case 1:
			interfaz.matEstaciones();
			try {
				Interfaz.mostrarResultado(coordinador.caminoCorto(interfaz.estacionesUsuario()));
			} catch (NullPointerException e) {
				Interfaz.mostrarError("Uno de los códigos o los dos códigos introducidos son incorrectos.");
			}
			
			break;
		
		case 2:
			interfaz.matEstaciones();
			try {
				Interfaz.mostrarResultado(coordinador.menosTrasbordo(interfaz.estacionesUsuario()));
				}
			catch (NullPointerException e) {
				Interfaz.mostrarError("Uno de los códigos o los dos códigos introducidos son incorrectos.");
			}
			
			break;
		
		case 3:
			interfaz.matEstaciones();
			try {
				Interfaz.mostrarResultado(coordinador.menosCongestion(interfaz.estacionesUsuario()));
				}
			catch (NullPointerException e) {
				Interfaz.mostrarError("Uno de los códigos o los dos códigos introducidos son incorrectos.");
			}
			break;
		
		default:
			throw new OpcionIncorrectaException();
		}
		
	}

}
