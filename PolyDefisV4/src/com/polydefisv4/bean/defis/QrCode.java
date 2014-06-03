package com.polydefisv4.bean.defis;

import java.util.Date;

import com.polydefisv4.bean.Defi;

public class QrCode extends Defi {
	/**
	 * 
	 */
	private static final long serialVersionUID = 876780732416078189L;
	private String qrCode; 
	
	public QrCode(int id, int idEtudiant, String intitule, String description, Date dateFin, int nombrePoint, int etatAcceptation, String portee, String qrCode) {
		super(id, idEtudiant, intitule, description, dateFin, nombrePoint, etatAcceptation, portee);
		this.qrCode = qrCode;
	}
	
	public QrCode(int idEtudiant, String intitule, String description, int etatAcceptation, 
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
