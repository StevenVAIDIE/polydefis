package com.polydefisv4.classement;

import java.util.List;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.polydefisv3.R;
import com.polydefisv4.metier.Etudiant;

/**
 * A placeholder fragment containing a simple view.
 */
public class ClassementFragmentFragment extends Fragment {
	private static final String ARG_SECTION_NUMBER = "section_number";

	/**
	 * Returns a new instance of this fragment for the given section number.
	 */
	public static ClassementFragmentFragment newInstance(int numeroPage, Etudiant etudiant) {
		ClassementFragmentFragment fragment = new ClassementFragmentFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_SECTION_NUMBER, numeroPage);
		args.putSerializable("etudiant", etudiant);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_fragment_classement, container, false);
		int numeroPage = getArguments().getInt(ARG_SECTION_NUMBER);
		List<Etudiant> listeEtudiant = null;
		
		Etudiant etudiant = (Etudiant) getArguments().getSerializable("etudiant");
		if(etudiant == null) {
			Toast.makeText(getActivity(), "Argument etudiant null", Toast.LENGTH_LONG).show();
		}
		
		if (numeroPage == PlaceClassement.placeClassementTotal.getEmplacement()) {
			listeEtudiant = Etudiant.getAllEtudiant();
		} else if (numeroPage == PlaceClassement.placeClassementINFO.getEmplacement()) {
			listeEtudiant = Etudiant.getAllEtudiant();
		} else if (numeroPage == PlaceClassement.placeClassementTE.getEmplacement()) {
			listeEtudiant = Etudiant.getAllEtudiant();
		} else if (numeroPage == PlaceClassement.placeClassementETN.getEmplacement()) {
			listeEtudiant = Etudiant.getAllEtudiant();
		} else if (numeroPage == PlaceClassement.placeClassementMAT.getEmplacement()) {
			listeEtudiant = Etudiant.getAllEtudiant();
		}
		
		ListView gridView = (ListView) rootView.findViewById(R.id.listViewClassement);
		gridView.setAdapter(new ClassementAdapter(this,listeEtudiant, etudiant));
		
		return rootView;
	}
}