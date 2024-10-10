package gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.border.Border;

/**
 * Frame el cual contiene la imagen de fondo de la aplicación.
 * @author Admin
 */
public class JPanelFondo extends javax.swing.JPanel {
	
	public JPanelFondo (int width, int height) {
		this.setSize(width, height);
	}
	
	public void paintComponent(Graphics g) {
		Dimension tamanio = getSize();
		ImageIcon imagenFondo = new ImageIcon(getClass().getResource("/imagen/fondo.jpg"));
		g.drawImage(imagenFondo.getImage(), 0 , 0 , tamanio.width, tamanio.height, null);
		setOpaque(false);
		super.paintComponent(g);
	}
	
	}


