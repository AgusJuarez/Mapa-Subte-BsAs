package subte;

import java.util.List;

import gui.AplicacionFrame;
import gui.Constantes;
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
import logica.LineaExisteException;
import logica.TramoExisteException;
import modelo.Estacion;
import modelo.Linea;
import modelo.Tramo;

/**
 * Clase coordinadora que es intermediario entre la GUI y la clase de Lógica.
 * @author Admin
 *
 */
public class Coordinador {
	
	private Calculo calculo;
	private AplicacionFrame aplicacion;
	private EstacionList estacionList;
	private EstacionForm estacionForm;
	private LineaList lineaList;
	private LineaForm lineaForm;
	private TramoList tramoList;
	private TramoForm tramoForm;
	private ABMEstacion abmEstaciones;
	private ABMLinea abmLineas;
	private ABMTramo abmTramos;

	//Getters and Setters;
	public Calculo getCalculo() {
		return calculo;
	}

	public void setCalculo(Calculo calculo) {
		this.calculo = calculo;
	}

	public AplicacionFrame getAplicacion() {
		return aplicacion;
	}

	public void setAplicacion(AplicacionFrame aplicacion) {
		this.aplicacion = aplicacion;
	}

	public EstacionList getEstacionList() {
		return estacionList;
	}

	public void setEstacionList(EstacionList estacionList) {
		this.estacionList = estacionList;
	}

	public EstacionForm getEstacionForm() {
		return estacionForm;
	}

	public void setEstacionForm(EstacionForm estacionForm) {
		this.estacionForm = estacionForm;
	}
	
	public LineaList getLineaList() {
		return lineaList;
	}
	public void setLineaList(LineaList lineaList) {
		this.lineaList = lineaList;
	}
	public LineaForm getLineaForm() {
		return lineaForm;
	}
	public void setLineaForm(LineaForm lineaForm) {
		this.lineaForm = lineaForm;
	}
	
	public TramoForm getTramoForm() {
		return tramoForm;
	}
	public void setTramoForm(TramoForm tramoForm) {
		this.tramoForm = tramoForm;
	}
	
	public TramoList getTramoList() {
		return tramoList;
	}
	public void setTramoList(TramoList tramoList) {
		this.tramoList = tramoList;
	}
	
	public ABMEstacion getABMEstaciones() {
		return abmEstaciones;
	}

	public void setABMEstaciones(ABMEstacion abmEstaciones) {
		this.abmEstaciones = abmEstaciones;
	}

	public ABMLinea getABMLineas() {
		return abmLineas;
	}

	public void setABMLineas(ABMLinea abmLineas) {
		this.abmLineas = abmLineas;
	}

	public ABMTramo getABMTramos() {
		return abmTramos;
	}

	public void setABMTramos(ABMTramo abmTramos) {
		this.abmTramos = abmTramos;
	}

	//Metodos que involucran a estaciones.
	public void mostrarEstacionList() {
		estacionList.loadTable();
		estacionList.setVisible(true);
	}

	public void insertarEstacionForm() {
		estacionForm.accion(Constantes.INSERTAR, null);
		estacionForm.setVisible(true);
	}
	
	public void modificarEstacionForm(Estacion estacion) {
		estacionForm.accion(Constantes.MODIFICAR, estacion);
		estacionForm.setVisible(true);
	}
	
	public void borrarEstacion(Estacion estacion) {
		estacionForm.accion(Constantes.BORRAR, estacion);
		estacionForm.setVisible(true);
	}
	
	public Estacion buscarEstacion(Estacion estacion) {
		return abmEstaciones.buscarEstacion(estacion);
	}
	
	public List<Estacion> listaEstacion() {
		return abmEstaciones.getEstaciones();
	}
	
	public void cancelarEstacion() {
		estacionForm.setVisible(false);
	}
	
	
	public void insertarEstacion(Estacion estacion) throws EstacionExisteException {
		abmEstaciones.agregarEstacion(estacion);
		estacionForm.setVisible(false);
		estacionList.addRow(estacion);

	}
	
	public void modificarEstacion(Estacion estacion) {
		abmEstaciones.modificarEstacion(estacion);
		estacionList.setAccion(Constantes.MODIFICAR);
		estacionList.setEstacion(estacion);
		estacionForm.setVisible(false);
	}
	
	public void borrarEstacionForm(Estacion estacion) {
		abmEstaciones.borrarEstacion(estacion);
		estacionList.setAccion(Constantes.BORRAR);
		estacionForm.setVisible(false);
	}
	
	//Metodos que involucran a las lineas
	public void mostrarLineaList() {
		lineaList.loadTable();
		lineaList.setVisible(true);
	}
	
	public void cancelarLinea() {
		lineaForm.setVisible(false);	
	}
	public void borrarLinea(Linea linea) {
		lineaForm.accion(Constantes.BORRAR, linea);
		lineaForm.setVisible(true);
	}
	
	public void insertarLinea(Linea linea) throws LineaExisteException {
		abmLineas.agregarLinea(linea);
		lineaForm.setVisible(false);
		lineaList.addRow(linea);
	}
	
	public void modificarLinea(Linea linea) {
		abmLineas.modificarLinea(linea);
		lineaList.setAccion(Constantes.MODIFICAR);
		lineaList.setLinea(linea);
		lineaForm.setVisible(false);
	}
	
	public void insertarLineaForm() {
		lineaForm.accion(Constantes.INSERTAR, null);
		lineaForm.setVisible(true);
	}
	
	public List<Linea> listaLinea() {
		return abmLineas.getLineas();
	}
	
	public Linea buscarLinea(Linea linea) {
		return abmLineas.buscarLinea(linea);
	}
	
	public void borrarLineaForm(Linea linea) {
		abmLineas.borrarLinea(linea);
		lineaList.setAccion(Constantes.BORRAR);
		lineaForm.setVisible(false);
	}
	
	//Metodos que involucran a los tramos.
	public List<Tramo> listaTramos() {
		return abmTramos.getTramos();
	}
	
	public Tramo buscarTramo(Tramo tramo) {
		return abmTramos.buscarTramo(tramo);
	}
	
	public void modificarTramo(Tramo tramo) {
		abmTramos.modificarTramo(tramo);
		tramoList.setAccion(Constantes.MODIFICAR);
		tramoList.setTramo(tramo);
		tramoForm.setVisible(false);
	}
	
	public void insertarTramoForm() {
		tramoForm.accion(Constantes.INSERTAR, null);
		tramoForm.setVisible(true);
	}
	
	public void borrarTramoForm(Tramo tramo) {
		abmTramos.borrarTramo(tramo);
		tramoList.setAccion(Constantes.BORRAR);
		tramoForm.setVisible(false);
	}
	
	public void cancelarTramo() {
		tramoForm.setVisible(false);	
	}
	
	public void borrarTramo(Tramo tramo) {
		tramoForm.accion(Constantes.BORRAR, tramo);
		tramoForm.setVisible(true);
	}
	
	public void insertarTramo(Tramo tramo) throws TramoExisteException {
		abmTramos.agregarTramo(tramo);
		tramoForm.setVisible(false);
		tramoList.addRow(tramo);
	}
	
	public void mostrarTramoList() {
		tramoList.loadTable();
		tramoList.setVisible(true);
	}
	
	
	//Metodos que interactuan con la interfaz.
	public String caminoCorto(String[] estaciones) {
		return Interfaz.recorrido(calculo.caminoCorto(estaciones));	
	}
	
	public String menosTrasbordo(String[] estaciones) {
		return Interfaz.recorrido(calculo.menosTrasbordos(estaciones));
	}
	
	public String menosCongestion(String[] estaciones) {
		return Interfaz.recorrido(calculo.menosCongestion(estaciones));	
	}
}
