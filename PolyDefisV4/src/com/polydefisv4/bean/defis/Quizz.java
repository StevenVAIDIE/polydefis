package com.polydefisv4.bean.defis;

import java.util.Date;

import com.polydefisv4.bean.Defi;
import com.polydefisv4.bean.Portee;

public class Quizz extends Defi {

	private int numeroQuestion;
	private String bonneReponse1;
	private String mauvaiseReponse2;
	private String mauvaiseReponse3;
	private String mauvaiseReponse4;
	
	public Quizz(int id, String idEtudiant, String intitule, String description, Date dateFin, int etatAcceptation, int nombrePoint, String portee, int numeroQuestion, String bonneReponse1, String mauvaiseReponse2, String mauvaiseReponse3, String mauvaiseReponse4) {
		super(id, idEtudiant, intitule, description, dateFin, etatAcceptation,
				nombrePoint, portee);
		this.numeroQuestion = numeroQuestion;
		this.bonneReponse1 = bonneReponse1;
		this.mauvaiseReponse2 = mauvaiseReponse2;
		this.mauvaiseReponse3 = mauvaiseReponse3;
		this.mauvaiseReponse4 = mauvaiseReponse4;
	}

	public int getNumeroQuestion() {
		return numeroQuestion;
	}

	public void setNumeroQuestion(int numeroQuestion) {
		this.numeroQuestion = numeroQuestion;
	}

	public String getBonneReponse1() {
		return bonneReponse1;
	}

	public void setBonneReponse1(String bonneReponse1) {
		this.bonneReponse1 = bonneReponse1;
	}

	public String getMauvaiseReponse2() {
		return mauvaiseReponse2;
	}

	public void setMauvaiseReponse2(String mauvaiseReponse2) {
		this.mauvaiseReponse2 = mauvaiseReponse2;
	}

	public String getMauvaiseReponse3() {
		return mauvaiseReponse3;
	}

	public void setMauvaiseReponse3(String mauvaiseReponse3) {
		this.mauvaiseReponse3 = mauvaiseReponse3;
	}

	public String getMauvaiseReponse4() {
		return mauvaiseReponse4;
	}

	public void setMauvaiseReponse4(String mauvaiseReponse4) {
		this.mauvaiseReponse4 = mauvaiseReponse4;
	}
	
}
