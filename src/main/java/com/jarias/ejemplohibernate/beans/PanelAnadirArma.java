package com.jarias.ejemplohibernate.beans;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.jarias.ejemplohibernate.Model;
import com.jarias.ejemplohibernate.base.Arma;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import java.awt.Color;

public class PanelAnadirArma extends JPanel implements ActionListener {
	public JPanel panel;
	public JComboGenerico<Arma> cgArmas;
	public JScrollPane scrollPane;
	public JList<Arma> lArmas;
	public DefaultListModel<Arma> modeloLista;
	public JButton btnAnadir;

	public PanelAnadirArma() {
		setLayout(null);
		
		panel = new JPanel();
		panel.setBounds(0, 0, 450, 1);
		add(panel);
		
		cgArmas = new JComboGenerico<>();
		cgArmas.setPreferredSize(new Dimension(100, 20));
		panel.add(cgArmas);
		panel.setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 39, 202, 93);
		add(scrollPane);
		
		lArmas = new JList<>();
		scrollPane.setViewportView(lArmas);
		modeloLista = new DefaultListModel<>();
		lArmas.setModel(modeloLista);
		
		cgArmas = new JComboGenerico<>();
		cgArmas.setBounds(0, 11, 154, 24);
		add(cgArmas);
		
		btnAnadir = new JButton("+");
		btnAnadir.setBackground(Color.WHITE);
		btnAnadir.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnAnadir.setBounds(156, 12, 46, 23);
		btnAnadir.setActionCommand("anadir");
		add(btnAnadir);

		inicializar();
	}
	
	private void inicializar() {
		Model model = new Model();
		List<Arma> armas = model.getArmasLibres();
		cgArmas.inicializar(armas);
		
		btnAnadir.addActionListener(this);
	}
	
	public List<Arma> getListadoArmas() {
		return Collections.list(modeloLista.elements());
	}
	
	public void anadirArmas(List<Arma> armas) {
		modeloLista.removeAllElements();
		for (Arma arma : armas)
			modeloLista.addElement(arma);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
			case "anadir":
				Arma armaSeleccionada = cgArmas.getDatoSeleccionado();
				if (armaSeleccionada == null)
					return;
				
				if (modeloLista.contains(armaSeleccionada))
					return;
				
				modeloLista.addElement(armaSeleccionada);
				break;
			default:
				break;
		}
	}
}
