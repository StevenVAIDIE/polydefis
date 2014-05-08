package com.polydefisv4.bean.defis;

import java.util.Date;

import com.polydefisv4.bean.Defi;
import com.polydefisv4.bean.Portee;

public class Geolocalisation extends Defi {

	public Geolocalisation(int id, String idEtudiant, String intitule, String description, Date dateFin, int etatAcceptation, int nombrePoint, Portee portee) {
		super(id, idEtudiant, intitule, description, dateFin, etatAcceptation, nombrePoint, portee);
	}

}
