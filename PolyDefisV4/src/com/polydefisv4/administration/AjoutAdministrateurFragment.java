package com.polydefisv4.administration;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.polydefisv3.R;
import com.polydefisv4.metier.Etudiant;
import com.polydefisv4.parrainage.Parrainage3AAdapter;

public class AjoutAdministrateurFragment extends Fragment implements OnClickListener {
	private AutoCompleteTextView nomParrain = null;
	private Button boutonAjoutResponsable;

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ajout_administrateur, container, false);

		nomParrain = (AutoCompleteTextView) rootView.findViewById(R.id.nomResponsable);
		nomParrain.setThreshold(2);
		
		Parrainage3AAdapter adapter = new Parrainage3AAdapter(getActivity().getApplicationContext(),
				android.R.layout.simple_dropdown_item_1line,
				android.R.id.text1, Etudiant.getAllEtudiant());
		
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
}