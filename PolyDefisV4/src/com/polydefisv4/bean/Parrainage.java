package com.polydefisv4.bean;

public class Parrainage {
	private int idParrain;
	private int idFilleul;
	private int etat;
	
	public static final int DEMANDE_EN_COURS = 0;
	public static final int DEMANDE_ACCEPTE = 1;
	
	public Parrainage(int idParrain, int idFilleul, int etat) {
		super();
		this.idParrain = idParrain;
		this.idFilleul = idFilleul;
		this.etat = etat;
	}

	public int getIdParrain() {
		return idParrain;
	}



	public void setIdParrain(int idParrain) {
		this.idParrain = idParrain;
	}



	public int getIdFilleul() {
		return idFilleul;
	}



	public void setIdFilleul(int idFilleul) {
		this.idFilleul = idFilleul;
	}



	public int getEtat() {
		return etat;
	}

	public void setEtat(int etat) {
		this.etat = etat;
	}
}
