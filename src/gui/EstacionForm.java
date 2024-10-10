package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import logica.EstacionExisteException;
import modelo.Estacion;
import modelo.Linea;
import subte.Coordinador;
import util.Validation;

/**
 * Frame que muestra el formulario para poder agregar una estación o modificar uno.
 * @author Admin
 *
 */
public class EstacionForm extends JDialog {

	private Coordinador coordinador;

	private JPanel contentPane;
	private JTextField jtfNombre;
	private JTextField jtfClave;
	private JTextField jtfLinea;


	private JLabel lblErrorNombre;
	private JLabel lblErrorCodigo;
	private JLabel lblErrorLinea;


	private JButton btnInsertar;
	private JButton btnModificar;
	private JButton btnBorrar;
	private JButton btnCancelar;
	
	JComboBox comboBoxLinea;

	/**
	 * Frame del formulario para agregar una estación.
	 * 
	 */
	public EstacionForm(List<Linea> lineas) {
		
		setBounds(100, 100, 662, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNombre.setBounds(42, 24, 107, 14);
		contentPane.add(lblNombre);

		jtfNombre = new JTextField();
		jtfNombre.setBounds(159, 24, 86, 20);
		contentPane.add(jtfNombre);
		jtfNombre.setColumns(10);

		JLabel lblClave = new JLabel("Código:");
		lblClave.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblClave.setBounds(42, 55, 107, 14);
		contentPane.add(lblClave);

		jtfClave = new JTextField();
		jtfClave.setBounds(159, 55, 86, 20);
		contentPane.add(jtfClave);
		jtfClave.setColumns(10);

		//Este es el combobox
		comboBoxLinea = new JComboBox();
		comboBoxLinea.setBounds(159, 86, 86, 20);
		comboBoxLinea.setFont(new Font("Tahoma", Font.BOLD, 11));
		for(Linea l : lineas)	
			comboBoxLinea.addItem(l);
		contentPane.add(comboBoxLinea);
		
		JLabel lblLinea = new JLabel("Línea:");
		lblLinea.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblLinea.setBounds(42, 89, 107, 14);
		contentPane.add(lblLinea);

		jtfLinea = new JTextField();
		jtfLinea.setBounds(159, 86, 86, 20);
		contentPane.add(jtfLinea);
		jtfLinea.setColumns(10);

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
		
		btnModificar.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        modificarEstacion();
		    }
		});

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

	public void accion(int accion, Estacion estacion) {
		btnInsertar.setVisible(false);
		btnModificar.setVisible(false);
		btnBorrar.setVisible(false);
		jtfNombre.setEditable(true);
		jtfClave.setEditable(true);
		//jtfLinea.setEditable(true);
		comboBoxLinea.setEnabled(true);

		if (accion == Constantes.INSERTAR) {
			btnInsertar.setVisible(true);
			limpiar();
		}

		if (accion == Constantes.MODIFICAR) {
			btnModificar.setVisible(true);
			//jtfLinea.setEditable(false);
			comboBoxLinea.setEnabled(false);
			mostrar(estacion);
		}

		if (accion == Constantes.BORRAR) {
			btnBorrar.setVisible(true);
			jtfNombre.setEditable(false);
			jtfClave.setEditable(false);
			//jtfLinea.setEditable(false);
			comboBoxLinea.setEnabled(false);
			mostrar(estacion);
		}
	}

	private void mostrar(Estacion estacion) {
		jtfNombre.setText(estacion.getNombre());
		jtfClave.setText(estacion.getKey());
		//jtfLinea.setText(estacion.getLinea().getCodigo());
		comboBoxLinea.setSelectedItem(estacion.getLinea()); // Selecciona la línea correspondiente en el comboBox


	}

	private void limpiar() {
		jtfNombre.setText("");
		jtfClave.setText("");
		//jtfLinea.setText("");
		comboBoxLinea.setSelectedItem(null);
	}
	
	private boolean validarCampos() {
	    if (jtfNombre.getText().isEmpty() || jtfClave.getText().isEmpty() || comboBoxLinea.getSelectedItem() == null) {
	        JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
	        return false;
	    }
	    return true;
	}

	private void modificarEstacion() {
	    if (!validarCampos()) {
	        return;
	    }

	    String nombre = jtfNombre.getText();
	    String clave = jtfClave.getText();
	    Linea linea = (Linea) comboBoxLinea.getSelectedItem();

	    Estacion estacion = new Estacion(clave, nombre, linea);
	    try {
	        coordinador.modificarEstacion(estacion);
	        JOptionPane.showMessageDialog(this, "Estación modificada con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
	        this.setVisible(false);
	    } catch (Exception e) {
	        JOptionPane.showMessageDialog(this, "Error al modificar la estación: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}


	/**
	 * Manejador de eventos del botón.
	 * @author Admin
	 */
	private class Handler implements ActionListener {
		public void actionPerformed(ActionEvent event) {

			if (event.getSource() == btnCancelar) {
				coordinador.cancelarEstacion();
				return;
			}
			if (event.getSource() == btnBorrar) {
				int resp = JOptionPane.showConfirmDialog(null, "Está seguro que borra este registro?", "Confirmar",
						JOptionPane.YES_NO_OPTION);
				if (JOptionPane.OK_OPTION == resp)
					coordinador.borrarEstacion(
							(new Estacion(null, null, null)));
				return;
			}

			boolean valido = true;

			lblErrorNombre.setText("");
			lblErrorCodigo.setText("");
			lblErrorLinea.setText("");


			// validar nombre
			String nombre = jtfNombre.getText().trim();
			if (nombre.isEmpty()) {
				lblErrorNombre.setText("Campo obligatorio");
				valido = false;
			} else if (nombre.matches("[A-Z][a-zA-Z]*") != true) {
				lblErrorNombre.setText("Solo letras. Primera con mayúscula");
				valido = false;
			}

			// validar clave
			String clave = jtfClave.getText().trim();
			if (clave.isEmpty()) {
				lblErrorCodigo.setText("Campo obligatorio");
				valido = false;
			}

			// validar linea
			String linea = jtfLinea.getText().trim();
			if (linea.isEmpty()) {
				lblErrorLinea.setText("Campo obligatorio");
				valido = false;
			} 
			else if (linea.matches("[A-Z][a-zA-Z]*") != true) {
				lblErrorLinea.setText("Solo letras. Y en mayúscula.");
				valido = false;
			}

			if (!valido)
				return;
			
			Linea nuevaLinea = new Linea(linea, "Linea " + linea);
			Estacion estacion = new Estacion(clave, nombre, nuevaLinea);
			if (event.getSource() == btnInsertar)
				try {
					coordinador.insertarEstacion(estacion);
				} catch (EstacionExisteException e) {
					JOptionPane.showMessageDialog(null, "Esta estacion ya existe!");
					return;
				}
			if (event.getSource() == btnModificar)
				coordinador.modificarEstacion(estacion);
		}
	}
		
	public void setCoordinador(Coordinador coordinador) {
		this.coordinador = coordinador;
	}

	
}


