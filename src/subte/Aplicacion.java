package subte;

import java.awt.EventQueue;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import datos.CargaDato;
import gui.AplicacionFrame;
import gui.EstacionForm;
import gui.EstacionList;
import gui.LineaForm;
import gui.LineaList;
import gui.TramoForm;
import gui.TramoList;
import interfaz.Interfaz;
import logica.ABMEstacion;
import logica.ABMLinea;
import logica.ABMTramo;
import logica.Calculo;
import logica.EstacionExisteException;
import modelo.Estacion;
import modelo.Linea;
import modelo.Tramo;
import net.datastructures.TreeMap;

/**
 * 
 * Clase que contiene el main de la aplicacion con la gui.
 * @author Admin
 *
 */
public class Aplicacion {
	
	// lógica
		private Calculo calculo;
		// vista
		private AplicacionFrame frame;
		private EstacionList estacionList;
		private EstacionForm estacionForm;
		private LineaList lineaList;
		private LineaForm lineaForm;
		private TramoList tramoList;
		private TramoForm tramoForm;
		private ABMEstacion abmEstaciones;
		private ABMLinea abmLineas;
		private ABMTramo abmTramos;
		// controlador
		private Coordinador coordinador;
	
	public static void main(String[] args) throws FileNotFoundException, EstacionExisteException {
		Aplicacion miAplicacion = new Aplicacion();
		miAplicacion.iniciar();
	}
	
	
	public void iniciar() throws EstacionExisteException {
		
		EventQueue.invokeLater(new Runnable() {
			/**
			 *
			 */
			public void run() {
				try {
					//Carga de datos en los archivos
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
					
					
					calculo = new Calculo(estaciones, tramos, congestiones);
					
					/* Se instancian las clases */
					coordinador = new Coordinador();
					frame = new AplicacionFrame(estaciones, calculo);
					abmEstaciones = new ABMEstacion(estaciones);
					abmLineas = new ABMLinea(lineas);
					abmTramos = new ABMTramo(tramos);
					estacionList = new EstacionList();
					estacionForm = new EstacionForm(abmLineas.getLineas());
					lineaList = new LineaList();
					lineaForm = new LineaForm();
					tramoList = new TramoList();
					tramoForm = new TramoForm(abmEstaciones.getEstaciones());
					
					
					
					/* Se establecen las relaciones entre clases */
					calculo.setCoordinador(coordinador);
					frame.setCoordinador(coordinador);
					estacionList.setCoordinador(coordinador);
					estacionForm.setCoordinador(coordinador);
					lineaList.setCoordinador(coordinador);
					lineaForm.setCoordinador(coordinador);
					tramoList.setCoordinador(coordinador);
					tramoForm.setCoordinador(coordinador);
					abmEstaciones.setCoordinador(coordinador);
					abmLineas.setCoordinador(coordinador);
					abmTramos.setCoordinador(coordinador);
					
					
					/* Se establecen relaciones con la clase coordinador */
					coordinador.setCalculo(calculo);
					coordinador.setAplicacion(frame);
					coordinador.setEstacionList(estacionList);
					coordinador.setEstacionForm(estacionForm);
					coordinador.setLineaList(lineaList);
					coordinador.setLineaForm(lineaForm);
					coordinador.setTramoList(tramoList);
					coordinador.setTramoForm(tramoForm);
					coordinador.setABMEstaciones(abmEstaciones);
					coordinador.setABMLineas(abmLineas);
					coordinador.setABMTramos(abmTramos);
					
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	}


