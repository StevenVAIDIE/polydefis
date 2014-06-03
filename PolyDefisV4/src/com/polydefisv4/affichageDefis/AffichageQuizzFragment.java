package com.polydefisv4.affichageDefis;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.polydefisv4.R;
import com.polydefisv4.bdd.SQLManager;
import com.polydefisv4.bean.Etudiant;
import com.polydefisv4.bean.defis.Quizz;
import com.polydefisv4.listeDefis.TypeUtilisation;

public class AffichageQuizzFragment extends Fragment implements OnClickListener {
	private Quizz defi;
	private TypeUtilisation typeUtilisation;
	private Button demarrageQuizz;
	private Button boutonValider;
	private Button boutonRefuser;
	private NumberPicker nbPoint;
	private Etudiant etudiant;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView;
		
		defi = (Quizz) getArguments().getSerializable("defis");
        typeUtilisation = (TypeUtilisation) getArguments().getSerializable("typeUtilisation");
        etudiant = (Etudiant) getArguments().getSerializable("etudiant");
        
        if(typeUtilisation == TypeUtilisation.VisualisationDefisARealiser) {
        	rootView = inflater.inflate(R.layout.fragment_affichage_quizz, container, false);
    		getActivity().setTitle("Affichage d'un défi");

        	TextView nbPoint = (TextView) rootView.findViewById(R.id.nb_point);
    		nbPoint.setText(defi.getNombrePoint() + " points");
    		
        	demarrageQuizz = (Button) rootView.findViewById(R.id.bouton_demarrage_quizz);
        	demarrageQuizz.setOnClickListener(this);
        } else if (typeUtilisation == TypeUtilisation.AdministrationPropositionDefis) {
        	rootView = inflater.inflate(R.layout.fragment_affichage_quizz_administration, container, false);
    		getActivity().setTitle("Administration d'un défi");

    		nbPoint = (NumberPicker) rootView.findViewById(R.id.nb_point);
    		nbPoint.setMinValue(0);
    		nbPoint.setMaxValue(15);
    		nbPoint.setValue(defi.getNombrePoint());

        	boutonValider = (Button) rootView.findViewById(R.id.bouton_valider);
        	boutonValider.setOnClickListener(this);
        	
        	boutonRefuser = (Button) rootView.findViewById(R.id.bouton_refuser);
        	boutonRefuser.setOnClickListener(this);
        } else {
        	Log.e(getClass().getName(), "Type d'utilisation incorrect");
        	return null;
        }
		
    	AffichageDefi fragmentDefi = new AffichageDefi();
    	fragmentDefi.setArguments(getArguments());
    
    	FragmentManager fragmentManager = getFragmentManager();
    	FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    	fragmentTransaction.replace(R.id.fragmentAffichageDefis, fragmentDefi);
    	fragmentTransaction.commit();
		
		return rootView;
	}

	@Override
	public void onClick(View v) {
		SQLManager manager = new SQLManager(getActivity());

		if (v.equals(demarrageQuizz)) {
			Fragment newFragment = new AffichageQuestionFragment();
			Bundle bundle = new Bundle();
			bundle.putSerializable("etudiant", etudiant);
			bundle.putSerializable("defis", defi);
			newFragment.setArguments(bundle);
			
			FragmentManager fragmentManager = getFragmentManager();
			FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
			fragmentTransaction.replace(R.id.frame_container, newFragment);
			fragmentTransaction.commit();
		} else if(v.equals(boutonRefuser)) {
			Toast.makeText(getActivity(), "Le quizz a bien été supprimé", Toast.LENGTH_LONG).show();
			manager.removeQuizz(defi);
			getActivity().onBackPressed();
		} else if(v.equals(boutonValider)) {
			Toast.makeText(getActivity(), "Le quizz a bien été validé", Toast.LENGTH_LONG).show();
			manager.validerDefi(defi, nbPoint.getValue());
			getActivity().onBackPressed();
		} else {
			Toast.makeText(getActivity(), "onClick inconnu", Toast.LENGTH_LONG).show();
			return;
		}
	}
}
