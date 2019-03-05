package com.jarias.ejemplohibernate.beans;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class JBusqueda extends JPanel {
	public JTextField tfBuscar;
	public JButton btnBuscar;

	public JBusqueda() {
		setLayout(null);
		
		tfBuscar = new JTextField();
		tfBuscar.setBounds(0, 0, 160, 20);
		add(tfBuscar);
		tfBuscar.setColumns(10);
		
		btnBuscar = new JButton("Buscar");
		btnBuscar.setBounds(170, 0, 88, 20);
		btnBuscar.setBackground(Color.WHITE);
		btnBuscar.setBorder(BorderFactory.createRaisedSoftBevelBorder());
		add(btnBuscar);

	}

}
