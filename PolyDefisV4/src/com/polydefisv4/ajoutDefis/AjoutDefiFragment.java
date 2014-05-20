package com.polydefisv4.ajoutDefis;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.polydefisv4.R;
import com.polydefisv4.bean.Defi;
import com.polydefisv4.bean.EtatAcceptation;
import com.polydefisv4.bean.Etudiant;
import com.polydefisv4.bean.Portee;
import com.polydefisv4.bean.defis.Geolocalisation;
import com.polydefisv4.bean.defis.Photo;
import com.polydefisv4.bean.defis.QrCode;
import com.polydefisv4.bean.defis.Quizz;

/**
 * @author Dorian KODELJA
 * 
 */
public class AjoutDefiFragment extends Fragment implements OnClickListener {
	private EditText nomDefis;
	private EditText description;
	private Spinner typeDefi;
	private Spinner portee;
	private Button valider;
	private Etudiant etudiant;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.activity_ajout_defi, container, false);
		etudiant = (Etudiant) getArguments().getSerializable("etudiant");
		
		nomDefis = (EditText) rootView.findViewById(R.id.nomDefi);
		description = (EditText) rootView.findViewById(R.id.description);
		typeDefi = (Spinner) rootView.findViewById(R.id.typeDefi);
		portee = ((Spinner) rootView.findViewById(R.id.portee));
		valider = (Button) rootView.findViewById(R.id.validation);

		valider.setOnClickListener(this);

		return rootView;
	}

	@Override
	public void onClick(View v) {
		if ((nomDefis.getText().toString().isEmpty())) {
			Toast.makeText(getActivity(), "Veuillez saisir un nom de defi",
					Toast.LENGTH_LONG).show();
		} else if ((description.getText().toString().isEmpty())) {
			Toast.makeText(getActivity(),
					"Veuillez saisir une description de defi",
					Toast.LENGTH_LONG).show();
		} else {
			Defi defis = null;
			String typeDefiChoisi = typeDefi.getSelectedItem().toString();
			Fragment newFragment = null;
			String porteDefis = null;

			if (portee.getSelectedItem().toString() == "Filleul") {
				porteDefis = Defi.PORTEE_FILLEUL;
			} else if (portee.getSelectedItem().toString() == "Promo") {
				porteDefis = Defi.PORTEE_PROMO;
			} else if (portee.getSelectedItem().toString() == "Tous") {
				porteDefis = Defi.PORTEE_ALL;
			} else {
				Log.e(getClass().getName(),"Portee inconnue");
			}
			
			if (typeDefiChoisi.equals("Photo")) {
				defis = new Photo(etudiant.getIdEtudiant(), nomDefis.getText().toString(), description.getText().toString(), EtatAcceptation.proposition, porteDefis);
				newFragment = new AjoutDefiFinaFragment();
			} else if (typeDefiChoisi.equals("GPS")) {
				defis = new Geolocalisation(etudiant.getIdEtudiant(), nomDefis.getText().toString(), description.getText().toString(),EtatAcceptation.proposition, porteDefis);
				newFragment = new AjoutDefiGeolocalisationFragment();
			} else if (typeDefiChoisi.equals("Quizz")) {
				defis = new Quizz(etudiant.getIdEtudiant(), nomDefis.getText().toString(), description.getText().toString(), EtatAcceptation.proposition, porteDefis);
				newFragment = new AjoutDefiQuizzFragment();
			} else if (typeDefiChoisi.equals("QrCode")) {
				defis = new QrCode(etudiant.getIdEtudiant(), nomDefis.getText().toString(), description.getText().toString(), EtatAcceptation.proposition, porteDefis);
				newFragment = new AjoutDefiQRCodeFragment();
			} else {
				Log.e(getClass().getName(), "Type de defi introuvable");
			}

			Bundle bundle = new Bundle();
			bundle.putSerializable("defis", defis);
			newFragment.setArguments(bundle);

			FragmentManager fragmentManager = getFragmentManager();
			FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
			fragmentTransaction.replace(R.id.frame_container, newFragment);
			fragmentTransaction.addToBackStack(null);
			fragmentTransaction.commit();
		}
	}
}
