package com.polydefisv4.parrainage;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.polydefisv4.R;
import com.polydefisv4.adapter.AutoCompleteEtudiantAdapter;
import com.polydefisv4.adapter.ListeAcceptationParrainnageAdapter;
import com.polydefisv4.bdd.SQLManager;
import com.polydefisv4.bean.Etudiant;

public class ParrainageFragment extends Fragment implements OnClickListener {
	private AutoCompleteTextView nomParrain = null;
	private Etudiant etudiant;
	private List<Etudiant> listeEtudiant;
	private ArrayList<Etudiant> listeParrain;
	private Button boutonDemandeParrainage;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Bundle i = getArguments();
		etudiant = (Etudiant) i.getSerializable("etudiant");

		View rootView;
		if (etudiant.getAnneePromo() == 3) {
			rootView = inflater.inflate(R.layout.fragment_parrainage3a, container, false);
			nomParrain = (AutoCompleteTextView) rootView.findViewById(R.id.nomParrain);
			nomParrain.setThreshold(0);

			SQLManager manager = new SQLManager(getActivity());
			listeParrain = manager.getEtudiantAnnee(4);
			AutoCompleteEtudiantAdapter adapter = new AutoCompleteEtudiantAdapter(
					getActivity().getApplicationContext(),
					android.R.layout.simple_dropdown_item_1line,
					android.R.id.text1, listeParrain);
			nomParrain.setAdapter(adapter);

			boutonDemandeParrainage = (Button) rootView.findViewById(R.id.boutonDemandeParrainage);
			boutonDemandeParrainage.setOnClickListener(this);
		} else {
			rootView = inflater.inflate(R.layout.fragment_parrainage4a, container, false);
			ListView listeDemandeParrainage = (ListView) rootView.findViewById(R.id.listeDemandeParrainage);

			listeEtudiant = Etudiant.getAllEtudiant();
			ListeAcceptationParrainnageAdapter adapter = new ListeAcceptationParrainnageAdapter(
					this, listeEtudiant);
			listeDemandeParrainage.setAdapter(adapter);
		}
		return rootView;
	}

	@Override
	public void onClick(View v) {
		Button selection = (Button) v;
		if (v.equals(boutonDemandeParrainage)) {
			int index = listeParrain.indexOf(nomParrain.getText().toString());
			if (index == -1) {
				Toast.makeText(getActivity(), "Etudiant inconnu", Toast.LENGTH_LONG).show();
			} else {
				//Ajout de parrainage ....
			}
		} else {
			int position = (Integer) v.getTag();
			if (selection.getText().toString().equals(getResources().getString(R.string.accepter))) {
				Toast.makeText(getActivity(), "Acceptation de la position " + position, Toast.LENGTH_LONG).show();
			} else if (selection.getText().toString().equals(getResources().getString(R.string.refuser))) {
				Toast.makeText(getActivity(),"Refus de la position " + position, Toast.LENGTH_LONG).show();
			}
		}
	}
}