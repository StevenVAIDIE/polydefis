package com.polydefisv4.bean;

public class Parrainage {

	private String parrain;
	private String filleul;
	private int etat;
	
	public static final int DEMANDE_EN_COURS = 0;
	public static final int DEMANDE_ACCEPTE = 1;
	
	public Parrainage(String parrain, String filleul, int etat) {
		super();
		this.parrain = parrain;
		this.filleul = filleul;
		this.etat = etat;
	}

	public String getParrain() {
		return parrain;
	}

	public void setParain(String parain) {
		this.parrain = parain;
	}

	public String getFilleul() {
		return filleul;
	}

	public void setFilleul(String filleul) {
		this.filleul = filleul;
	}

	public int getEtat() {
		return etat;
	}

	public void setEtat(int etat) {
		this.etat = etat;
	}
	
}
