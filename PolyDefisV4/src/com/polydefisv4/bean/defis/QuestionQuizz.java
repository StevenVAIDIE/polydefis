package com.polydefisv4.bean.defis;

public class QuestionQuizz {
	private String question;
	private String reponse1;
	private String reponse2;
	private String reponse3;
	private String reponse4;
	
	public QuestionQuizz(String question, String reponse1, String reponse2, String reponse3, String reponse4) {
		this.question = question;
		this.reponse1 = reponse1;
		this.reponse2 = reponse2;
		this.reponse3 = reponse3;
		this.reponse4 = reponse4;
	}

	public String getQuestion() {
		return question;
	}

	public String getReponse() {
		return reponse1;
	}

	public String getReponse2() {
		return reponse2;
	}

	public String getReponse3() {
		return reponse3;
	}

	public String getReponse4() {
		return reponse4;
	}
}
