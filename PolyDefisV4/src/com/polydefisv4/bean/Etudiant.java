package com.polydefisv4.bean;

public class Etudiant {

	public static final String INFO = "INFO";
	public static final String MAT = "MAT";
	public static final String TE = "TE";
	public static final String ETN = "ETN";
	
	private String nom;
	private String prenom;
	private String idEtudiant;
	private boolean respo;
	private int points;
	private int anneePromo;
	private String departement;
	
	public Etudiant(String idEtudiant, String nom, String prenom, String departement, int anneePromo, boolean respo,int points) {
		super();
		
		this.nom = nom;
		this.prenom = prenom;
		this.idEtudiant = idEtudiant;
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

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getIdEtudiant() {
		return idEtudiant;
	}

	public void setIdEtudiant(String idEtudiant) {
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

	public String toString()
	{
		return(this.prenom + " " + this.nom + " " + this.departement + this.anneePromo + " " + this.points + " points.");
	}
	
}
