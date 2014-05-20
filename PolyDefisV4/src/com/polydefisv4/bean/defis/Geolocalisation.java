package com.polydefisv4.bean.defis;

import java.util.Date;

import com.polydefisv4.bean.Defi;
import com.polydefisv4.bean.Portee;

public class Geolocalisation extends Defi {
	double longitude;
	double latitude;
	
	public Geolocalisation(String idEtudiant, String intitule, String description, int etatAcceptation, Portee portee) {
		super(idEtudiant, intitule, description, etatAcceptation, portee);
	}		
	
	public Geolocalisation(int id, String idEtudiant, String intitule, String description, Date dateFin, int etatAcceptation, int nombrePoint, Portee portee, double longitude, double latitude) {
		super(id, idEtudiant, intitule, description, dateFin, etatAcceptation, nombrePoint, portee);
		this.longitude = longitude;
		this.latitude = latitude;
	}
	
	public double getLatitude() {
		return latitude;
	}
	
	public double getLongitude() {
		return longitude;
	}
}
