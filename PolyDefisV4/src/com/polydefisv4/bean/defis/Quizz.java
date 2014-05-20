package com.polydefisv4.bean.defis;

import java.util.ArrayList;
import java.util.Date;

import com.polydefisv4.bean.Defi;
import com.polydefisv4.bean.Portee;

public class Quizz extends Defi {
	ArrayList<QuestionQuizz> listeQuestionQuizz;
	
	public Quizz(int id, String idEtudiant, String intitule, String description, Date dateFin, int etatAcceptation, int nombrePoint, Portee portee) {
		super(id, idEtudiant, intitule, description, dateFin, etatAcceptation, nombrePoint, portee);
		listeQuestionQuizz = new ArrayList<QuestionQuizz>();
	}
	
	public Quizz(String idEtudiant, String intitule, String description, int etatAcceptation, Portee portee) {
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
		Quizz quizz =  new Quizz (10, "E116143S", "Quizz de BG", "Exoooooo", new Date(2000, 02, 15), 1, 20, Portee.Promotion);
		QuestionQuizz q = new QuestionQuizz("Quelle est la capitale de la France ? ", "Sable", "Nantes","Toulouse", "Le Mans : Réponse");
		QuestionQuizz q2 = new QuestionQuizz("Qui est la copine de Dodo?", "Alice", "Emeline","Misshautaine", "Martinez : Réponse ");
		QuestionQuizz q3 = new QuestionQuizz("Quelle est la réponse à la question ?", "La reponse D", "KOI? ","answer", "La réponse de la question : Réponse");
		QuestionQuizz q4 = new QuestionQuizz("En vrai mes questions elles sont pourries?", "Putain Ouai", "Grave ","Non", "T'es un dieu : Réponse");
		quizz.addQuestion(q);
		quizz.addQuestion(q2);
		quizz.addQuestion(q3);
		quizz.addQuestion(q4);
		
		return quizz;
	}
}
