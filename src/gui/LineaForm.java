package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;


import logica.EstacionExisteException;
import logica.LineaExisteException;
import modelo.Estacion;
import modelo.Linea;
import subte.Coordinador;

public class LineaForm extends JDialog{

	private Coordinador coordinador;

	private JPanel contentPane;
	private JTextField jtfCodigo;
	private JTextField jtfNombre;
//	private JTextField jtfLinea;


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
	public LineaForm() {
		
		setBounds(100, 100, 662, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblCodigo = new JLabel("Código:");
		lblCodigo.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblCodigo.setBounds(42, 24, 107, 14);
		contentPane.add(lblCodigo);

		jtfCodigo = new JTextField();
		jtfCodigo.setBounds(159, 24, 86, 20);
		contentPane.add(jtfCodigo);
		jtfCodigo.setColumns(10);

		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNombre.setBounds(42, 55, 107, 14);
		contentPane.add(lblNombre);

		jtfNombre = new JTextField();
		jtfNombre.setBounds(159, 55, 86, 20);
		contentPane.add(jtfNombre);
		jtfNombre.setColumns(10);

		lblErrorNombre = new JLabel("");
		lblErrorNombre.setForeground(Color.RED);
		lblErrorNombre.setBounds(255, 24, 300, 14);
		contentPane.add(lblErrorNombre);

		lblErrorCodigo = new JLabel("");
		lblErrorCodigo.setForeground(Color.RED);
		lblErrorCodigo.setBounds(255, 55, 300, 14);
		contentPane.add(lblErrorCodigo);

//		lblErrorLinea = new JLabel("");
//		lblErrorLinea.setForeground(Color.RED);
//		lblErrorLinea.setBounds(255, 89, 300, 14);
//		contentPane.add(lblErrorLinea);

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

	public void accion(int accion, Linea linea) {
		btnInsertar.setVisible(false);
		btnModificar.setVisible(false);
		btnBorrar.setVisible(false);
		jtfCodigo.setEditable(true);
		jtfNombre.setEditable(true);
//		jtfLinea.setEditable(true);


		if (accion == Constantes.INSERTAR) {
			btnInsertar.setVisible(true);
			limpiar();
		}

		if (accion == Constantes.MODIFICAR) {
			btnModificar.setVisible(true);
//			jtfLinea.setEditable(false);
			mostrar(linea);
		}

		if (accion == Constantes.BORRAR) {
			btnBorrar.setVisible(true);
			jtfCodigo.setEditable(false);
			jtfNombre.setEditable(false);
//			jtfLinea.setEditable(false);
			mostrar(linea);
		}
	}

	private void mostrar(Linea linea) {
		jtfCodigo.setText(linea.getCodigo());
		jtfNombre.setText(linea.getNombre());
//		jtfLinea.setText(estacion.getLinea().getCodigo());

	}

	private void limpiar() {
		jtfCodigo.setText("");
		jtfNombre.setText("");
//		jtfLinea.setText("");
	}

	/**
	 * Manejador de eventos del botón.
	 * @author Admin
	 */
	private class Handler implements ActionListener {
		public void actionPerformed(ActionEvent event) {

			if (event.getSource() == btnCancelar) {
				coordinador.cancelarLinea();
				return;
			}
			if (event.getSource() == btnBorrar) {
				int resp = JOptionPane.showConfirmDialog(null, "Está seguro que borra este registro?", "Confirmar",
						JOptionPane.YES_NO_OPTION);
				if (JOptionPane.OK_OPTION == resp)
					coordinador.borrarLinea(
							(new Linea(null, null)));
				return;
			}

			boolean valido = true;

			lblErrorCodigo.setText("");
			lblErrorNombre.setText("");
//			lblErrorLinea.setText("");


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
			
			String codigo = jtfCodigo.getText().trim();
			if (codigo.isEmpty()) {
				lblErrorCodigo.setText("Campo obligatorio");
				valido = false;
			} else if (codigo.matches("[A-Z]*") != true) {
				lblErrorCodigo.setText("Un solo caracter. Con mayúscula.");
				valido = false;
			}


			if (!valido)
				return;
			
			Linea nuevaLinea = new Linea(codigo, nombre);
			if (event.getSource() == btnInsertar)
				try {
					coordinador.insertarLinea(nuevaLinea);
				} catch (LineaExisteException e) {
					JOptionPane.showMessageDialog(null, "Esta linea ya existe!");
					return;
				}
			if (event.getSource() == btnModificar)
				coordinador.modificarLinea(nuevaLinea);
		}
	}
		
	public void setCoordinador(Coordinador coordinador) {
		this.coordinador = coordinador;
	}
}
