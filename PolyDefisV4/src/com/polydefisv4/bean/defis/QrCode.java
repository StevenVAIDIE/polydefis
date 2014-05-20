package com.polydefisv4.bean.defis;

import java.util.Date;

import com.polydefisv4.bean.Defi;
import com.polydefisv4.bean.Portee;

public class QrCode extends Defi {
	private String url;

	public QrCode(int id, String idEtudiant, String intitule, String description, Date dateFin, int etatAcceptation, int nombrePoint, Portee portee, String url) {
		super(id, idEtudiant, intitule, description, dateFin, etatAcceptation, nombrePoint, portee);
		this.url = url;
	}
	
	public QrCode(String idEtudiant, String intitule, String description, int etatAcceptation, 
			Portee portee) {
		super(idEtudiant, intitule, description, etatAcceptation, portee);
	}

	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
}
