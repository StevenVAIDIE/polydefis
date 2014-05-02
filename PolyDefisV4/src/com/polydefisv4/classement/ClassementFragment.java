package com.polydefisv4.classement;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.polydefisv3.R;
import com.polydefisv4.metier.Etudiant;

public class ClassementFragment extends Fragment {
	private Etudiant etudiant;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_classement, container, false);
		Bundle i = getArguments();
	    etudiant = (Etudiant) i.getSerializable("etudiant");

		ListView gridView = (ListView) rootView.findViewById(R.id.listViewClassement);
		gridView.setAdapter(new ClassementAdapter(this,Etudiant.getAllEtudiant(), etudiant));
		
        return rootView;
    }
}
