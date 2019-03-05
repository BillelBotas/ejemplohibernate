package com.jarias.ejemplohibernate;

import org.hibernate.Session;

import com.jarias.ejemplohibernate.base.Personaje;

public class Aplicacion {
	public static void main(String[] args) {
		View view = new View();
		Model model = new Model();
		Controller controller = new Controller(view, model);
	}
}
