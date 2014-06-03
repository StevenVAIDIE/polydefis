package com.polydefisv4.bean.defis;

import java.util.Date;

import com.polydefisv4.bean.Defi;

public class Geolocalisation extends Defi {
	private static final long serialVersionUID = 559705636230518845L;
	
	private double latitude;
	private double longitude;
	
	public Geolocalisation(int idEtudiant, String intitule, String description, int etatAcceptation, String portee) {
		super(idEtudiant, intitule, description, etatAcceptation, portee);
	}
	
	public Geolocalisation(int id, int idEtudiant, String intitule, String description, Date dateFin, int nombrePoint, int etatAcceptation, String portee, double latitude, double longitude) {
		super(id, idEtudiant, intitule, description, dateFin, nombrePoint, etatAcceptation, portee);
		this.latitude = latitude;
		this.longitude = longitude;
	}


	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
}
