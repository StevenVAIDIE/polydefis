package com.polydefisv4.bean.defis;

import java.util.Date;

import com.polydefisv4.bean.Defi;
import com.polydefisv4.bean.Portee;

public class Photo extends Defi {
	private String url;

	public Photo(String idEtudiant, String intitule, String description, int etatAcceptation, Portee portee) {
		super(idEtudiant, intitule, description, etatAcceptation, portee);
	}
	
	public Photo(int id, String idEtudiant, String intitule, String description, Date dateFin, int etatAcceptation, int nombrePoint, Portee portee, String url) {
		super(id, idEtudiant, intitule, description, dateFin, etatAcceptation, nombrePoint, portee);
		this.url = url;
	}

	public String getUrl() {
		return url;
	}
}
