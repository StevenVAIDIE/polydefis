package com.polydefisv4.affichageDefis;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.polydefisv4.R;
import com.polydefisv4.bean.defis.Quizz;
/*
public class AffichageQuizzFragment extends Fragment implements OnClickListener {
	private Quizz defi;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_affichage_quizz, container, false);
		defi = (Quizz) getArguments().getSerializable("defis");
		
		TextView titreDefis = (TextView) rootView.findViewById(R.id.intitule_defi);
		titreDefis.setText(defi.getIntitule());

		TextView descriptionDefi = (TextView) rootView.findViewById(R.id.description_defi);
		descriptionDefi.setText(defi.getDescription());

		TextView nbPoint = (TextView) rootView.findViewById(R.id.nb_point);
		nbPoint.setText(defi.getNombrePoint() + " points");

		TextView dateLimite = (TextView) rootView.findViewById(R.id.date_limite);
		dateLimite.setText(defi.getDateFin().toString());
		
		Button demarrageQuizz = (Button) rootView.findViewById(R.id.bouton_demarrage_quizz);
		demarrageQuizz.setOnClickListener(this);
		
		return rootView;
	}

	@Override
	public void onClick(View v) {
		AffichageQuestionFragment newFragment = new AffichageQuestionFragment();
		Bundle bundle = new Bundle();
		bundle.putSerializable("defis", defi);
		newFragment.setArguments(bundle);
		
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.replace(R.id.frame_container, newFragment);
		fragmentTransaction.addToBackStack(null);
		fragmentTransaction.commit();
	}
	
}*/
