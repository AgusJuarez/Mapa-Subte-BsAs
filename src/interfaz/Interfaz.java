package interfaz;


import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map.Entry;

import gui.Constantes;

import java.util.Scanner;

import modelo.Estacion;
import modelo.Linea;
import modelo.Tramo;
import net.datastructures.PositionalList;
import net.datastructures.TreeMap;
import net.datastructures.Vertex;

/**
 * Clase que contiene la interfaz que se comunica con el usuario, o le brinda cierta informacion.
 * @author Agustin Juarez
 *
 */
public class Interfaz {
	
	private String [][] matriz;
	private List<String> listaEstaciones;
	
	public Interfaz (TreeMap<String, Linea> lineas, TreeMap<String, Estacion> estaciones) {
		int i = 0;
		int j = 0;
		String lineaAnterior = "";
		List<Linea> l = new ArrayList<Linea>();
		List<Estacion> e = new ArrayList<Estacion>();
		listaEstaciones = new ArrayList<String>();
		
		for(net.datastructures.Entry<String, Linea> entry : lineas.entrySet())
			l.add(entry.getValue());
		
		for(net.datastructures.Entry<String, Estacion> entry : estaciones.entrySet()) {
			e.add(entry.getValue());
			listaEstaciones.add(entry.getValue().getKey());
		}	
		
		
		matriz = new String[l.size()][e.size()];
		
		for(Linea line : l) {
			matriz[i][0] = line.getNombre() + "		";
			i++;
		}
		i = 0;
		for(Estacion est : e) {
			//Empiezo a cargar los codigos y nombres de las estaciones en la matriz.
			if (lineaAnterior == "")
				lineaAnterior = l.get(0).getCodigo();
			
			if (est.getLinea().getCodigo().equals(lineaAnterior)) {
				j++;

			} else {
				j = 1;
				i++;
			}
			lineaAnterior = est.getLinea().getCodigo();
			matriz[i][j] = est.getKey() + ". " + est.getNombre() + "      ";
			
		}
	}

	/**
	 * Metodo que muestra las opciones para el usuario
	 * @return
	 */
	public int elegirRecorrido () {
		Scanner res = new Scanner(System.in);
		int resultado;
		System.out.printf("Bienvenido! Elija una opción \n" + 
							"1- Camino mas corto.\n" + 
							"2- Camino con menos trasbordos.\n" +
							"3- Camino con menos congestión."
							);
		boolean ok = true;
		while(ok) {
		try {
			 resultado = res.nextInt();
			 if((resultado == 1) || (resultado == 2) || (resultado == 3)) {
				 ok = false;
				 return resultado;
			 } else 
				 System.out.println("Opción Incorrecta. Intentelo nuevamente.");
		} catch (InputMismatchException e) {
			System.out.println("Opción Incorrecta. Intentelo nuevamente.");
		} 
		}
		return 0;
	}
	
	public String[][] getMatriz() {
		return matriz;
	}
	
	public void setMatriz(String[][] matrizNueva) {
		matriz = matrizNueva;
	}
	/**
	 * Metodo para mostrar la matriz con las estaciones y su codigo correspondiente
	 */
	public void matEstaciones(){
		System.out.println("Por favor ponga a continuación el código correspondiente\n"+
							"a la estación en donde se encuentra. Luego el código de\n"+
							"estación destino.");
		System.out.println();
		for(String[] aux : getMatriz()) {
			for(String elem : aux) {
				if (!(elem == null))
					System.out.printf(elem);
			}
			System.out.println();
		}
		System.out.println();
	}
		
	/**
	 * Metodo que le indica al usuario introducir la estacion en donde partira y la estacion destino
	 * @return = devuelve un array que contiene la estacion en donde comienza y la estacion destino
	 */
	public String[] estacionesUsuario() {

		boolean ok = true;
		String datos[] = new String[2];
		Scanner aux = new Scanner(System.in);
		
		while (ok) {
			System.out.println("Introduzca código de la estación donde se encuentra: ");
			datos[0] = aux.next().toLowerCase();
			if(listaEstaciones.contains(datos[0]))
				ok = false;
			 else
				Interfaz.mostrarError("El código introducido es incorrecto.");
		}

		ok = true;
		
		while(ok) {
			System.out.println("Introduzca código de la estación destino: ");
			datos[1] = aux.next().toLowerCase();
			if(listaEstaciones.contains(datos[1])) {
				ok = false;
			} else
				Interfaz.mostrarError("El código introducido es incorrecto.");
		}
		
		return datos;
	}
	
	public static void mostrarResultado(String camino) {
		System.out.println(camino);
	}
	
	public static void mostrarError(String error) {
		System.out.println(error);
	}
	
	public static String recorrido (List<Tramo> tramos) {
		String recorrido = "";
		Estacion verticeA;
		Estacion verticeB;
		double tiempoCaminando = 0;
		double tiempoSubte = 0;
		double tiempoTotal = 0;
		
			for (Tramo t : tramos) {
				verticeA = t.getE1();
				verticeB = t.getE2();
				recorrido = recorrido + verticeA.getNombre() + " ---(" + t.getTiempo() + " minutos)--> " + verticeB.getNombre() + "\n";
				
				if(t.getTrasbordo() >= Constantes.VALOR_TRASBORDO) {
					
					tiempoCaminando = tiempoCaminando + t.getTiempo();
					
					recorrido = recorrido + "Linea " + verticeA.getLinea().getCodigo() + " =======> Linea " + verticeB.getLinea().getCodigo() + "\n";
				
				} else 
					tiempoSubte = tiempoSubte + t.getTiempo();		
//			}
			
			
			}
			//Sumatoria de los tiempos.
			tiempoTotal = tiempoCaminando + tiempoSubte;	
			recorrido = recorrido + "\n";
			recorrido = recorrido + "Tiempo en subte: " + tiempoSubte + " minutos" + "\n";
			recorrido = recorrido + "Tiempo caminando (trasbordos): " + tiempoCaminando + " minutos" + "\n";
			recorrido = recorrido + "Tiempo total del recorrido: " + tiempoTotal + " minutos" +"\n";
			
			return recorrido;
				
		}
	}

	

