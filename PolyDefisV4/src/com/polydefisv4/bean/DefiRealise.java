package com.polydefisv4.bean;

import java.util.Date;

public class DefiRealise {

	public static final int EN_COURS_VALIDATION = 1;
	public static final int VALIDE = 2;
	
	private int idDefi;
	private String idEtudiant;
	private Date dateRealise;
	private int etat;
	
	public DefiRealise(int idDefi, String idEtudiant) {
		this.idDefi = idDefi;
		this.idEtudiant = idEtudiant;
		this.dateRealise = null;
		this.etat = EN_COURS_VALIDATION;
	}

	public DefiRealise(int idDefi, String idEtudiant, Date dateRealise) {
		super();
		this.idDefi = idDefi;
		this.idEtudiant = idEtudiant;
		this.dateRealise = dateRealise;
		this.etat = VALIDE;
	}

	public int getIdDefi() {
		return idDefi;
	}
	
	public void setIdDefi(int idDefi) {
		this.idDefi = idDefi;
	}
	
	public String getIdEtudiant() {
		return idEtudiant;
	}
	
	public void setIdEtudiant(String idEtudiant) {
		this.idEtudiant = idEtudiant;
	}
	
	public Date getDateRealise() {
		return dateRealise;
	}
	
	public void setDateRealise(Date dateRealise) {
		this.dateRealise = dateRealise;
	}
	
	public int getEtat() {
		return etat;
	}
	
	public void setEtat(int etat) {
		this.etat = etat;
	}
	
	
	
}
