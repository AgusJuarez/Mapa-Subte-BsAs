package gui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

import javax.swing.DefaultCellEditor;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import modelo.Estacion;
import subte.Coordinador;



/**
 * Frame de la lista que muestra las estaciones.
 * @author Admin
 */
public class EstacionList extends JDialog {

	private Coordinador coordinador;
	private int accion;
	private Estacion estacion;
	
	private JPanel contentPane;
	private JScrollPane scrollPane;
	private JTable tableEstacion;
	private JButton btnInsertar;

	/**
	 * Create the frame.
	 */
	public EstacionList() {
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 756, 366);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		btnInsertar = new JButton("Insertar");
		btnInsertar.setBounds(38, 280, 114, 32);
		contentPane.add(btnInsertar);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(38, 25, 673, 244);
		contentPane.add(scrollPane);

		tableEstacion = new JTable();
		tableEstacion.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "Código", "Nombre", "Línea", "Modificar", "Borrar" }) {
			boolean[] columnEditables = new boolean[] { false, false, false, true, true };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});

		tableEstacion.getColumn("Modificar").setCellRenderer(new ButtonRenderer());
		tableEstacion.getColumn("Modificar").setCellEditor(new ButtonEditor(new JCheckBox()));
		tableEstacion.getColumn("Borrar").setCellRenderer(new ButtonRenderer());
		tableEstacion.getColumn("Borrar").setCellEditor(new ButtonEditor(new JCheckBox()));
		scrollPane.setViewportView(tableEstacion);

		Handler handler = new Handler();
		btnInsertar.addActionListener(handler);

		setModal(true);		
	}

	private class Handler implements ActionListener {
		public void actionPerformed(ActionEvent event) {

			EstacionForm estacionForm = null;
			if (event.getSource() == btnInsertar)
				coordinador.insertarEstacionForm();
		}
	}

	public void loadTable() {
		// Eliminar todas las filas
		((DefaultTableModel) tableEstacion.getModel()).setRowCount(0);
		for (Estacion est : coordinador.listaEstacion())
			if (est instanceof Estacion)
				addRow((Estacion) est);
	}

	public void addRow(Estacion est) {
		Object[] row = new Object[tableEstacion.getModel().getColumnCount()];

		row[0] = est.getKey();
		row[1] = est.getNombre();
		row[2] = est.getLinea().getCodigo();
		row[3] = "edit";
		row[4] = "drop";
		((DefaultTableModel) tableEstacion.getModel()).addRow(row);
	}

	
	private void updateRow(int row) {
		tableEstacion.setValueAt(estacion.getKey(), row, 0);
		tableEstacion.setValueAt(estacion.getNombre(), row, 1);
		tableEstacion.setValueAt(estacion.getLinea(), row, 2);
	}

	class ButtonRenderer extends JButton implements TableCellRenderer {

		public ButtonRenderer() {
			setOpaque(true);
		}

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			if (isSelected) {
				setForeground(table.getSelectionForeground());
				setBackground(table.getSelectionBackground());
			} else {
				setForeground(table.getForeground());
				setBackground(UIManager.getColor("Button.background"));
			}
			// setText((value == null) ? "" : value.toString());
			Icon icon = null;
			if (value.toString().equals("edit"))
				icon = new ImageIcon(getClass().getResource("/imagen/b_edit.png"));
			if (value.toString().equals("drop"))
				icon = new ImageIcon(getClass().getResource("/imagen/b_drop.png"));
			setIcon(icon);
			return this;
		}
	}

	class ButtonEditor extends DefaultCellEditor {

		protected JButton button;
		private String label;
		private boolean isPushed;
		private JTable table;
		private boolean isDeleteRow = false;
		private boolean isUpdateRow = false;

		public ButtonEditor(JCheckBox checkBox) {
			super(checkBox);
			button = new JButton();
			button.setOpaque(true);
			button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					fireEditingStopped();
				}
			});
		}

		@Override
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
				int column) {

			if (isSelected) {
				button.setForeground(table.getSelectionForeground());
				button.setBackground(table.getSelectionBackground());
			} else {
				button.setForeground(table.getForeground());
				button.setBackground(table.getBackground());
			}

			label = (value == null) ? "" : value.toString();
			Icon icon = null;
			if (value.toString().equals("edit"))
				icon = new ImageIcon(getClass().getResource("/imagen/b_edit.png"));
			if (value.toString().equals("drop"))
				icon = new ImageIcon(getClass().getResource("/imagen/b_drop.png"));
			button.setIcon(icon);
			isPushed = true;
			this.table = table;
			isDeleteRow = false;
			isUpdateRow = false;
			return button;
		}

		@Override
		public Object getCellEditorValue() {
			if (isPushed) {				
				String id = tableEstacion.getValueAt(tableEstacion.getSelectedRow(), 0).toString();
				Estacion est = (Estacion) coordinador
						.buscarEstacion(new Estacion(id, null, null));
				if (label.equals("edit"))
					coordinador.modificarEstacionForm(est);					
				else
					coordinador.borrarEstacionForm(est);						
				}
			/*if (accion == Constantes.BORRAR)
				isDeleteRow = true;
			if (accion == Constantes.MODIFICAR)
				isUpdateRow = true;
			isPushed = false;*/
			return new String(label);
		}

		@Override
		public boolean stopCellEditing() {
			isPushed = false;
			return super.stopCellEditing();
		}

		protected void fireEditingStopped() {
			super.fireEditingStopped();

			DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
			if (isDeleteRow)
				tableModel.removeRow(table.getSelectedRow());

			if (isUpdateRow) {
				updateRow(table.getSelectedRow());
			}

		}
	}

	public void setCoordinador(Coordinador coordinador) {
		this.coordinador = coordinador;
	}

	public void setAccion(int accion) {
		this.accion = accion;
	}

	public void setEstacion(Estacion estacion) {
		this.estacion = estacion;
	}

		
}
