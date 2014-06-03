package com.polydefisv4.bean.defis;

import java.util.ArrayList;
import java.util.Date;

import com.polydefisv4.bean.Defi;

public class Quizz extends Defi {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4952744520402397373L;
	ArrayList<QuestionQuizz> listeQuestionQuizz;
	
	public Quizz(int id, int idEtudiant, String intitule, String description, Date dateFin, int nombrePoint, int etatAcceptation, String portee) {
		super(id, idEtudiant, intitule, description, dateFin, nombrePoint,  etatAcceptation, portee);
		listeQuestionQuizz = new ArrayList<QuestionQuizz>();
	}
	
	public Quizz(int idEtudiant, String intitule, String description, int etatAcceptation, String portee) {
		super(idEtudiant, intitule, description, etatAcceptation, portee);
		listeQuestionQuizz = new ArrayList<QuestionQuizz>();
	}
	
	public QuestionQuizz getQuestions(int index) {
		return listeQuestionQuizz.get(index);
	}
	
	public void addQuestion(QuestionQuizz questionQuizz) {
		listeQuestionQuizz.add(questionQuizz);
	}

	public int getNbQuestions() {
		return listeQuestionQuizz.size();
	}
	
	public ArrayList<QuestionQuizz> getListeQuestion() {
		return listeQuestionQuizz;
	}
}
