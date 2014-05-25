package com.polydefisv4.classement;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.polydefisv4.R;
import com.polydefisv4.adapter.ClassementAdapter;
import com.polydefisv4.bdd.SQLManager;
import com.polydefisv4.bean.Etudiant;

public class ClassementFragmentFragment extends Fragment {
	private int numeroPage;
	private int anneeClassement;
	private Etudiant etudiant;
	
	public static Fragment newInstance(int position, int anneeClassement, Etudiant etudiant) {
		ClassementFragmentFragment classementFragmentFragment = new ClassementFragmentFragment();
		Bundle bundle = new Bundle();
		bundle.putInt("section_number", position);
		bundle.putSerializable("etudiant", etudiant);
		bundle.putInt("anneePromotion", anneeClassement);
		classementFragmentFragment.setArguments(bundle);
		return classementFragmentFragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		numeroPage = getArguments().getInt("section_number");
		anneeClassement = getArguments().getInt("anneePromotion");
		etudiant = (Etudiant) getArguments().getSerializable("etudiant");
		Log.e("OnCreate", "Nouvelle année "+anneeClassement+"");
		Log.e("OnCreate","numero page " + numeroPage);

	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_fragment_classement, container, false);
		List<Etudiant> listeEtudiant = null;
		
		Log.e("", "année = "+ anneeClassement);
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
		
		ListView listView = (ListView) rootView.findViewById(R.id.listViewClassement);
		listView.setAdapter(new ClassementAdapter(this, listeEtudiant, etudiant));
		
		return rootView;
	}
}