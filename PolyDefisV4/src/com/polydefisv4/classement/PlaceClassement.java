package com.polydefisv4.classement;

public enum PlaceClassement {
	placeClassementTotal(0), placeClassementINFO(1), placeClassementETN(2), 
	placeClassementTE(3), placeClassementMAT(4);

	private int emplacement;

	PlaceClassement(int emplacement) {
		this.emplacement = emplacement;
	}
	
	public int getEmplacement() {
		return emplacement;
	}
}
