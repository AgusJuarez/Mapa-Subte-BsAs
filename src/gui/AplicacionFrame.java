package gui;


import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.FileNotFoundException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import logica.Calculo;
import modelo.Estacion;
import net.datastructures.TreeMap;
import subte.Coordinador;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTextArea;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import java.awt.Font;
import java.awt.Color;

/**
 * Frame que contiene la aplicación.
 * @author Admin
 *
 */
public class AplicacionFrame extends JFrame {

	private Coordinador coordinador;
	private JLabel lblOrigen;
	private JLabel lblEstacionOrigen;
	private JComboBox comboBoxEstacionOrigen;
	private JButton btnCalcular;
	private JComboBox comboBoxEstacionDestino;
	private JLabel lblEstacionDestino;
	private JLabel lblDestino;
	private JTextArea textCamino;
	private JLabel lblOpcionUsuario;
	private JRadioButton rdbtnCaminoCorto;
	private JRadioButton rdbtnMenosTrasbordo;
	private JRadioButton rdbtnMenosCongestion;
	private JScrollPane scroll;
	private JLabel lblImagen;
	private JMenuItem mntmNewMenuItem;
	private JMenuItem mntmNewMenuItem2;
	private JMenuItem mntmNewMenuItem3;
	


	/**
	 * Create the frame.
	 * @throws FileNotFoundException 
	 */
	public AplicacionFrame(TreeMap<String, Estacion> estaciones, Calculo logica) throws FileNotFoundException {
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu_1 = new JMenu("Subte");
		menuBar.add(mnNewMenu_1);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Salir");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 System.exit(NORMAL);
			}
		});
		mnNewMenu_1.add(mntmNewMenuItem_1);

		JMenu mnNewMenu = new JMenu("Estaciones");
		menuBar.add(mnNewMenu);
		JMenuItemHandler handlerJMenu = new JMenuItemHandler();
		mntmNewMenuItem = new JMenuItem("Estacion");
		mntmNewMenuItem.addActionListener(handlerJMenu);

		mnNewMenu.add(mntmNewMenuItem);
		
		mntmNewMenuItem2 = new JMenuItem("Linea");
		mntmNewMenuItem2.addActionListener(handlerJMenu);

		mnNewMenu.add(mntmNewMenuItem2);
		
		mntmNewMenuItem3 = new JMenuItem("Tramo");
		mntmNewMenuItem3.addActionListener(handlerJMenu);

		mnNewMenu.add(mntmNewMenuItem3);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 686, 435);
		JPanelFondo fondo = new JPanelFondo(getSize().width, getSize().height);
		fondo.setBorder(new EmptyBorder(5, 5, 5, 5));
		fondo.setForeground(new Color(0, 0, 0));
		setContentPane(fondo);
		
		setSize(679, 495);
		setTitle("Aplicacion Subte");
		setLocationRelativeTo(null);
		setResizable(false);
		getContentPane().setLayout(null);
		
		fondo.setLayout(null);
		
		//Creacion de contenido de la aplicacion.
		lblOrigen = new JLabel("Origen");
		lblOrigen.setFont(new Font("Verdana", Font.BOLD, 12));
		lblOrigen.setForeground(Color.WHITE);
		lblOrigen.setBounds(439, 11, 70, 14);
		fondo.add(lblOrigen);
		
		lblEstacionOrigen = new JLabel("Estaci\u00F3n");
		lblEstacionOrigen.setFont(new Font("Verdana", Font.BOLD, 12));
		lblEstacionOrigen.setForeground(Color.WHITE);
		lblEstacionOrigen.setBounds(360, 36, 70, 14);
		fondo.add(lblEstacionOrigen);
		
		comboBoxEstacionOrigen = new JComboBox();
		comboBoxEstacionOrigen.setBounds(439, 32, 174, 22);
		for(net.datastructures.Entry<String, Estacion> e : estaciones.entrySet())	
			comboBoxEstacionOrigen.addItem(e.getValue());
		fondo.add(comboBoxEstacionOrigen);
		
		lblDestino = new JLabel("Destino");
		lblDestino.setFont(new Font("Verdana", Font.BOLD, 12));
		lblDestino.setForeground(Color.WHITE);
		lblDestino.setBounds(439, 75, 80, 14);
		fondo.add(lblDestino);
		
		lblEstacionDestino = new JLabel("Estaci\u00F3n");
		lblEstacionDestino.setFont(new Font("Verdana", Font.BOLD, 12));
		lblEstacionDestino.setForeground(Color.WHITE);
		lblEstacionDestino.setBounds(360, 101, 70, 14);
		fondo.add(lblEstacionDestino);
		
		comboBoxEstacionDestino = new JComboBox();
		comboBoxEstacionDestino.setBounds(439, 95, 174, 22);
		for(net.datastructures.Entry<String, Estacion> e : estaciones.entrySet())	
			comboBoxEstacionDestino.addItem(e.getValue());
		fondo.add(comboBoxEstacionDestino);
		
		btnCalcular = new JButton("Calcular");
		btnCalcular.setBounds(460, 140, 89, 23);

		fondo.add(btnCalcular);
		
		textCamino = new JTextArea();
		textCamino.setEditable(false);
		scroll = new JScrollPane(textCamino);
		scroll.setBounds(350, 175, 300, 243);
		scroll.setPreferredSize(new Dimension(200, 250));
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        fondo.add(scroll);
		
		rdbtnCaminoCorto = new JRadioButton("Camino corto");
		rdbtnCaminoCorto.setBounds(103, 32, 133, 23);
		fondo.add(rdbtnCaminoCorto);
		
		rdbtnMenosTrasbordo = new JRadioButton("Menos trasbordos");
		rdbtnMenosTrasbordo.setBounds(103, 66, 133, 23);
		fondo.add(rdbtnMenosTrasbordo);
		
		rdbtnMenosCongestion = new JRadioButton("Menos congestion");
		rdbtnMenosCongestion.setBounds(103, 97, 133, 23);
		fondo.add(rdbtnMenosCongestion);
		
		lblOpcionUsuario = new JLabel("Elija una opci\u00F3n");
		lblOpcionUsuario.setForeground(Color.WHITE);
		lblOpcionUsuario.setBackground(Color.WHITE);
		lblOpcionUsuario.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblOpcionUsuario.setBounds(118, 10, 106, 14);
		fondo.add(lblOpcionUsuario);
		
		
		lblImagen = new JLabel();
		lblImagen.setBounds(10, 144, 330, 274);
		ImageIcon ico = new ImageIcon(getClass().getResource("/imagen/imagen-subte2.png"));
		ImageIcon img = new ImageIcon(ico.getImage().getScaledInstance(lblImagen.getWidth(), lblImagen.getHeight(), Image.SCALE_SMOOTH));
		lblImagen.setIcon(img);
		fondo.add(lblImagen);
		
		
		ButtonHandler handler = new ButtonHandler();
	    btnCalcular.addActionListener(handler);
	    
	    RadioButtonHandler rdbtnHandler = new RadioButtonHandler();
	    rdbtnCaminoCorto.addItemListener(rdbtnHandler);
	    rdbtnMenosTrasbordo.addItemListener(rdbtnHandler);
	    rdbtnMenosCongestion.addItemListener(rdbtnHandler);
	    
	}
	// private inner class for event handling
	 /**
	 * Manejador de eventos del botón.
	 * @author Admin
	 *
	 */
	private class ButtonHandler implements ActionListener 
	   {
	      public void actionPerformed( ActionEvent event )
	      {
	    	  String[] estaciones = new String[2];
	    	  Estacion e;
	    	  if(rdbtnCaminoCorto.isSelected()) {
	    		  e = (Estacion)comboBoxEstacionOrigen.getSelectedItem();
	    		  estaciones[0] = e.getKey();
	    		  e = (Estacion)comboBoxEstacionDestino.getSelectedItem();
	    		  estaciones[1] = e.getKey();
	    		  textCamino.setText(coordinador.caminoCorto(estaciones));
	          }
	    	  if(rdbtnMenosTrasbordo.isSelected()) {	    		  
	    		  e = (Estacion)comboBoxEstacionOrigen.getSelectedItem();
	    		  estaciones[0] = e.getKey();
	    		  e = (Estacion)comboBoxEstacionDestino.getSelectedItem();
	    		  estaciones[1] = e.getKey();
	    		  textCamino.setText(coordinador.menosTrasbordo(estaciones));
	          }	    	  
	    	  if(rdbtnMenosCongestion.isSelected()) {	    		  
	    		  e = (Estacion)comboBoxEstacionOrigen.getSelectedItem();
	    		  estaciones[0] = e.getKey();
	    		  e = (Estacion)comboBoxEstacionDestino.getSelectedItem();
	    		  estaciones[1] = e.getKey();
	    		  textCamino.setText(coordinador.menosCongestion(estaciones));
	          }
	    	  
	    	  if((rdbtnCaminoCorto.isSelected() == false) && (rdbtnMenosTrasbordo.isSelected() == false) && (rdbtnMenosCongestion.isSelected() == false))
	    		  JOptionPane.showMessageDialog(null,"No se ha seleccionado ninguna opción","Error: Opcion sin elegir", JOptionPane.ERROR_MESSAGE);
	   } 
	  }
	   	   
	/**
	 * Manejador de eventos del RadioButton.
	 * 
	 * @author Admin
	 *
	 */
	private class RadioButtonHandler implements ItemListener
	    {

			@Override
			public void itemStateChanged(ItemEvent event) {
				if(event.getSource() == rdbtnCaminoCorto) {
					rdbtnMenosTrasbordo.setSelected(false);
					rdbtnMenosCongestion.setSelected(false);
				}
				
				if(event.getSource() == rdbtnMenosTrasbordo) {
					rdbtnCaminoCorto.setSelected(false);
					rdbtnMenosCongestion.setSelected(false);
				}
				
				if(event.getSource() == rdbtnMenosCongestion) {
					rdbtnCaminoCorto.setSelected(false);
					rdbtnMenosTrasbordo.setSelected(false);
				}
				
			}
	    }
	
	private class JMenuItemHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			if (arg0.getSource() == mntmNewMenuItem) {
				coordinador.mostrarEstacionList();
			}
			
			if (arg0.getSource() == mntmNewMenuItem2) {
				coordinador.mostrarLineaList();
			}
			
			if (arg0.getSource() == mntmNewMenuItem3) {
				coordinador.mostrarTramoList();
			}
			
			
		}
		
	}
	   public void setCoordinador(Coordinador coordinador) {
			this.coordinador = coordinador;
		}
}
