package com.polydefisv4.parrainage;

import java.util.ArrayList;
import java.util.List;

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
import android.widget.ListView;
import android.widget.Toast;

import com.polydefisv4.R;
import com.polydefisv4.adapter.AutoCompleteEtudiantAdapter;
import com.polydefisv4.adapter.ListeAcceptationParrainnageAdapter;
import com.polydefisv4.bdd.SQLManager;
import com.polydefisv4.bean.Etudiant;
import com.polydefisv4.bean.Parrainage;

public class ParrainageFragment extends Fragment implements OnClickListener, OnItemClickListener, OnKeyListener {
	private AutoCompleteTextView nomParrain = null;
	private Etudiant etudiant;
	private List<Etudiant> listeDemandeParrainage;
	private ArrayList<Etudiant> listeEtudiant4A;
	private Button boutonDemandeParrainage;
	private AutoCompleteEtudiantAdapter adapter3A;
	private ListeAcceptationParrainnageAdapter adapter4A;
	private ListView listViewDemandeParrainage;
	private boolean derniereActionIsClic;
	private int dernierIndexClique;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		getActivity().setTitle("Parrainage");
		etudiant = (Etudiant) getArguments().getSerializable("etudiant");

		SQLManager manager = new SQLManager(getActivity());

		View rootView;
		if (etudiant.getAnneePromo() == 3) {
			rootView = inflater.inflate(R.layout.fragment_parrainage3a, container, false);
			nomParrain = (AutoCompleteTextView) rootView.findViewById(R.id.nomParrain);
			nomParrain.setThreshold(0);
			nomParrain.setOnItemClickListener(this);
			nomParrain.setOnKeyListener(this);
			listeEtudiant4A = manager.getEtudiantPromo(etudiant.getDepartement(), 4);
			adapter3A = new AutoCompleteEtudiantAdapter(getActivity().getApplicationContext(), android.R.layout.simple_dropdown_item_1line, android.R.id.text1, listeEtudiant4A);
			nomParrain.setAdapter(adapter3A);

			boutonDemandeParrainage = (Button) rootView.findViewById(R.id.boutonDemandeParrainage);
			boutonDemandeParrainage.setOnClickListener(this);
		} else {
			rootView = inflater.inflate(R.layout.fragment_parrainage4a, container, false);
			listViewDemandeParrainage = (ListView) rootView.findViewById(R.id.listeDemandeParrainage);

			listeDemandeParrainage = manager.getAllDemandeParrainage(etudiant);
			adapter4A = new ListeAcceptationParrainnageAdapter(this, listeDemandeParrainage);
			listViewDemandeParrainage.setAdapter(adapter4A);
			listViewDemandeParrainage.setEmptyView(rootView.findViewById(R.id.empty));
		}
		return rootView;
	}

	@Override
	public void onClick(View v) {
		Button selection = (Button) v;
		SQLManager manager = new SQLManager(getActivity());

		if (v.equals(boutonDemandeParrainage)) {
			if (!derniereActionIsClic) {
				Toast.makeText(getActivity(), "Veuillez selectionner un etudiant parmi la liste des etudiants", Toast.LENGTH_LONG).show();
			} else {
				Parrainage parrainage = new Parrainage(adapter3A.getItem(dernierIndexClique).getIdEtudiant(), etudiant.getIdEtudiant(), Parrainage.DEMANDE_EN_COURS);
				manager.insertParrainage(parrainage);
				Toast.makeText(getActivity(), "Votre demande a bien été envoyé a " + adapter3A.getItem(dernierIndexClique).getNom()+" "+adapter3A.getItem(dernierIndexClique).getPrenom(), Toast.LENGTH_LONG).show();
				getActivity().onBackPressed();
			}
		} else {
			int position = (Integer) v.getTag();
			Etudiant etudiantSelectionne = listeDemandeParrainage.get(position);
			Parrainage parrainageSelectionne = manager.getParrainnage(etudiant, etudiantSelectionne);
			if (selection.getText().toString().equals(getResources().getString(R.string.accepter))) {
				parrainageSelectionne.setEtat(Parrainage.DEMANDE_ACCEPTE);
				manager.updateParrainage(parrainageSelectionne);
				Toast.makeText(getActivity(), "Le parrainage avec "+ etudiantSelectionne.getNom()+" "+etudiantSelectionne.getPrenom()+" à bien été accepté", Toast.LENGTH_LONG).show();
				adapter4A.notifyDataSetChanged();
			} else if (selection.getText().toString().equals(getResources().getString(R.string.refuser))) {
				manager.removeParrainage(parrainageSelectionne);
				listeDemandeParrainage.remove(position);
				Toast.makeText(getActivity(), "Le parrainage avec "+ etudiantSelectionne.getNom()+" "+etudiantSelectionne.getPrenom()+" à bien été refusé", Toast.LENGTH_LONG).show();
				adapter4A.notifyDataSetChanged();
			}
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		derniereActionIsClic = true;
		dernierIndexClique = position;
	}

	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		derniereActionIsClic = false;
		return false;
	}
}