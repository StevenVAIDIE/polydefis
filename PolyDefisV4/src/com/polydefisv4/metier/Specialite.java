package com.polydefisv4.metier;

public enum Specialite {
	INFO("Informatique"),
	ETN("ETN"),
	TE("Themo-energetique"),
	MAT("Materiaux");
	
	private String nomSpecialite;
	
	private Specialite(String nomSpecialite) {
		this.nomSpecialite = nomSpecialite;
	}
	
	@Override
	public String toString() {
		return nomSpecialite;
	}
}
