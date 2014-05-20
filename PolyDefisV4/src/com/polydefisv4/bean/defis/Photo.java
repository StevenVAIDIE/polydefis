package com.polydefisv4.bean.defis;

import java.util.Date;

import com.polydefisv4.bean.Defi;
import com.polydefisv4.bean.Portee;

public class Photo extends Defi {
	private String urlPhoto;

	public Photo(String idEtudiant, String intitule, String description, int etatAcceptation, String portee) {
		super(idEtudiant, intitule, description, etatAcceptation, portee);
	}
	
	public Photo(int id, String idEtudiant, String intitule, String description, Date dateFin, int etatAcceptation, int nombrePoint, String portee, String urlPhoto) {
		super(id, idEtudiant, intitule, description, dateFin, etatAcceptation, nombrePoint, portee);
		this.urlPhoto = urlPhoto;
	}

	public String getUrlPhoto() {
		return urlPhoto;
	}

	public void setUrlPhoto(String urlPhoto) {
		this.urlPhoto = urlPhoto;
	}
}
