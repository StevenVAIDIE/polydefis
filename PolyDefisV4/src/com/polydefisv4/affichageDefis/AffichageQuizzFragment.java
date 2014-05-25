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

import com.polydefisv4.R;
import com.polydefisv4.bean.Defi;
import com.polydefisv4.bean.defis.Quizz;
import com.polydefisv4.listeDefis.TypeUtilisation;

public class AffichageQuizzFragment extends Fragment implements OnClickListener {
	private Quizz defi;
	private TypeUtilisation typeUtilisation;
	private Button demarrageQuizz;
	private Button boutonValider;
	private Button boutonRefuser;
	private NumberPicker nbPoint;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_affichage_quizz, container, false);
		defi = (Quizz) getArguments().getSerializable("defis");
        typeUtilisation = (TypeUtilisation) getArguments().getSerializable("typeUtilisation");

        if(typeUtilisation == TypeUtilisation.VisualisationDefisARealiser) {
        	rootView = inflater.inflate(R.layout.fragment_affichage_photo, container, false);
        
        	TextView nbPoint = (TextView) rootView.findViewById(R.id.nb_point);
    		nbPoint.setText(defi.getNombrePoint() + " points");
    		
        	demarrageQuizz = (Button) rootView.findViewById(R.id.bouton_demarrage_quizz);
        	demarrageQuizz.setOnClickListener(this);
        } else if (typeUtilisation == TypeUtilisation.AdministrationPropositionDefis) {
        	rootView = inflater.inflate(R.layout.fragment_affichage_photo_administration, container, false);
        	
    		nbPoint = (NumberPicker) rootView.findViewById(R.id.nb_point);
    		nbPoint.setValue(defi.getNombrePoint());
    		
        	boutonValider = (Button) rootView.findViewById(R.id.bouton_valider);
        	boutonValider.setOnClickListener(this);
        	
        	boutonRefuser = (Button) rootView.findViewById(R.id.bouton_refuser);
        	boutonRefuser.setOnClickListener(this);
        } else {
        	Log.e(getClass().getName(), "Type d'utilisation incorrect");
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
		if (v.equals(demarrageQuizz)) {
			AffichageQuestionFragment newFragment = new AffichageQuestionFragment();
			Bundle bundle = new Bundle();
			bundle.putSerializable("defis", defi);
			newFragment.setArguments(bundle);
		
			FragmentManager fragmentManager = getFragmentManager();
			FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
			fragmentTransaction.replace(R.id.frame_container, newFragment);
			fragmentTransaction.addToBackStack(null);
			fragmentTransaction.commit();
		} else if(v.equals(boutonRefuser)) {
			//defi.setEtatAcceptation();
		} else if(v.equals(boutonValider)) {
			defi.setEtatAcceptation(Defi.ETAT_ACCEPTE);
			defi.setNombrePoint(nbPoint.getValue());
		}
	}
}
