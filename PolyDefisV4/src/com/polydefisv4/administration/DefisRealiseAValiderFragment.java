package com.polydefisv4.administration;

import java.util.ArrayList;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.polydefisv4.R;
import com.polydefisv4.adapter.ListeDefisAdapter;
import com.polydefisv4.bean.Defi;
import com.polydefisv4.bean.Etudiant;

public class DefisRealiseAValiderFragment extends Fragment {
	private Etudiant etudiant;
	private ArrayList<Defi> listeDefisEtudiant;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_defi_realise_a_valider, container, false);
        etudiant = (Etudiant) getArguments().getSerializable("etudiant");
	    
	    ListView listViewDefis = (ListView) rootView.findViewById(R.id.listViewDefis);
	    
	    //listeDefisEtudiant = SQLManager.getDefis(etudiant);
	    listeDefisEtudiant = Defi.getAllDefis();
	    listViewDefis.setAdapter(new ListeDefisAdapter(this.getActivity(),listeDefisEtudiant));
		return rootView;
	}
}
