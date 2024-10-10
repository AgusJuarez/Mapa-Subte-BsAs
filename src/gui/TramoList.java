package gui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

import gui.EstacionList.ButtonEditor;
import gui.EstacionList.ButtonRenderer;

import modelo.Estacion;
import modelo.Tramo;
import subte.Coordinador;

public class TramoList extends JDialog{
	private Coordinador coordinador;
	private int accion;
	private Tramo tramo;
	
	private JPanel contentPane;
	private JScrollPane scrollPane;
	private JTable tableTramos;
	private JButton btnInsertar;

	/**
	 * Create the frame.
	 */
	public TramoList() {

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

		tableTramos = new JTable();
		tableTramos.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "Estacion A", "Estacion B", "Tiempo", "Modificar", "Borrar" }) {
			boolean[] columnEditables = new boolean[] { false, false, false, true, true };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});

		tableTramos.getColumn("Modificar").setCellRenderer(new ButtonRenderer());
		tableTramos.getColumn("Modificar").setCellEditor(new ButtonEditor(new JCheckBox()));
		tableTramos.getColumn("Borrar").setCellRenderer(new ButtonRenderer());
		tableTramos.getColumn("Borrar").setCellEditor(new ButtonEditor(new JCheckBox()));
		scrollPane.setViewportView(tableTramos);

		Handler handler = new Handler();
		btnInsertar.addActionListener(handler);

		setModal(true);		
	}

	private class Handler implements ActionListener {
		public void actionPerformed(ActionEvent event) {

			TramoForm TramoForm = null;
			if (event.getSource() == btnInsertar)
				coordinador.insertarTramoForm();
		}
	}

	public void loadTable() {
		// Eliminar todas las filas
		((DefaultTableModel) tableTramos.getModel()).setRowCount(0);
		for (Tramo tramo : coordinador.listaTramos())
			if (tramo instanceof Tramo)
				addRow((Tramo) tramo);
	}

	public void addRow(Tramo tramo) {
		Object[] row = new Object[tableTramos.getModel().getColumnCount()];

		row[0] = tramo.getE1();
		row[1] = tramo.getE2();
		row[2] = tramo.getTiempo();
		row[3] = "edit";
		row[4] = "drop";
		((DefaultTableModel) tableTramos.getModel()).addRow(row);
	}

	
	private void updateRow(int row) {
		tableTramos.setValueAt(tramo.getE1(), row, 0);
		tableTramos.setValueAt(tramo.getE2(), row, 1);
		tableTramos.setValueAt(tramo.getTiempo(), row, 2);
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
				
				String id = tableTramos.getValueAt(tableTramos.getSelectedRow(), 0).toString();
				Estacion estacionA = (Estacion) tableTramos.getValueAt(tableTramos.getSelectedRow(), 0);
				Estacion estacionB = (Estacion) tableTramos.getValueAt(tableTramos.getSelectedRow(), 1);
				Tramo t = (Tramo) coordinador
						.buscarTramo(new Tramo(estacionA, estacionB, 0, 0, 0));
				if (label.equals("edit"))
					coordinador.modificarTramo(t);					
				else
					coordinador.borrarTramoForm(t);						
				}
			if (accion == Constantes.BORRAR)
				isDeleteRow = true;
			if (accion == Constantes.MODIFICAR)
				isUpdateRow = true;
			isPushed = false;
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

	public void setTramo(Tramo tramo) {
		this.tramo = tramo;
	}

}
