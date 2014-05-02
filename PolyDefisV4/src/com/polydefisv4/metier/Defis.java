package com.polydefisv4.metier;

import java.util.ArrayList;
import java.util.Date;

public class Defis { 
	private int idDefis;
	private int idEtudiant;
	private String intitule;
	private String description;
	private TypeDefis typeDefis;
	private Date dateFin;
	private int porteeDefis;
	private int nbPoint;
	private int etatAccepte;
	
	public Defis(TypeDefis typeDefis, String intitule, int nbPoint) {
		this.typeDefis = typeDefis;
		this.intitule = intitule;
		this.nbPoint = nbPoint;
	}
	
	public static ArrayList<Defis> getAllDefis() {
		ArrayList<Defis> listeDefis = new ArrayList<Defis>();
		listeDefis.add(new Defis(TypeDefis.PHOTO, "Boire un coup", 5));
		listeDefis.add(new Defis(TypeDefis.GEOLOCALISATION, "Boire un coup", 5));
		listeDefis.add(new Defis(TypeDefis.QR_CODE, "Boire un coup", 5));
		listeDefis.add(new Defis(TypeDefis.QUIZZ, "Boire un coup", 5));
		listeDefis.add(new Defis(TypeDefis.PHOTO, "Boire un coup", 5));
		listeDefis.add(new Defis(TypeDefis.GEOLOCALISATION, "Boire un coup", 5));
		listeDefis.add(new Defis(TypeDefis.QR_CODE, "Boire un coup", 5));
		listeDefis.add(new Defis(TypeDefis.QUIZZ, "Boire un coup", 5));
		listeDefis.add(new Defis(TypeDefis.PHOTO, "Boire un coup", 5));
		listeDefis.add(new Defis(TypeDefis.GEOLOCALISATION, "Boire un coup", 5));
		listeDefis.add(new Defis(TypeDefis.QR_CODE, "Boire un coup", 5));
		listeDefis.add(new Defis(TypeDefis.QUIZZ, "Boire un coup", 5));
		listeDefis.add(new Defis(TypeDefis.PHOTO, "Boire un coup", 5));
		listeDefis.add(new Defis(TypeDefis.GEOLOCALISATION, "Boire un coup", 5));
		listeDefis.add(new Defis(TypeDefis.QR_CODE, "Boire un coup", 5));
		listeDefis.add(new Defis(TypeDefis.QUIZZ, "Boire un coup", 5));
		listeDefis.add(new Defis(TypeDefis.PHOTO, "Boire un coup", 5));
		listeDefis.add(new Defis(TypeDefis.GEOLOCALISATION, "Boire un coup", 5));
		listeDefis.add(new Defis(TypeDefis.QR_CODE, "Boire un coup", 5));
		listeDefis.add(new Defis(TypeDefis.QUIZZ, "Boire un coup", 5));
		listeDefis.add(new Defis(TypeDefis.PHOTO, "Boire un coup", 5));
		listeDefis.add(new Defis(TypeDefis.GEOLOCALISATION, "Boire un coup", 5));
		listeDefis.add(new Defis(TypeDefis.QR_CODE, "Boire un coup", 5));
		listeDefis.add(new Defis(TypeDefis.QUIZZ, "Boire un coup", 5));
		listeDefis.add(new Defis(TypeDefis.PHOTO, "Boire un coup", 5));
		listeDefis.add(new Defis(TypeDefis.GEOLOCALISATION, "Boire un coup", 5));
		listeDefis.add(new Defis(TypeDefis.QR_CODE, "Boire un coup", 5));
		listeDefis.add(new Defis(TypeDefis.QUIZZ, "Boire un coup", 5));
		listeDefis.add(new Defis(TypeDefis.PHOTO, "Boire un coup", 5));
		listeDefis.add(new Defis(TypeDefis.GEOLOCALISATION, "Boire un coup", 5));
		listeDefis.add(new Defis(TypeDefis.QR_CODE, "Boire un coup", 5));
		listeDefis.add(new Defis(TypeDefis.QUIZZ, "Boire un coup", 5));
		return listeDefis;
	}

	public TypeDefis getTypeDefis() {
		return typeDefis;
	}

	public String getIntitule() {
		return intitule;
	}

	public int getNbPoint() {
		return nbPoint;
	}
}
