package com.jarias.ejemplohibernate;

import java.util.List;

import com.jarias.ejemplohibernate.base.Personaje;
import com.jarias.ejemplohibernate.beans.Login;

public class Controller {
	private View view;
	private Model model;
	
	public Controller(View view, Model model) {
		this.view = view;
		this.model = model;
				
		iniciarSesion();
		cargarDatos();
	}
	
	private void iniciarSesion() {
		boolean autenticado = false;
		
		do {
			Login login = new Login();
			String usuario = login.getUsuario();
			String contrasena = login.getContrasena();
			
			autenticado = model.iniciarSesion(usuario, contrasena);
			if(!autenticado)
				continue;
		}while(!autenticado);
	}
	
	private void cargarDatos() {
		List<Personaje> personajes = model.getPersonajes();
		view.mPersonajes.removeAllElements();
		for (Personaje personaje : personajes)
			view.mPersonajes.addElement(personaje);
	}

}
