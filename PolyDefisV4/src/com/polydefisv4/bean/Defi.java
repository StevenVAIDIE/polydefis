package com.polydefisv4.bean;

import java.util.Date;


public abstract class Defi {

	public static final int ETAT_EN_COURS_ACCEPTATION = 0;
	public static final int ETAT_ACCEPTE = 1;
	
	public static final int TYPE_PHOTO = 1;
	public static final int TYPE_GEOLOCALIATION = 2;
	public static final int TYPE_QRCODE = 3;
	public static final int TYPE_QUIZZ = 4;
	
	private int id;
	private String idEtudiant;
	private String intitule;
	private String description;
	private Date dateFin;
	private int etatAcceptation;
	private int nombrePoint;
	private Portee portee;
	
	public Defi(int id, String idEtudiant, String intitule, String description, Date dateFin, int etatAcceptation, int nombrePoint, Portee portee) {
		this.id = id;
		this.idEtudiant = idEtudiant;
		this.intitule = intitule;
		this.description = description;
		this.dateFin = dateFin;
		this.etatAcceptation = etatAcceptation;
		this.nombrePoint = nombrePoint;
		this.portee = portee;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getIdEtudiant() {
		return idEtudiant;
	}

	public void setIdEtudiant(String idEtudiant) {
		this.idEtudiant = idEtudiant;
	}

	public String getIntitule() {
		return intitule;
	}

	public void setIntitule(String intitule) {
		this.intitule = intitule;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDateFin() {
		return dateFin;
	}

	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}

	public int getEtatAcceptation() {
		return etatAcceptation;
	}

	public void setEtatAcceptation(int etatAcceptation) {
		this.etatAcceptation = etatAcceptation;
	}

	public int getNombrePoint() {
		return nombrePoint;
	}

	public void setNombrePoint(int nombrePoint) {
		this.nombrePoint = nombrePoint;
	}

	public Portee getPortee() {
		return portee;
	}

	public void setPortee(Portee portee) {
		this.portee = portee;
	}
	
}
