package com.polydefisv4.accueil;

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
import android.widget.GridView;
import android.widget.Toast;

import com.polydefisv3.R;
import com.polydefisv4.classement.ClassementFragment;
import com.polydefisv4.listeDefis.ListeDefisRealiseFragment;
import com.polydefisv4.metier.Etudiant;
import com.polydefisv4.profil.ProfilFragment;

public class AccueilFragment extends Fragment implements OnItemClickListener {
	private Etudiant etudiant;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_acceuil, container, false);
        etudiant = (Etudiant) getArguments().getSerializable("etudiant");
	    
	    GridView gridView = (GridView) rootView.findViewById(R.id.gridviewS);	    		
	    gridView.setAdapter(new AccueilAdapter(getActivity(),etudiant));
		gridView.setOnItemClickListener(this);
		
	    return rootView;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Fragment fragment = null;
		Toast.makeText(getActivity(), "onItemClick", Toast.LENGTH_LONG).show();
		switch (position) {
		case 0:
			fragment = new ListeDefisRealiseFragment();
			break;
		case 1:
			fragment = new ListeDefisRealiseFragment();
			break;
		case 2:
			fragment = new ProfilFragment();
			break;
		case 3:
			fragment = new ClassementFragment();
			break;
		case 4:
			fragment = new ClassementFragment();
			break;

		default:
			break;
		}

		if (fragment != null) {
			Bundle bundle = new Bundle();
			bundle.putSerializable("etudiant", etudiant);
			fragment.setArguments(bundle);
			
			FragmentManager fragmentManager = getFragmentManager();
			FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
			fragmentTransaction.replace(R.id.frame_container, fragment);
			fragmentTransaction.addToBackStack(null);
			fragmentTransaction.commit();
		} else {
			Log.e("AccueilActivity", "Error in creating fragment");
		}
	}
}