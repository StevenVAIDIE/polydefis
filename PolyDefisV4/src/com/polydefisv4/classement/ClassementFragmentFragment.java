package com.polydefisv4.classement;

import java.util.List;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.polydefisv4.R;
import com.polydefisv4.bdd.SQLManager;
import com.polydefisv4.bean.Etudiant;

public class ClassementFragmentFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_fragment_classement, container, false);
		int numeroPage = getArguments().getInt("section_number");
		int anneeClassement = getArguments().getInt("anneePromotion");
		Etudiant etudiant = (Etudiant) getArguments().getSerializable("etudiant");
		
		List<Etudiant> listeEtudiant = null;
		
		SQLManager manager = new SQLManager(getActivity());
		if (numeroPage == PlaceClassement.placeClassementTotal.getEmplacement()) {
			listeEtudiant = manager.getEtudiantAnnee(anneeClassement);
		} else if (numeroPage == PlaceClassement.placeClassementINFO.getEmplacement()) {
			listeEtudiant = manager.getEtudiantPromo(Etudiant.INFO, anneeClassement);
		} else if (numeroPage == PlaceClassement.placeClassementTE.getEmplacement()) {
			listeEtudiant = manager.getEtudiantPromo(Etudiant.TE, anneeClassement);
		} else if (numeroPage == PlaceClassement.placeClassementETN.getEmplacement()) {
			listeEtudiant = manager.getEtudiantPromo(Etudiant.ETN, anneeClassement);
		} else if (numeroPage == PlaceClassement.placeClassementMAT.getEmplacement()) {
			listeEtudiant = manager.getEtudiantPromo(Etudiant.MAT, anneeClassement);
		} else {
			Log.e("ClassementFragmentFragment", "Ni ETN, INFO, TE, MAT ("+numeroPage+")");
		}
		
		Log.e("ClassementFragmentFragment", "taille"+listeEtudiant.size()+"");

		ListView listView = (ListView) rootView.findViewById(R.id.listViewClassement);
		listView.setAdapter(new ClassementAdapter(this, listeEtudiant, etudiant));
		
		return rootView;
	}
}