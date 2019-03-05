package com.jarias.ejemplohibernate;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.jarias.ejemplohibernate.base.Arma;
import com.jarias.ejemplohibernate.base.Personaje;
import com.jarias.ejemplohibernate.beans.PanelArmas;
import com.jarias.ejemplohibernate.beans.PanelPersonajes;

public class View extends JFrame implements ActionListener, ChangeListener {

	public JPanel contentPane;
	public DefaultListModel<Personaje> mPersonajes;
	public DefaultListModel<Arma> mArmas;
	public JTabbedPane tabbedPane;
	public PanelArmas panelArmas;
	public PanelPersonajes panelPersonajes;
	public JMenuBar menuBar;
	public JMenu mnArchivo;
	public JMenuItem miExportar;
	public JMenuItem miImportar;
	public JMenu mnEditar;
	public JMenu mnOpciones;
	public JMenu mnAyuda;
	
	private Model model;
	
	public View() {
		setTitle("CRUD Hibernate");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 610, 498);
		
		model = new Model();
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		mnArchivo = new JMenu("Archivo");
		menuBar.add(mnArchivo);
		
		miExportar = new JMenuItem("Exportar");
		miExportar.addActionListener(this);
		miExportar.setActionCommand("exportar");
		mnArchivo.add(miExportar);
		
		miImportar = new JMenuItem("Importar");
		miImportar.addActionListener(this);
		miImportar.setActionCommand("importar");
		mnArchivo.add(miImportar);
		
		mnEditar = new JMenu("Editar");
		menuBar.add(mnEditar);
		
		mnOpciones = new JMenu("Opciones");
		menuBar.add(mnOpciones);
		
		mnAyuda = new JMenu("Ayuda");
		menuBar.add(mnAyuda);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		
		panelPersonajes = new PanelPersonajes(model);
		tabbedPane.addTab("Personajes", null, panelPersonajes, null);
		mArmas = new DefaultListModel<Arma>();
		mPersonajes = new DefaultListModel<Personaje>();
		
		panelArmas = new PanelArmas(model, panelPersonajes);
		tabbedPane.addTab("Armas", null, panelArmas, null);
		
		tabbedPane.addChangeListener(this);
		
		setVisible(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
			case "exportar":
				model.exportarJson();
				break;
			case "importar":
				model.importarJson();
				break;
			default:
				break;
		}
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		model = new Model();
		List<Arma> armas = model.getArmas();
		panelPersonajes.panelAnadirArma.cgArmas.inicializar(armas);
		panelPersonajes.panelAnadirArma.cgArmas.refrescar();
	}
}
