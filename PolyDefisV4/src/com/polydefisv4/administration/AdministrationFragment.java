package com.polydefisv4.administration;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.polydefisv3.R;

public class AdministrationFragment extends Fragment implements OnClickListener {

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_administration, container, false);

		Button boutonValiderPropositionDefis = (Button) rootView.findViewById(R.id.boutonValiderPropositionDefis);
		boutonValiderPropositionDefis.setOnClickListener(this);
		
		Button boutonValiderDefisRealise = (Button) rootView.findViewById(R.id.boutonValiderDefisRealise);
		boutonValiderDefisRealise.setOnClickListener(this);

		Button boutonAjoutRespo = (Button) rootView.findViewById(R.id.boutonAjoutRespo);
		boutonAjoutRespo.setOnClickListener(this);
		
		return rootView;
	}
	
	@Override
	public void onClick(View v) {
		Button selection = (Button) v;
		
		if(selection.getText().toString().equals(getResources().getString(R.string.validerPropositionDefis))) {
			Toast.makeText(AdministrationFragment.this.getActivity(), "Pas encore implementé", Toast.LENGTH_LONG).show();
		} else if (selection.getText().toString().equals(getResources().getString(R.string.validerDefisRealise))) {
			Toast.makeText(AdministrationFragment.this.getActivity(), "Pas encore implementé", Toast.LENGTH_LONG).show();
		} else if (selection.getText().toString().equals(getResources().getString(R.string.ajoutRespo))) {
			Intent intent = new Intent (AdministrationFragment.this.getActivity(), AjoutAdministrateurFragment.class);
			startActivity(intent);
		}
		
	}
}
