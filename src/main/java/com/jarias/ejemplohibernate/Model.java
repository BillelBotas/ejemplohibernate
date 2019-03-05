package com.jarias.ejemplohibernate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JFileChooser;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.jarias.ejemplohibernate.base.Arma;
import com.jarias.ejemplohibernate.base.Personaje;
import com.jarias.ejemplohibernate.base.Usuario;

public class Model {
	public Personaje personajeRecuperar;
	public Arma armaRecuperar;
	public List<Arma> armasPersonaje;
	private File ficheroSeleccionado;
	
	public Model() {
		conectar();
	}
	
	@Override
	public void finalize() {
		desconectar();
	}
	
	private void conectar() {
		HibernateUtil.buildSessionFactory();
	}
	
	private void desconectar() {
		HibernateUtil.closeSessionFactory();
	}
	
	public void guardarPersonaje(Personaje personaje) {
		Session sesion = HibernateUtil.getCurrentSession();
		sesion.beginTransaction();
		if(existePersonaje(personaje.getNombre()))
			return;
		sesion.save(personaje);
		for (Arma arma : personaje.getArmas()) {
			arma.setPersonaje(personaje);
			sesion.save(arma);
		}
		sesion.getTransaction().commit();
		sesion.close();
	}
	
	public void guardarPersonajeJson(Personaje personaje) {
		Session sesion = HibernateUtil.getCurrentSession();
		sesion.beginTransaction();
		if(existePersonaje(personaje.getNombre()))
			return;
		sesion.save(personaje);
		sesion.getTransaction().commit();
		sesion.close();
	}
	
	public void eliminarPersonaje(Personaje personaje) {
		personajeRecuperar = personaje;
		armasPersonaje = personaje.getArmas();
		personaje.setArmas(null);
		for(Arma arma : armasPersonaje) {
			arma.setPersonaje(null);
		}
		
		Session sesion = HibernateUtil.getCurrentSession();
		sesion.beginTransaction();
		sesion.delete(personaje);
		sesion.getTransaction().commit();
		sesion.close();
	}
	
	public boolean existePersonaje(String nombre) {
		Session sesion = HibernateUtil.getCurrentSession();
		Query<Personaje> query = sesion.createQuery("FROM Personaje WHERE nombre = :nombre");
		query.setParameter("nombre", nombre);
		Personaje personaje = query.uniqueResult();
		return (personaje != null);
	}
	
	public List<Personaje> getPersonajes(){
		Session sesion = HibernateUtil.getCurrentSession();
		ArrayList<Personaje> personajes = (ArrayList<Personaje>) sesion.createQuery("From Personaje").list();
		return personajes;
	}
	
	public List<Personaje> getPersonajes(String busqueda){
		return null;
	}
	
	public Personaje busqueda(String nomPersonaje) {
		Session sesion = HibernateUtil.getCurrentSession();
		Query<Personaje> query = sesion.createQuery("FROM Personaje p WHERE p.nombre = :nombre");
		query.setParameter("nombre", nomPersonaje);
		
		Personaje personajeBuscar = (Personaje) query.uniqueResult();
		return personajeBuscar;
	}
	
	public void recuperarUltimoPersonaje() {
		personajeRecuperar.setArmas(armasPersonaje);
		
		Session sesion = HibernateUtil.getCurrentSession();
		sesion.beginTransaction();
		for (Arma arma : personajeRecuperar.getArmas()) {
			arma.setPersonaje(personajeRecuperar);
		}
		sesion.save(personajeRecuperar);
		sesion.getTransaction().commit();
		sesion.close();
	}
	
	public boolean comprobar() {
		return false;
	}
	
	public void guardarArma(Arma arma) {
		Session sesion = HibernateUtil.getCurrentSession();
		sesion.beginTransaction();
		sesion.save(arma);
		sesion.getTransaction().commit();
		sesion.close();
	}
	
	public void eliminarArma(Arma arma) {
		armaRecuperar = arma;
		Session sesion = HibernateUtil.getCurrentSession();
		sesion.beginTransaction();
		sesion.delete(arma);
		sesion.getTransaction().commit();
		sesion.close();
	}
	
	public List<Arma> getArmas(){
		Session sesion = HibernateUtil.getCurrentSession();
		ArrayList<Arma> armas = (ArrayList<Arma>) sesion.createQuery("From Arma").list();
		return armas;
	}
	
	public List<Arma> getArmasLibres() {
		Session sesion = HibernateUtil.getCurrentSession();
		List<Arma> armas = (ArrayList<Arma>) sesion.createQuery("FROM Arma a WHERE a.personaje IS NULL").list();
		return armas;
	}
	
	public void recuperarUltimoArma() {
		Session sesion = HibernateUtil.getCurrentSession();
		sesion.beginTransaction();
		sesion.save(armaRecuperar);
		sesion.getTransaction().commit();
		sesion.close();
	}
	
	public void eliminarTodo() {
		Session sesion = HibernateUtil.getCurrentSession();
		sesion.beginTransaction();
		sesion.createSQLQuery("TRUNCATE TABLE armas").executeUpdate();
		sesion.createSQLQuery("TRUNCATE TABLE personajes").executeUpdate();
		sesion.getTransaction().commit();
		sesion.close();
	}
	
	public boolean iniciarSesion(String usuario, String contrasena) {
		Session sesion = HibernateUtil.getCurrentSession();
		Query<Usuario> query = sesion.createQuery("FROM Usuario WHERE nombre = :nombre " + "AND contrasena = SHA1(:contrasena)");
		query.setParameter("nombre", usuario);
		query.setParameter("contrasena", contrasena);
		
		Usuario encontrado = query.uniqueResult();
		return (encontrado != null);
	}
	
	public void exportarJson() {
		try {
			JFileChooser jfc = new JFileChooser();
			jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			if(jfc.showOpenDialog(null) == JFileChooser.CANCEL_OPTION)
				return;
			
			String carpeta = jfc.getSelectedFile().getAbsolutePath();
			File fichero = new File(carpeta + File.separator + "Personajes.json");
			if (fichero.exists()) {
				fichero.delete();
			}
			PrintWriter bw = new PrintWriter(new FileWriter(fichero, true));
			Gson gson = new GsonBuilder().addSerializationExclusionStrategy(new ExclusionStrategy() {
				@Override
				public boolean shouldSkipField(FieldAttributes f) {
					return false;
				}
				@Override
				public boolean shouldSkipClass(Class<?> clazz) {
					return clazz.getCanonicalName().equals("com.jarias.ejemplohibernate.base.Arma");
				}
			}).create();
			
			String jsonStr = gson.toJson(getPersonajes());
			bw.println(jsonStr);
			bw.close();
			
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public void importarJson() {
		try {
			JFileChooser jfc = new JFileChooser();
			if (jfc.showOpenDialog(null) == JFileChooser.CANCEL_OPTION)
				return;
			ficheroSeleccionado = jfc.getSelectedFile();
			
			BufferedReader br = new BufferedReader(new FileReader(ficheroSeleccionado));
			Gson gson = new Gson();
			while(br.ready()) {
				String linea = br.readLine();
				Type collectionType = new TypeToken<Collection<Personaje>>(){}.getType();
				Collection<Personaje> personajes = gson.fromJson(linea, collectionType);
				for(Personaje personaje : personajes)
					guardarPersonajeJson(personaje);
			}
			br.close();
		}catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
}
