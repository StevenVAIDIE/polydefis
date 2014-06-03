package com.polydefisv4.bean;

public class Connexion {
	private int idEtudiant;
	private String identifiant;
	private String motdepasse;
 
	public Connexion(int idEtudiant, String identifiant, String motdepasse){
		this.idEtudiant = idEtudiant;
		this.identifiant = identifiant;
		this.motdepasse = motdepasse;
	}
 
	public String getIdentifiant() {
		return identifiant;
	}
 
	public void setIdentifiant(String identifiant) {
		this.identifiant = identifiant;
	}
 
	public String getMotDePasse() {
		return motdepasse;
	}
 
	public void setMotDePasse(String motdepasse) {
		this.motdepasse = motdepasse;
	}

	public int getIdEtudiant() {
		return idEtudiant;
	}

	public void setIdEtudiant(int idEtudiant) {
		this.idEtudiant = idEtudiant;
	}
 
}