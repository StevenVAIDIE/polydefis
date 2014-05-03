package com.polydefisv4.metier;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Etudiant implements Serializable {
	private static final long serialVersionUID = 6115230983358841271L;

	private String nom;
	private String prenom;
	private Specialite specialite;
	private boolean respo;
	private int nbPointsQuizz;
	private int nbPointsGeolocalisation;
	private int nbPointsPhoto;
	private int nbPointsQrCode;
	private int annee;

	public Etudiant(String nom, String prenom, int annee, Specialite specialite, boolean respo) {
		this.nom = nom; 
		this.prenom = prenom;
		this.annee = annee;
		this.specialite = specialite;
		this.respo = respo;
	}
	
	public String getNom() {
		return nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public int getAnnee() {
		return annee;
	}
	
	public Specialite getSpecialite() {
		return specialite;
	}
	
	public static List<Etudiant> getAllEtudiant() {
		List<Etudiant> listeEtudiants = new ArrayList<Etudiant>();
		listeEtudiants.add(new Etudiant("VAIDIE", "Steven", 3, Specialite.INFO, false));
		listeEtudiants.add(new Etudiant("PAYEN", "Mathieu", 3, Specialite.INFO, false));
		listeEtudiants.add(new Etudiant("GUERRE", "Martin", 3, Specialite.INFO, false));
		listeEtudiants.add(new Etudiant("KODELJA", "Dorian", 3, Specialite.INFO, false));
		listeEtudiants.add(new Etudiant("GUINARD", "Arnaud", 3, Specialite.INFO, false));
		listeEtudiants.add(new Etudiant("GOUDAUX", "Thomas", 3, Specialite.INFO, false));
		
		return listeEtudiants;
	}

	public int getNbPointPhoto() {
		return nbPointsPhoto;
	}

	public int getNbPoint() {
		return nbPointsPhoto + nbPointsQuizz + nbPointsGeolocalisation + nbPointsQrCode;
	}

	public int getNbPointQuizz() {
		return nbPointsQuizz;
	}

	public int getNbPointGeolocalisation() {
		return nbPointsGeolocalisation;
	}

	public int getNbPointQrCode() {
		return nbPointsQrCode;
	}

	public boolean isAdmin() {
		return respo;
	}

	public static Etudiant getEtudiant() {
		return new Etudiant("GUERRE", "Martin", 3, Specialite.INFO, false);
	}
}
