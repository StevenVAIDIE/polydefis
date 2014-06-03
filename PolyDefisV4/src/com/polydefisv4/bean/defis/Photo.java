package com.polydefisv4.bean.defis;

import java.util.Date;

import com.polydefisv4.bean.Defi;

public class Photo extends Defi {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8443276664576636357L;
	private String urlPhoto;

	public Photo(int idEtudiant, String intitule, String description, int etatAcceptation, String portee) {
		super(idEtudiant, intitule, description, etatAcceptation, portee);
	}
	
	public Photo(int id, int idEtudiant, String intitule, String description, Date dateFin, int nombrePoint, int etatAcceptation, String portee, String urlPhoto) {
		super(id, idEtudiant, intitule, description, dateFin, nombrePoint, etatAcceptation, portee);
		this.urlPhoto = urlPhoto;
	}

	public Photo(int id, int idEtudiant, String intitule, String description, Date dateFin, int nombrePoint, int etatAcceptation, String portee) {
		this(id, idEtudiant, intitule, description, dateFin, nombrePoint, etatAcceptation, portee, "");
	}

	public String getUrlPhoto() {
		return urlPhoto;
	}

	public void setUrlPhoto(String urlPhoto) {
		this.urlPhoto = urlPhoto;
	}
}
