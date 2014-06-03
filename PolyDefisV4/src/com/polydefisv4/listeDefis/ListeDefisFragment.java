package com.polydefisv4.listeDefis;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.polydefisv4.R;
import com.polydefisv4.adapter.ListeDefisAdapter;
import com.polydefisv4.administration.AffichageValidationPhoto;
import com.polydefisv4.affichageDefis.AffichageGeolocalisationFragment;
import com.polydefisv4.affichageDefis.AffichagePhotoFragment;
import com.polydefisv4.affichageDefis.AffichageQrCodeFragment;
import com.polydefisv4.affichageDefis.AffichageQuizzFragment;
import com.polydefisv4.bdd.SQLManager;
import com.polydefisv4.bean.Defi;
import com.polydefisv4.bean.Etudiant;
import com.polydefisv4.bean.defis.Geolocalisation;
import com.polydefisv4.bean.defis.Photo;
import com.polydefisv4.bean.defis.QrCode;
import com.polydefisv4.bean.defis.Quizz;

public class ListeDefisFragment extends Fragment implements
		OnItemClickListener {
	private Etudiant etudiant;
	private ArrayList<Defi> listeDefisEtudiant;
	private TypeUtilisation typeUtilisation;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_liste_defis_realise, container, false);
		etudiant =  (Etudiant) getArguments().getSerializable("etudiant");
		typeUtilisation =  (TypeUtilisation) getArguments().getSerializable("typeUtilisation");

		ListView listViewDefis = (ListView) rootView.findViewById(R.id.listViewDefis);
		TextView texteRemplacement = (TextView) rootView.findViewById(R.id.empty);
		
		SQLManager manager = new SQLManager(getActivity());
		if (typeUtilisation == TypeUtilisation.AdministrationPropositionDefis) {
			getActivity().setTitle("Liste des défis à administrer");
			texteRemplacement.setText("Aucun défi à administrer");
			listeDefisEtudiant = manager.getAllDefiAAccepter();
		} else if (typeUtilisation == TypeUtilisation.AdministrationValidationPhoto) {
			getActivity().setTitle("Liste des photos à valider");
			texteRemplacement.setText("Aucune photo à valider");
			listeDefisEtudiant = manager.getAllPhotoAValider();
		} else if (typeUtilisation == TypeUtilisation.VisualisationDefisARealiser) {
			getActivity().setTitle("Liste des défis à effectuer");
			texteRemplacement.setText("Aucun défi à réaliser");
			listeDefisEtudiant = manager.getAllDefiARealiser(etudiant);
		} else {
			Log.e("ListeDefisRealiseFragment", "Type d'utilisation inconnu");
		}
 
		listViewDefis.setEmptyView(texteRemplacement);
		listViewDefis.setAdapter(new ListeDefisAdapter(this.getActivity(), listeDefisEtudiant));
		listViewDefis.setOnItemClickListener(this);
		return rootView;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Fragment newFragment = null;
		Bundle bundle = new Bundle();

		bundle.putSerializable("etudiant", etudiant);
		bundle.putSerializable("defis", listeDefisEtudiant.get(position));
		bundle.putSerializable("typeUtilisation", typeUtilisation);

		if (typeUtilisation == TypeUtilisation.AdministrationValidationPhoto) {
			newFragment = new AffichageValidationPhoto();
		} else if (listeDefisEtudiant.get(position) instanceof QrCode) {
			newFragment = new AffichageQrCodeFragment();
		} else if (listeDefisEtudiant.get(position) instanceof Photo) {
			newFragment = new AffichagePhotoFragment();
		} else if (listeDefisEtudiant.get(position) instanceof Geolocalisation) {
			newFragment = new AffichageGeolocalisationFragment();
		} else if (listeDefisEtudiant.get(position) instanceof Quizz) {
			newFragment = new AffichageQuizzFragment();
		} else {
			Log.e(getClass().getName(), "Instance inconnue");
		}

		if (newFragment != null) {
			newFragment.setArguments(bundle);

			FragmentManager fragmentManager = getFragmentManager();
			FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
			fragmentTransaction.replace(R.id.frame_container, newFragment);
			fragmentTransaction.addToBackStack(null);
			fragmentTransaction.commit();
		}
	}
}
