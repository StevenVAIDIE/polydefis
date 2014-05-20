package com.polydefisv4.ajoutDefis;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.polydefisv4.R;
import com.polydefisv4.bean.defis.QuestionQuizz;
import com.polydefisv4.bean.defis.Quizz;

public class AjoutDefiQuizzFragment extends Fragment implements OnClickListener {
	private Button ajoutQuestion;
	private Button valider;
	private EditText editTextQuestion;
	private EditText editTextRep1;
	private EditText editTextRep2;
	private EditText editTextRep3;
	private EditText editTextRep4;
	
	private Quizz defi;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.activity_ajout_defi_quizz, container, false);
		defi = (Quizz) getArguments().getSerializable("defis");
		
		editTextQuestion = ((EditText) rootView.findViewById(R.id.question));
		editTextRep1 = ((EditText) rootView.findViewById(R.id.rep1));
		editTextRep2 = ((EditText) rootView.findViewById(R.id.rep2));
		editTextRep3 = ((EditText) rootView.findViewById(R.id.rep3));
		editTextRep4 = ((EditText) rootView.findViewById(R.id.rep4));
		ajoutQuestion = (Button) rootView.findViewById(R.id.buttonAddQuestion);
		valider = (Button) rootView.findViewById(R.id.QuizzValidation);
		
		valider.setOnClickListener(this);
		ajoutQuestion.setOnClickListener(this);
		
		return rootView;
	}

	@Override
	public void onClick(View v) {
		String question = editTextQuestion.getText().toString();
		String rep1 = editTextRep1.getText().toString();
		String rep2 = editTextRep2.getText().toString();
		String rep3 = editTextRep3.getText().toString();
		String rep4 = editTextRep4.getText().toString();
		
		if (question.isEmpty() || rep1.isEmpty() || rep2.isEmpty() || rep3.isEmpty() || rep4.isEmpty()) {
			Toast.makeText(getActivity(), "Veuillez remplir tous les champs", Toast.LENGTH_LONG).show();
		} else {
			QuestionQuizz questionQuizz = new QuestionQuizz(question, rep1, rep2, rep3, rep4);
			defi.addQuestion(questionQuizz);

			if (v.getId() == R.id.buttonAddQuestion) {
				Toast.makeText(getActivity(), "Question ajoutée", Toast.LENGTH_LONG).show();
				editTextQuestion.setText(null);
				editTextRep1.setText(null);
				editTextRep2.setText(null);
				editTextRep3.setText(null);
				editTextRep4.setText(null);
			} else {
				Fragment newFragment = new AjoutDefiFinaFragment();
				Bundle bundle = new Bundle();
				bundle.putSerializable("defis", defi);
				newFragment.setArguments(bundle);

				FragmentManager fragmentManager = getFragmentManager();
				FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
				fragmentTransaction.replace(R.id.frame_container, newFragment);
				fragmentTransaction.addToBackStack(null);
				fragmentTransaction.commit();
			}
		}
	}
}
