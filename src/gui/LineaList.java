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
import modelo.Estacion;
import modelo.Linea;
import subte.Coordinador;

public class LineaList extends JDialog{

	private Coordinador coordinador;
	private int accion;
	private Linea linea;
	
	private JPanel contentPane;
	private JScrollPane scrollPane;
	private JTable tableLinea;
	private JButton btnInsertar;

	/**
	 * Create the frame.
	 */
	public LineaList() {
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

		tableLinea = new JTable();
		tableLinea.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "Código", "Nombre", "Modificar", "Borrar" }) {
			boolean[] columnEditables = new boolean[] { false, false, true, true };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});

		tableLinea.getColumn("Modificar").setCellRenderer(new ButtonRenderer());
		tableLinea.getColumn("Modificar").setCellEditor(new ButtonEditor(new JCheckBox()));
		tableLinea.getColumn("Borrar").setCellRenderer(new ButtonRenderer());
		tableLinea.getColumn("Borrar").setCellEditor(new ButtonEditor(new JCheckBox()));
		scrollPane.setViewportView(tableLinea);

		Handler handler = new Handler();
		btnInsertar.addActionListener(handler);

		setModal(true);		
	}

	private class Handler implements ActionListener {
		public void actionPerformed(ActionEvent event) {

			LineaForm lineaForm = null;
			if (event.getSource() == btnInsertar)
				coordinador.insertarLineaForm();
		}
	}

	public void loadTable() {
		// Eliminar todas las filas
		((DefaultTableModel) tableLinea.getModel()).setRowCount(0);
		for (Linea l : coordinador.listaLinea())
			if (l instanceof Linea)
				addRow((Linea) l);
	}

	public void addRow(Linea l) {
		Object[] row = new Object[tableLinea.getModel().getColumnCount()];

		row[0] = l.getCodigo();
		row[1] = l.getNombre();
		row[2] = "edit";
		row[3] = "drop";
		((DefaultTableModel) tableLinea.getModel()).addRow(row);
	}

	
	private void updateRow(int row) {
		tableLinea.setValueAt(linea.getCodigo(), row, 0);
		tableLinea.setValueAt(linea.getNombre(), row, 1);
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
				String id = tableLinea.getValueAt(tableLinea.getSelectedRow(), 0).toString();
				Linea l = (Linea) coordinador
						.buscarLinea(new Linea(id, null));
				if (label.equals("edit"))
					coordinador.modificarLinea(l);					
				else
					coordinador.borrarLineaForm(l);						
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

	public void setLinea(Linea linea) {
		this.linea = linea;
	}

}
