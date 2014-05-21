package com.polydefisv4.listeDefis;

import java.util.ArrayList;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.polydefisv4.R;
import com.polydefisv4.adapter.ListeDefisAdapter;
import com.polydefisv4.affichageDefis.AffichageGeolocalisationFragment;
import com.polydefisv4.affichageDefis.AffichagePhotoFragment;
import com.polydefisv4.affichageDefis.AffichageQrCodeFragment;
import com.polydefisv4.affichageDefis.AffichageQuizzFragment;
import com.polydefisv4.bean.Defi;
import com.polydefisv4.bean.Etudiant;
import com.polydefisv4.bean.defis.Geolocalisation;
import com.polydefisv4.bean.defis.Photo;
import com.polydefisv4.bean.defis.QrCode;
import com.polydefisv4.bean.defis.Quizz;

public class ListeDefisRealiseFragment extends Fragment implements OnItemClickListener {	
	private Etudiant etudiant;
	private ArrayList<Defi> listeDefisEtudiant;
	private TypeUtilisation typeUtilisation;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_liste_defis_realise, container, false);
        etudiant = (Etudiant) getArguments().getSerializable("etudiant");
        typeUtilisation = (TypeUtilisation) getArguments().getSerializable("typeUtilisation");

	    ListView listViewDefis = (ListView) rootView.findViewById(R.id.listViewDefis);
	    
	    if (typeUtilisation == TypeUtilisation.AdministrationPropositionDefis) {
		    listeDefisEtudiant = Defi.getAllDefis();
	    } else if (typeUtilisation == TypeUtilisation.AdministrationValidationPhoto) {
		    listeDefisEtudiant = Defi.getAllDefis();
	    } else if (typeUtilisation == TypeUtilisation.VisualisationDefisARealiser){
		    listeDefisEtudiant = Defi.getAllDefis();
	    } else {
	    	Log.e("ListeDefisRealiseFragment", "Type d'utilisation inconnu");
	    }

	    listViewDefis.setAdapter(new ListeDefisAdapter(this.getActivity(),listeDefisEtudiant));
	    listViewDefis.setOnItemClickListener(this);
		return rootView;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,	long id) {
		Fragment newFragment = null;
		Bundle bundle = new Bundle();
		
		bundle.putSerializable("defis", listeDefisEtudiant.get(position));
		bundle.putSerializable("typeUtilisation", typeUtilisation);

		if(listeDefisEtudiant.get(position) instanceof QrCode) {
			newFragment = new AffichageQrCodeFragment();
		} else if (listeDefisEtudiant.get(position) instanceof Photo) {
			newFragment = new AffichagePhotoFragment();
		} else if (listeDefisEtudiant.get(position) instanceof Geolocalisation) {
			newFragment = new AffichageGeolocalisationFragment();
		} else if (listeDefisEtudiant.get(position) instanceof Quizz) {
			newFragment = new AffichageQuizzFragment();
		} else {
			
		}
		
		newFragment.setArguments(bundle);

		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.replace(R.id.frame_container, newFragment);
		fragmentTransaction.addToBackStack(null);
		fragmentTransaction.commit();
	}
}
