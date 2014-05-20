package com.polydefisv4.ajoutAdministrateur;

import java.util.ArrayList;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.polydefisv4.R;
import com.polydefisv4.bdd.SQLManager;
import com.polydefisv4.bean.Etudiant;
import com.polydefisv4.parrainage.Parrainage3AAdapter;

public class AjoutAdministrateurFragment extends Fragment implements OnClickListener, OnItemClickListener {
	private AutoCompleteTextView nomParrain = null;
	private Button boutonAjoutResponsable;
	ArrayList<Etudiant> listePotencielAdministrateur;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ajout_administrateur, container, false);

		nomParrain = (AutoCompleteTextView) rootView.findViewById(R.id.nomResponsable);
		nomParrain.setThreshold(2);
		nomParrain.setOnItemClickListener(this);
		
		SQLManager sqlManager = new SQLManager(getActivity());
		listePotencielAdministrateur = sqlManager.getEtudiantAnnee(4);
		Parrainage3AAdapter adapter = new Parrainage3AAdapter(getActivity(),android.R.layout.simple_dropdown_item_1line, android.R.id.text1, listePotencielAdministrateur);
		
		nomParrain.setAdapter(adapter);

		boutonAjoutResponsable = (Button) rootView.findViewById(R.id.boutonAjoutResponsable);
		boutonAjoutResponsable.setOnClickListener(this);
		
		return rootView;
	}
	
	

	@Override
	public void onClick(View v) {
		Toast.makeText(AjoutAdministrateurFragment.this.getActivity(),
				"Ajout du nouveau parain : " + nomParrain.getText().toString(),
				Toast.LENGTH_LONG).show();
	}



	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		System.out.println(listePotencielAdministrateur.get(position).getNom());
	}
}