package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;


import logica.EstacionExisteException;
import logica.TramoExisteException;
import modelo.Estacion;
import modelo.Tramo;
import subte.Coordinador;

public class TramoForm extends JDialog {
	
	private Coordinador coordinador;
	private final static Random generator = new Random();
	private JPanel contentPane;
	private JTextField jtfEstacionA;
	private JTextField jtfEstacionB;
	private JTextField jtfTiempo;


	private JLabel lblErrorNombre;
	private JLabel lblErrorCodigo;
	private JLabel lblErrorLinea;


	private JButton btnInsertar;
	private JButton btnModificar;
	private JButton btnBorrar;
	private JButton btnCancelar;
	
	JComboBox comboBoxEstacionA;
	JComboBox comboBoxEstacionB;

	/**
	 * Frame del formulario para agregar una estación.
	 * 
	 */
	public TramoForm(List<Estacion> estaciones)  {
		
		setBounds(100, 100, 662, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblEstacionA = new JLabel("Estacion A: ");
		lblEstacionA.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblEstacionA.setBounds(42, 24, 107, 14);
		contentPane.add(lblEstacionA);

		//Este es el combobox
		comboBoxEstacionA = new JComboBox();
		comboBoxEstacionA.setBounds(159, 24, 200, 20);
		comboBoxEstacionA.setFont(new Font("Tahoma", Font.BOLD, 11));
		for(Estacion e : estaciones)	
			comboBoxEstacionA.addItem(e);
		contentPane.add(comboBoxEstacionA);
		
		jtfEstacionA = new JTextField();
		jtfEstacionA.setBounds(159, 24, 86, 20);
		contentPane.add(jtfEstacionA);
		jtfEstacionA.setColumns(10);

		JLabel lblEstacionB = new JLabel("Estacion B:");
		lblEstacionB.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblEstacionB.setBounds(42, 55, 107, 14);
		contentPane.add(lblEstacionB);

		//Este es el combobox
		comboBoxEstacionB = new JComboBox();
		comboBoxEstacionB.setBounds(159, 55, 200, 20);
		comboBoxEstacionB.setFont(new Font("Tahoma", Font.BOLD, 11));
		for(Estacion e : estaciones)	
			comboBoxEstacionB.addItem(e);
		contentPane.add(comboBoxEstacionB);
		
		jtfEstacionB = new JTextField();
		jtfEstacionB.setBounds(159, 55, 86, 20);
		contentPane.add(jtfEstacionB);
		jtfEstacionB.setColumns(10);
		
		JLabel lblTiempo = new JLabel("Tiempo:");
		lblTiempo.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTiempo.setBounds(42, 89, 107, 14);
		contentPane.add(lblTiempo);

		jtfTiempo = new JTextField();
		jtfTiempo.setBounds(159, 86, 86, 20);
		contentPane.add(jtfTiempo);
		jtfTiempo.setColumns(10);

		lblErrorNombre = new JLabel("");
		lblErrorNombre.setForeground(Color.RED);
		lblErrorNombre.setBounds(255, 24, 300, 14);
		contentPane.add(lblErrorNombre);

		lblErrorCodigo = new JLabel("");
		lblErrorCodigo.setForeground(Color.RED);
		lblErrorCodigo.setBounds(255, 55, 300, 14);
		contentPane.add(lblErrorCodigo);

		lblErrorLinea = new JLabel("");
		lblErrorLinea.setForeground(Color.RED);
		lblErrorLinea.setBounds(255, 89, 300, 14);
		contentPane.add(lblErrorLinea);

		Handler handler = new Handler();

		btnInsertar = new JButton("Insertar");
		btnInsertar.setBounds(85, 202, 114, 32);
		contentPane.add(btnInsertar);
		btnInsertar.addActionListener(handler);

		btnModificar = new JButton("Modificar");
		btnModificar.setBounds(85, 202, 114, 32);
		contentPane.add(btnModificar);
		btnModificar.addActionListener(handler);

		btnBorrar = new JButton("Borrar");
		btnBorrar.setBounds(85, 202, 114, 32);
		contentPane.add(btnBorrar);
		btnBorrar.addActionListener(handler);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(225, 202, 114, 32);
		contentPane.add(btnCancelar);
		btnCancelar.addActionListener(handler);

		setModal(true);
	}

	public void accion(int accion, Tramo tramo) {
		btnInsertar.setVisible(false);
		btnModificar.setVisible(false);
		btnBorrar.setVisible(false);
		jtfEstacionA.setEditable(true);
		jtfEstacionB.setEditable(true);
		jtfTiempo.setEditable(true);


		if (accion == Constantes.INSERTAR) {
			btnInsertar.setVisible(true);
			limpiar();
		}

		if (accion == Constantes.MODIFICAR) {
			btnModificar.setVisible(true);
			jtfTiempo.setEditable(false);
			mostrar(tramo);
		}

		if (accion == Constantes.BORRAR) {
			btnBorrar.setVisible(true);
			jtfEstacionA.setEditable(false);
			jtfEstacionB.setEditable(false);
			jtfTiempo.setEditable(false);
			mostrar(tramo);
		}
	}

	private void mostrar(Tramo tramo) {
		jtfEstacionA.setText(tramo.getE1().toString());
		jtfEstacionB.setText(tramo.getE2().toString());
		jtfTiempo.setText(Double.toString(tramo.getTiempo()));

	}

	private void limpiar() {
		jtfEstacionA.setText("");
		jtfEstacionB.setText("");
		jtfTiempo.setText("");
	}

	/**
	 * Manejador de eventos del botón.
	 * @author Admin
	 */
	private class Handler implements ActionListener {
		public void actionPerformed(ActionEvent event) {

			if (event.getSource() == btnCancelar) {
				coordinador.cancelarTramo();
				return;
			}
			if (event.getSource() == btnBorrar) {
				int resp = JOptionPane.showConfirmDialog(null, "Está seguro que borra este registro?", "Confirmar",
						JOptionPane.YES_NO_OPTION);
				if (JOptionPane.OK_OPTION == resp)
					coordinador.borrarTramo(
							(new Tramo(null, null, 0, 0, 0)));
				return;
			}
			
			Estacion estacionA = (Estacion) comboBoxEstacionA.getSelectedItem();
			Estacion estacionB = (Estacion) comboBoxEstacionB.getSelectedItem();
			int tiempo = Integer.parseInt(jtfTiempo.getText());
			
			if(estacionA.equals(estacionB))
				tiempo = 0;
			
			int trasbordo = 0;
			if(estacionA.getLinea().equals(estacionB.getLinea()))
				trasbordo = Constantes.VALOR_TRASBORDO;
			
			int congestion = generator.nextInt(Constantes.MAX_CONGESTION) + 1;
			Tramo nuevoTramo = new Tramo(estacionA, estacionB, tiempo, congestion, trasbordo);
			
			if (event.getSource() == btnInsertar)
				try {
					coordinador.insertarTramo(nuevoTramo);
				} catch (TramoExisteException e) {
					JOptionPane.showMessageDialog(null, "Esta estacion ya existe!");
					return;
				}
			if (event.getSource() == btnModificar)
				coordinador.modificarTramo(nuevoTramo);
		}
	}
		
	public void setCoordinador(Coordinador coordinador) {
		this.coordinador = coordinador;
	}
}
