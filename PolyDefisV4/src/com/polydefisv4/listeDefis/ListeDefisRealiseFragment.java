package com.polydefisv4.listeDefis;

import java.util.ArrayList;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.polydefisv3.R;
import com.polydefisv4.metier.Defis;
import com.polydefisv4.metier.Etudiant;

public class ListeDefisRealiseFragment extends Fragment implements OnItemClickListener {
	private Etudiant etudiant;
	private ArrayList<Defis> listeDefisEtudiant;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_liste_defis_realise, container, false);
        etudiant = (Etudiant) getArguments().getSerializable("etudiant");
	    
	    ListView listViewDefis = (ListView) rootView.findViewById(R.id.listViewDefis);
	    
	    //listeDefisEtudiant = SQLManager.getDefis(etudiant);
	    listeDefisEtudiant = Defis.getAllDefis();
	    listViewDefis.setAdapter(new ListeDefisAdapter(this.getActivity(),Defis.getAllDefis()));
	    listViewDefis.setOnItemClickListener(this);
		return rootView;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,	long id) {
		Toast.makeText(getActivity(), listeDefisEtudiant.get(position).getIntitule(), Toast.LENGTH_LONG).show();
	}
}
