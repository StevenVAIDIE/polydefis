package com.polydefisv4.metier;

public enum TypeDefis {
	QUIZZ("Quizz"),
	GEOLOCALISATION("Geolocalisation"),
	PHOTO("Photo"),
	QR_CODE("QrCode");
	
	private String typeDefis;
	private TypeDefis(String typeDefis) {
		this.typeDefis = typeDefis;
	}
	
	@Override
	public String toString() {
		return typeDefis;
	}
}
