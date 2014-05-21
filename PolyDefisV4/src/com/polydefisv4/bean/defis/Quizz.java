package com.polydefisv4.bean.defis;

import java.util.ArrayList;
import java.util.Date;

import com.polydefisv4.bean.Defi;

public class Quizz extends Defi {
	ArrayList<QuestionQuizz> listeQuestionQuizz;
	
	public Quizz(int id, String idEtudiant, String intitule, String description, Date dateFin, int etatAcceptation, int nombrePoint, String portee) {
		super(id, idEtudiant, intitule, description, dateFin, etatAcceptation, nombrePoint, portee);
		listeQuestionQuizz = new ArrayList<QuestionQuizz>();
	}
	
	public Quizz(String idEtudiant, String intitule, String description, int etatAcceptation, String portee) {
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

	public static Defi getQuizz() {
		Quizz quizz =  new Quizz (10, "E116143S", "Quizz de BG", "Exoooooo", new Date(2000, 02, 15), 1, 20, Defi.PORTEE_ALL);
		QuestionQuizz q = new QuestionQuizz("Quelle est la capitale de la France ? ", "Sable : Réponse", "Le Mans", "Nantes","Toulouse");
		QuestionQuizz q2 = new QuestionQuizz("Qui est la copine de Dodo?", "Martinez : Réponse ", "Alice", "Emeline","Misshautaine");
		QuestionQuizz q3 = new QuestionQuizz("Quelle est la réponse à la question ?", "Martinez : Réponse ", "La reponse D", "KOI? ","answer");
		QuestionQuizz q4 = new QuestionQuizz("En vrai mes questions elles sont pourries?", "T'es un dieu : Réponse", "Putain Ouai", "Grave ","Non");
		quizz.addQuestion(q);
		quizz.addQuestion(q2);
		quizz.addQuestion(q3);
		quizz.addQuestion(q4);
		
		return quizz;
	}
}
