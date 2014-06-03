package com.polydefisv4.affichageDefis;

import java.util.ArrayList;
import java.util.Collections;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.polydefisv4.R;
import com.polydefisv4.bdd.SQLManager;
import com.polydefisv4.bean.DefiRealise;
import com.polydefisv4.bean.Etudiant;
import com.polydefisv4.bean.defis.Quizz;

public class AffichageQuestionFragment extends Fragment implements OnClickListener {
	private int index;
	private boolean donnerPoints;

	private Quizz defis;

	private TextView question;
	private RadioGroup reponses;
	private RadioButton c1;
	private RadioButton c2;
	private RadioButton c3;
	private RadioButton c4;
	private Button boutonEnvoieReponsesQuizz;

	private ArrayList<String> monRandom = new ArrayList<String>();
	private View rootView;
	private Etudiant etudiant;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_affichage_question, container, false);
		getActivity().setTitle("Affichage d'une question");
		index = 0;
		donnerPoints = true;
		defis = (Quizz) getArguments().getSerializable("defis");
		etudiant = (Etudiant) getArguments().getSerializable("etudiant");

		question = (TextView) rootView.findViewById(R.id.question);
		reponses = (RadioGroup) rootView.findViewById(R.id.reponses);
		c1 = (RadioButton) rootView.findViewById(R.id.rep1);
		c2 = (RadioButton) rootView.findViewById(R.id.rep2);
		c3 = (RadioButton) rootView.findViewById(R.id.rep3);
		c4 = (RadioButton) rootView.findViewById(R.id.rep4);

		boutonEnvoieReponsesQuizz = (Button) rootView.findViewById(R.id.envoyer_rep);
		boutonEnvoieReponsesQuizz.setOnClickListener(this);

		melangerListe();

		return rootView;
	}

	public ArrayList<String> melanger(ArrayList<String> listeDepart) {
		ArrayList<String> nouvelle = new ArrayList<String>(listeDepart);
		Collections.shuffle(nouvelle);
		return nouvelle;
	}

	public void melangerListe() {
		monRandom.add(defis.getQuestions(index).getReponse());
		monRandom.add(defis.getQuestions(index).getReponse2());
		monRandom.add(defis.getQuestions(index).getReponse3());
		monRandom.add(defis.getQuestions(index).getReponse4());
		monRandom = melanger(monRandom);

		question.setText(defis.getQuestions(index).getQuestion());
		c1.setText(monRandom.get(1));
		c2.setText(monRandom.get(2));
		c3.setText(monRandom.get(3));
		c4.setText(monRandom.get(0));

		monRandom.clear();
	}

	@Override
	public void onClick(View arg0) {
		int selectedId = reponses.getCheckedRadioButtonId();
		if (selectedId == -1) {
			Toast.makeText(getActivity(), "Veuillez cochez une réponse", Toast.LENGTH_LONG).show();
		} else {
			RadioButton radioButton = (RadioButton) rootView.findViewById(selectedId);
			if (!radioButton.getText().toString().equals(defis.getQuestions(index).getReponse())) {
				donnerPoints = false;
			}
			index++;
			reponses.check(-1);
			
			if (index < defis.getNbQuestions()) {
				melangerListe();
			}

			else {
				SQLManager manager = new SQLManager(getActivity());
				if (donnerPoints) {
					Toast.makeText(getActivity(), "Quizz terminé : " + defis.getNombrePoint() + " points attribués", Toast.LENGTH_LONG).show();
					manager.defiEffectue(defis, etudiant.getIdEtudiant(),DefiRealise.ETAT_REUSSI);
				} else {
					Toast.makeText(getActivity(), "Quizz terminé : Pas de points attribués", Toast.LENGTH_LONG).show();
					manager.defiEffectue(defis, etudiant.getIdEtudiant(),DefiRealise.ETAT_ECHEC);
				}
				getActivity().onBackPressed();
			}
		}
	}
}
