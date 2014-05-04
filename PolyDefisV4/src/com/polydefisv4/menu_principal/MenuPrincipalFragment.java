package com.polydefisv4.menu_principal;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.polydefisv3.R;
import com.polydefisv4.classement.ClassementFragment;
import com.polydefisv4.listeDefis.ListeDefisRealiseFragment;
import com.polydefisv4.metier.Etudiant;
import com.polydefisv4.profil.ProfilFragment;

public class MenuPrincipalFragment extends Fragment implements
		OnItemClickListener {
	private Etudiant etudiant;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_acceuil, container,
				false);
		etudiant = (Etudiant) getArguments().getSerializable("etudiant");
		getActivity().getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);  

		GridView gridView = (GridView) rootView.findViewById(R.id.gridviewS);
		gridView.setAdapter(new MenuPrincipalAdapter(getActivity(), etudiant));
		gridView.setOnItemClickListener(this);

		return rootView;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Button boutonclique = (Button) view;

		Fragment fragment = null;
		if (boutonclique.getText().toString().equals("Liste des défis réalise")) {
			fragment = new ListeDefisRealiseFragment();
		} else if (boutonclique.getText().toString().equals("Liste défis à réaliser")) {
			fragment = new ListeDefisRealiseFragment();
		} else if (boutonclique.getText().toString().equals("Profil")) {
			fragment = new ProfilFragment();
		} else if (boutonclique.getText().toString().equals("Classement des 3A")) {
			fragment = new ClassementFragment();
		} else if (boutonclique.getText().toString().equals("Classement des 4A")) {
			fragment = new ClassementFragment();
		}

		if (fragment != null) {
			getActivity().setTitle(boutonclique.getText());

			Bundle bundle = new Bundle();
			bundle.putSerializable("etudiant", etudiant);
			fragment.setArguments(bundle);

			FragmentManager fragmentManager = getFragmentManager();
			FragmentTransaction fragmentTransaction = fragmentManager
					.beginTransaction();
			fragmentTransaction.replace(R.id.frame_container, fragment);
			fragmentTransaction.addToBackStack(null);
			fragmentTransaction.commit();
		} else {
			Log.e("AccueilActivity", "Error in creating fragment");
		}
	}
}