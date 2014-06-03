package com.polydefisv4.bean;

import java.io.Serializable;
import java.util.Date;

public abstract class Defi implements Serializable, Comparable<Defi> {
	private static final long serialVersionUID = -7240025753183618060L;

	public static final int ETAT_EN_COURS_ACCEPTATION = 0;
	public static final int ETAT_ACCEPTE = 1;
	public static final int ETAT_ATTENTE_VALIDATION = 2;
	public static final int ETAT_TERMINE = 3;

	public static final int TYPE_PHOTO = 1;
	public static final int TYPE_GEOLOCALISATION = 2;
	public static final int TYPE_QRCODE = 3;
	public static final int TYPE_QUIZZ = 4;

	public static final String PORTEE_ALL = "Tous";
	public static final String PORTEE_PROMO = "Promo";
	public static final String PORTEE_FILLEUL = "Filleul";

	private int id;
	private int idEtudiant;
	private String intitule;
	private String description;
	private Date dateFin;
	private int etatAcceptation;
	private int nombrePoint;
	private String portee;

	public Defi(int idEtudiant, String intitule, String description,
			int etatAcceptation, String portee) {
		this.idEtudiant = idEtudiant;
		this.intitule = intitule;
		this.description = description;
		this.portee = portee;
		this.etatAcceptation = etatAcceptation;
	}

	public Defi(int id, int idEtudiant, String intitule, String description,
			Date dateFin, int nombrePoint, int etatAcceptation, String portee) {
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

	public int getIdEtudiant() {
		return idEtudiant;
	}

	public void setIdEtudiant(int idEtudiant) {
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

	public String getPortee() {
		return portee;
	}

	public void setPortee(String portee) {
		this.portee = portee;
	}
	
	@Override
	public int compareTo(Defi defi) {
		return this.id - defi.getId();
	}
}
