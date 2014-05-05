package com.polydefisv4.parrainage;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.polydefisv3.R;
import com.polydefisv4.metier.Etudiant;

public class ParrainageFragment extends Fragment implements OnClickListener {
	private AutoCompleteTextView nomParrain = null;
	private Etudiant etudiant;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Bundle i = getArguments();
	    etudiant = (Etudiant) i.getSerializable("etudiant");
	    
	    View rootView;
		if(etudiant.getAnnee() == 3) {
	        rootView = inflater.inflate(R.layout.fragment_parrainage3a, container, false);
			nomParrain = (AutoCompleteTextView) rootView.findViewById(R.id.nomParrain);
			nomParrain.setThreshold(2);
			
			Parrainage3AAdapter adapter = new Parrainage3AAdapter(getActivity().getApplicationContext(), android.R.layout.simple_dropdown_item_1line, android.R.id.text1, Etudiant.getAllEtudiant());
			nomParrain.setAdapter(adapter);
	    
			Button boutonDemandeParrainage = (Button) rootView.findViewById(R.id.boutonDemandeParrainage);
			boutonDemandeParrainage.setOnClickListener(this);
		} else  {
	        rootView = inflater.inflate(R.layout.fragment_parrainage4a, container, false);
			ListView listeDemandeParrainage = (ListView) rootView.findViewById(R.id.listeDemandeParrainage);

			Parrainage4AAdapter adapter = new Parrainage4AAdapter(this, Etudiant.getAllEtudiant());
			listeDemandeParrainage.setAdapter(adapter);
		}
		return rootView;		
	}
	
	@Override
	public void onClick(View v) {
		Button selection = (Button) v;
		if(selection.getText().toString().equals(getResources().getString(R.string.demande_Parrainage))) {
			Toast.makeText(ParrainageFragment.this.getActivity(), "Ajout du nouveau parain : "+nomParrain.getText().toString(), Toast.LENGTH_LONG).show();
		//} else if(selection.getText().equals(getResources().getString(R.string.listeDefisARealiser))) {

		//} else if(selection.getText().equals(getResources().getString(R.string.listeDefisARealiser))) {
		
		}
	}
}