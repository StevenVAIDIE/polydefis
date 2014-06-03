package com.polydefisv4.administration;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.polydefisv4.R;
import com.polydefisv4.adapter.AutoCompleteEtudiantAdapter;
import com.polydefisv4.bdd.SQLManager;
import com.polydefisv4.bean.Etudiant;



public class AjoutAdministrateurFragment extends Fragment implements OnClickListener, OnItemClickListener, OnKeyListener {
	private AutoCompleteTextView nomParrain = null;
	private Button boutonAjoutResponsable;
	ArrayList<Etudiant> listePotencielAdministrateur;
	private boolean derniereActionIsClick;
	private int dernierItemClicke;
	private AutoCompleteEtudiantAdapter adapter;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ajout_administrateur, container, false);
        getActivity().setTitle("Ajout d'un administrateur");
        
		nomParrain = (AutoCompleteTextView) rootView.findViewById(R.id.nomResponsable);
		nomParrain.setThreshold(2);
		nomParrain.setOnItemClickListener(this);
		nomParrain.setOnKeyListener(this);
		
		SQLManager sqlManager = new SQLManager(getActivity());
		listePotencielAdministrateur = sqlManager.getEtudiantAnnee(4);
		
		adapter = new AutoCompleteEtudiantAdapter(getActivity(),android.R.layout.simple_dropdown_item_1line, android.R.id.text1, listePotencielAdministrateur);
		nomParrain.setAdapter(adapter);

		boutonAjoutResponsable = (Button) rootView.findViewById(R.id.boutonAjoutResponsable);
		boutonAjoutResponsable.setOnClickListener(this);
		
		return rootView;
	}

	@Override
	public void onClick(View v) {
		SQLManager manager = new SQLManager(getActivity());
		if(derniereActionIsClick) {
			Etudiant etudiant4A = adapter.getItem(dernierItemClicke);
			manager.makeRespo(etudiant4A);
			Toast.makeText(getActivity(), etudiant4A.getNom()+" "+etudiant4A.getPrenom()+" est désormais administrateur", Toast.LENGTH_LONG).show();
			getActivity().onBackPressed();
		} else {
			Toast.makeText(getActivity(), "Veuillez selectionner un etudiant parmi la liste des etudiants", Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		derniereActionIsClick = true;
		dernierItemClicke = position;
	}

	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		derniereActionIsClick = false;
		return false;
	}
}