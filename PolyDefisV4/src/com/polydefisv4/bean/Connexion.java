package com.polydefisv4.bean;

public class Connexion {

	private String identifiant;
	private String motdepasse;
 
	public Connexion(String identifiant, String motdepasse){
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
 
}