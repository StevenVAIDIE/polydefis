package com.polydefisv4.bean;

import java.util.Date;

public class DefiRealise {
	public final static String ETAT_ECHEC = "Echec"; 
	public final static String ETAT_REUSSI = "Reussi"; 

	private int idDefi;
	private int idEtudiant;
	private Date dateRealise;
	private String etat;

	public DefiRealise(int idDefi, int idEtudiant, Date dateRealise, String etat) {
		super();
		this.idDefi = idDefi;
		this.idEtudiant = idEtudiant;
		this.dateRealise = dateRealise;
		this.etat = etat;
	}

	public int getIdDefi() {
		return idDefi;
	}
	
	public void setIdDefi(int idDefi) {
		this.idDefi = idDefi;
	}
	
	public int getIdEtudiant() {
		return idEtudiant;
	}
	
	public void setIdEtudiant(int idEtudiant) {
		this.idEtudiant = idEtudiant;
	}
	
	public Date getDateRealise() {
		return dateRealise;
	}
	
	public void setDateRealise(Date dateRealise) {
		this.dateRealise = dateRealise;
	}

	public String getEtat() {
		return etat;
	}

	public void setEtat(String etat) {
		this.etat = etat;
	}
}
