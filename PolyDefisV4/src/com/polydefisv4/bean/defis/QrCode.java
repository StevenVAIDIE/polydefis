package com.polydefisv4.bean.defis;

import java.util.Date;

import com.polydefisv4.bean.Defi;
import com.polydefisv4.bean.Portee;

public class QrCode extends Defi {
	private String qrCode; 
	
	public QrCode(int id, String idEtudiant, String intitule, String description, Date dateFin, int etatAcceptation, int nombrePoint, String portee, String qrCode) {
		super(id, idEtudiant, intitule, description, dateFin, etatAcceptation, nombrePoint, portee);
		this.qrCode = qrCode;
	}
	
	public QrCode(String idEtudiant, String intitule, String description, int etatAcceptation, 
			String portee) {
		super(idEtudiant, intitule, description, etatAcceptation, portee);
	}

	public String getQrCode() {
		return qrCode;
	}

	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}
}
