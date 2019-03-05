package com.jarias.ejemplohibernate.beans;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.JLabel;

public class JEstado extends JPanel {

	public JLabel lblEstado;
	
	public JEstado() {
		
		setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		lblEstado = new JLabel("");
		add(lblEstado);

	}

	public void setMensajeConfirmacion(String mensaje) {
		
		lblEstado.setForeground(Color.BLACK);
		lblEstado.setText(mensaje);
	}
	
	public void setMensajeError(String mensaje) {
		
		lblEstado.setForeground(Color.RED);
		lblEstado.setText(mensaje);
	}
}
