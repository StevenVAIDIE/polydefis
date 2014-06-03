package com.polydefisv4.bean;

import java.io.Serializable;

public class Etudiant implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String INFO = "INFO";
	public static final String MAT = "MAT";
	public static final String TE = "TE";
	public static final String ETN = "ETN";
	
	private int idEtudiant;
	private String nom;
	private String prenom;
	private boolean respo;
	private int points;
	private int anneePromo;
	private String departement;
	
	public Etudiant(int idEtudiant, String nom, String prenom, String departement, int anneePromo, boolean respo,int points) {
		super();
		
		this.idEtudiant = idEtudiant;
		this.nom =  nom;
		this.prenom = prenom;
		if(anneePromo == 3)
			this.respo = false;
		else
			this.respo = respo;
		this.points = points;
		this.anneePromo = anneePromo;
		this.departement = departement;
	}
	
	
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Etudiant other = (Etudiant) obj;
		if (nom == null) {
			if (other.nom != null)
				return false;
		} else if (!nom.equals(other.nom))
			return false;
		if (prenom == null) {
			if (other.prenom != null)
				return false;
		} else if (!prenom.equals(other.prenom))
			return false;
		return true;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public int getIdEtudiant() {
		return idEtudiant;
	}

	public void setIdEtudiant(int idEtudiant) {
		this.idEtudiant = idEtudiant;
	}

	public boolean isRespo() {
		return respo;
	}

	public void setRespo(boolean respo) {
		this.respo = respo;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}
	
	public int getAnneePromo() {
		return anneePromo;
	}

	public void setAnneePromo(int anneePromo) {
		this.anneePromo = anneePromo;
	}

	public String getDepartement() {
		return departement;
	}

	public void setDepartement(String departement) {
		this.departement = departement;
	}

	public String toString() {
		return(this.prenom + " " + this.nom);
	}
}
